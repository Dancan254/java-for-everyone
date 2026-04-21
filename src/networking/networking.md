# Networking in Java

Networking in Java is built on the `java.net` package, which provides classes for TCP and UDP communication, DNS resolution, and HTTP interaction. Java 11 added `java.net.http`, a modern HTTP client that replaces the older `HttpURLConnection` API.

All network I/O is inherently I/O-bound and can fail in ways that file I/O cannot — connections can time out, be refused, or drop mid-transfer. Every network operation that can block or fail throws checked exceptions (`IOException`, `SocketException`, `UnknownHostException`), so the compiler forces you to handle them.

---

## 1. The `java.net` Package

The primary networking classes used in this guide:

| Class / Interface      | Purpose                                                    |
|------------------------|------------------------------------------------------------|
| `InetAddress`          | Represents an IP address; resolves hostnames via DNS       |
| `Socket`               | TCP client — connects to a server and exchanges data       |
| `ServerSocket`         | TCP server — listens for incoming connections              |
| `DatagramSocket`       | UDP socket for sending and receiving datagrams             |
| `DatagramPacket`       | A single UDP message (payload + address)                   |
| `URL`                  | Represents a URL; parses its components                    |
| `HttpURLConnection`    | HTTP client built on `URL` (legacy, Java 1.1+)             |

Java 11 added `java.net.http.HttpClient`, which is the recommended replacement for `HttpURLConnection`.

---

## 2. InetAddress — Hostnames and IP Addresses

`InetAddress` represents either an IPv4 or IPv6 address. You obtain instances through its static factory methods; there is no public constructor.

```java
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDemo {
    public static void main(String[] args) throws UnknownHostException {

        // Resolve a hostname to its IP address
        InetAddress address = InetAddress.getByName("example.com");
        System.out.println("Host name:    " + address.getHostName());
        System.out.println("Host address: " + address.getHostAddress());

        // Get all IP addresses associated with a hostname
        InetAddress[] addresses = InetAddress.getAllByName("example.com");
        for (InetAddress addr : addresses) {
            System.out.println("  " + addr.getHostAddress());
        }

        // Get the address of the local machine
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("Local host: " + localhost.getHostName()
                + " / " + localhost.getHostAddress());

        // Create an address directly from a numeric IP string
        InetAddress byIp = InetAddress.getByName("93.184.216.34");
        System.out.println("Reverse lookup: " + byIp.getHostName());

        // Check reachability (uses ICMP or TCP echo on port 7; may require privileges)
        boolean reachable = address.isReachable(3000); // timeout in milliseconds
        System.out.println("Reachable: " + reachable);
    }
}
```

`UnknownHostException` is thrown when the hostname cannot be resolved — for example, because there is no network connection or the DNS lookup fails.

---

## 3. TCP Communication with Sockets

TCP (Transmission Control Protocol) provides a reliable, ordered, connection-oriented byte stream. The Java model mirrors the OS socket API directly.

### How a TCP connection works

```
Client                              Server
------                              ------
Socket(host, port)   ---------->   ServerSocket.accept()
                                   returns a connected Socket

OutputStream (write) ---------->   InputStream (read)
InputStream  (read)  <----------   OutputStream (write)

close()              <--------->   close()
```

### 3.1 Building a TCP Server

`ServerSocket` binds to a port and waits for incoming connections. Each call to `accept()` **blocks** until a client connects, then returns a new `Socket` representing that connection. The server socket itself stays open so the next client can connect.

```java
import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        int port = 9000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            // Accept one client, handle it, then exit.
            // A real server would loop: while (true) { handle(serverSocket.accept()); }
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Client connected: "
                        + clientSocket.getInetAddress().getHostAddress());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Received: " + line);
                    out.println("Echo: " + line);  // send back to client
                }
            }
        }
        System.out.println("Server shut down.");
    }
}
```

Key points:
- `new ServerSocket(port)` binds the port immediately — throws `BindException` if the port is already in use.
- `accept()` returns only after a client connects; it does not time out by default.
- The `Socket` returned by `accept()` is separate from `ServerSocket` — close it when you are done with that client, not the server.
- Both `ServerSocket` and `Socket` implement `Closeable`; use try-with-resources.

### 3.2 Building a TCP Client

`Socket` connects to a server at a given host and port. Once connected, you read and write through its streams exactly as you would with file I/O.

```java
import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 9000;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to " + host + ":" + port);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String[] messages = {"Hello", "World", "Goodbye"};
            for (String message : messages) {
                out.println(message);           // send to server
                String response = in.readLine(); // wait for echo
                System.out.println("Server replied: " + response);
            }
        }
    }
}
```

### 3.3 Handling Multiple Clients with Threads

Blocking `accept()` means a single-threaded server can only talk to one client at a time. The standard solution is to dispatch each accepted connection to a new thread.

```java
import java.io.*;
import java.net.*;

public class MultiClientServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(9001)) {
            System.out.println("Multi-client server running on port 9001");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Spawn a thread per client — keeps accept() free for the next one
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("[" + socket.getPort() + "] " + line);
                out.println("Echo: " + line);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        }
    }
}
```

### 3.4 Socket Configuration

```java
Socket socket = new Socket();

// Set timeouts before connecting
socket.setSoTimeout(5000);             // read() blocks at most 5 seconds
socket.connect(new InetSocketAddress("example.com", 80), 3000); // connect timeout

socket.setTcpNoDelay(true);            // disable Nagle's algorithm for low-latency
socket.setSoLinger(true, 2);           // wait up to 2 seconds on close() to flush
socket.setKeepAlive(true);             // OS sends periodic probes to detect dead connections
```

`SocketTimeoutException` (a subclass of `IOException`) is thrown when `setSoTimeout` expires during a read.

---

## 4. UDP with DatagramSocket

UDP (User Datagram Protocol) is connectionless and does not guarantee delivery or order. Each message is a self-contained `DatagramPacket` that carries both payload and destination address. Use UDP when speed matters more than reliability — for example, DNS queries, gaming, or video streaming.

```java
import java.net.*;

public class UdpExample {

    // --- Server side ---
    static void server() throws Exception {
        try (DatagramSocket socket = new DatagramSocket(9002)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("UDP server listening on port 9002");
            socket.receive(packet);  // blocks until a datagram arrives

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received: " + received);

            // Reply to the sender
            String reply = "UDP echo: " + received;
            byte[] replyData = reply.getBytes();
            DatagramPacket replyPacket = new DatagramPacket(
                    replyData, replyData.length, packet.getAddress(), packet.getPort());
            socket.send(replyPacket);
        }
    }

    // --- Client side ---
    static void client() throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9002;

            String message = "Hello UDP";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(
                    data, data.length, serverAddress, serverPort);
            socket.send(packet);

            // Wait for reply
            byte[] buffer = new byte[1024];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.setSoTimeout(3000);  // don't wait forever
            socket.receive(reply);

            System.out.println("Server replied: "
                    + new String(reply.getData(), 0, reply.getLength()));
        }
    }
}
```

UDP differences from TCP:
- No `connect()` / `accept()` handshake — just `send()` and `receive()`
- A single `DatagramSocket` can exchange packets with any number of peers
- Packets may be lost, duplicated, or arrive out of order — the application must deal with this if it matters
- Maximum safe UDP payload is around 508 bytes; practical implementations use up to ~65 507 bytes but fragmentation risk increases beyond 1472 bytes

---

## 5. HTTP Requests with HttpClient (Java 11+)

`java.net.http.HttpClient` is the modern, fluent HTTP client. It supports HTTP/1.1 and HTTP/2, synchronous and asynchronous requests, redirects, cookies, and authentication.

### Synchronous GET request

```java
import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class HttpClientDemo {
    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/get"))
                .header("Accept", "application/json")
                .GET()
                .timeout(Duration.ofSeconds(15))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Body:   " + response.body());
    }
}
```

### POST request with a JSON body

```java
HttpRequest postRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://httpbin.org/post"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString("""
                {"name": "Alice", "age": 30}
                """))
        .build();

HttpResponse<String> postResponse =
        client.send(postRequest, HttpResponse.BodyHandlers.ofString());

System.out.println("POST status: " + postResponse.statusCode());
System.out.println("POST body:   " + postResponse.body());
```

### Asynchronous request

`sendAsync()` returns a `CompletableFuture` and does not block the calling thread.

```java
client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(body -> System.out.println("Async response: " + body))
        .join(); // wait for completion in this example; don't call join() in real async code
```

### Body handlers

| Handler                                   | Result type              |
|-------------------------------------------|--------------------------|
| `BodyHandlers.ofString()`                 | `String`                 |
| `BodyHandlers.ofByteArray()`              | `byte[]`                 |
| `BodyHandlers.ofFile(Path)`               | `Path` (saves to file)   |
| `BodyHandlers.ofLines()`                  | `Stream<String>`         |
| `BodyHandlers.discarding()`               | `Void` (ignores body)    |

---

## 6. Legacy HTTP with HttpURLConnection

`HttpURLConnection` predates Java 11 and remains in use in older codebases. Prefer `HttpClient` for new code, but you will encounter this class when maintaining legacy systems.

```java
import java.io.*;
import java.net.*;

public class HttpUrlConnectionDemo {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://httpbin.org/get");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(10_000);
        connection.setReadTimeout(15_000);

        int status = connection.getResponseCode();
        System.out.println("Status: " + status);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            connection.disconnect();
        }
    }
}
```

---

## 7. Common Networking Exceptions

| Exception                  | Cause                                                          |
|----------------------------|----------------------------------------------------------------|
| `UnknownHostException`     | DNS lookup failed — hostname not found                         |
| `ConnectException`         | Connection refused — nothing listening on that port            |
| `SocketTimeoutException`   | A read or connect operation exceeded its timeout               |
| `SocketException`          | Socket error (connection reset, broken pipe, etc.)             |
| `BindException`            | Cannot bind to the requested port (already in use)             |
| `IOException`              | General I/O failure on a network stream                        |
| `HttpConnectTimeoutException` | HTTP connect timeout (Java 11 `HttpClient`)                 |
| `HttpTimeoutException`     | HTTP request timeout (Java 11 `HttpClient`)                    |

All of these (except `HttpConnectTimeoutException` and `HttpTimeoutException`) extend `IOException`, so you can catch them individually for specific handling or together with `catch (IOException e)` for a common fallback.

---

## 8. Complete Example: HTTP Weather Client

This example demonstrates `HttpClient`, JSON parsing with standard string operations, and structured error handling.

```java
import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class WeatherClient {

    private static final String BASE_URL =
            "https://wttr.in/%s?format=j1"; // public weather API, no key required

    private final HttpClient client;

    public WeatherClient() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public String fetchWeatherJson(String city) throws Exception {
        String url = String.format(BASE_URL, city.replace(" ", "+"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "java-weather-client/1.0")
                .GET()
                .timeout(Duration.ofSeconds(15))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Unexpected HTTP status: " + response.statusCode());
        }
        return response.body();
    }

    public static void main(String[] args) {
        WeatherClient weatherClient = new WeatherClient();

        String[] cities = {"London", "Nairobi", "Tokyo"};

        for (String city : cities) {
            try {
                String json = weatherClient.fetchWeatherJson(city);
                System.out.println("Fetched weather data for " + city
                        + " (" + json.length() + " chars)");
            } catch (java.net.UnknownHostException e) {
                System.out.println("No network connection or DNS failure: " + e.getMessage());
            } catch (java.net.http.HttpTimeoutException e) {
                System.out.println("Request to " + city + " timed out.");
            } catch (Exception e) {
                System.out.println("Error fetching data for " + city + ": " + e.getMessage());
            }
        }
    }
}
```

---

## 9. Best Practices

### Always set timeouts
A `Socket` or `HttpClient` with no timeout can block a thread indefinitely if the remote host is unreachable or slow.

```java
// Socket
socket.setSoTimeout(5000);  // 5 seconds read timeout

// HttpClient
HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
HttpRequest.newBuilder().timeout(Duration.ofSeconds(15)).build();
```

### Use try-with-resources for all sockets
`Socket`, `ServerSocket`, and `DatagramSocket` all implement `Closeable`. Not closing them leaks file descriptors.

```java
// Good
try (Socket socket = new Socket(host, port)) {
    // use socket
}

// Bad — if an exception is thrown before close(), the socket leaks
Socket socket = new Socket(host, port);
// ... use socket
socket.close();
```

### Separate I/O from business logic
Mixing network calls with domain logic makes code hard to test. Wrap network operations in a dedicated class and use checked exceptions to signal failure.

```java
public class UserApiClient {
    public User findUser(long id) throws IOException, InterruptedException {
        // network call here — callers decide how to handle failures
    }
}
```

### Handle connection failures gracefully
Network errors are expected, not exceptional. Catch specific exception types and communicate clearly to the caller or user.

```java
try {
    String data = client.fetch(url);
} catch (java.net.UnknownHostException e) {
    System.out.println("Cannot reach server — check your network connection.");
} catch (java.net.http.HttpTimeoutException e) {
    System.out.println("Request timed out. Try again later.");
} catch (IOException e) {
    System.out.println("Network error: " + e.getMessage());
}
```

### Prefer HttpClient over HttpURLConnection for new code
`HttpClient` is cleaner, supports HTTP/2, handles redirects natively, and has a fluent builder API. `HttpURLConnection` is kept for backward compatibility only.

---

## 10. Key Takeaways

- `InetAddress` resolves hostnames to IP addresses via DNS; `getLocalHost()` returns the local machine's address
- TCP provides a reliable, ordered byte stream; `ServerSocket` accepts connections and `Socket` represents each one
- `accept()` blocks indefinitely — use per-client threads or non-blocking I/O (`java.nio`) for concurrent servers
- UDP is connectionless and unreliable; use `DatagramSocket` and `DatagramPacket` when speed matters more than delivery guarantees
- `java.net.http.HttpClient` (Java 11+) is the preferred way to make HTTP requests; use `send()` for synchronous and `sendAsync()` for non-blocking calls
- Always set connect and read timeouts — unprotected blocking calls can freeze threads indefinitely
- All network I/O classes implement `Closeable`; use try-with-resources to avoid resource leaks
- Networking exceptions (`UnknownHostException`, `ConnectException`, `SocketTimeoutException`) all extend `IOException` — catch them specifically for meaningful error messages

---

Networking is inherently unpredictable. Write code that expects failures, sets timeouts, and surfaces meaningful errors rather than code that assumes connections succeed.
