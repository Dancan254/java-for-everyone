# Networking Practice Exercises

Networking code requires handling failure modes that are absent in pure in-memory code — timeouts, refused connections, DNS failures, and partial reads. The exercises below build from basic hostname resolution through TCP client-server programming to HTTP clients. Work through each set in order.

---

## Exercise Set 1: InetAddress and DNS

### Exercise 1.1: Hostname Lookup

Write a method `resolve(String hostname)` that:
- Uses `InetAddress.getByName` to look up the hostname
- Prints the canonical hostname and IP address
- Catches `UnknownHostException` and prints a specific message explaining that the host could not be found

Call it with `"google.com"`, `"localhost"`, and `"this.hostname.does.not.exist"`.

### Exercise 1.2: All Addresses

Write a method `resolveAll(String hostname)` that uses `InetAddress.getAllByName` to retrieve every IP address associated with a hostname. Print each one on its own line, prefixed with its index (e.g., `[0] 142.250.185.14`). If the hostname cannot be resolved, print a clear error message.

Call it with a hostname that has multiple addresses (try `"google.com"`).

### Exercise 1.3: Reachability Check

Write a method `checkReachable(String hostname, int timeoutMs)` that:
1. Resolves the hostname with `InetAddress.getByName`
2. Calls `isReachable(timeoutMs)` on the result
3. Prints whether the host responded within the timeout, and if not, what the timeout was

Wrap the entire method in appropriate exception handling. Call it with `"localhost"` (should succeed) and a non-routable address such as `"192.0.2.1"` (reserved, will time out).

---

## Exercise Set 2: TCP Sockets

### Exercise 2.1: Echo Server and Client

Build a single-round-trip echo pair:

**Server** — `SingleEchoServer`:
- Listens on port 9100
- Accepts one connection, reads one line, sends it back prefixed with `"Echo: "`, then closes

**Client** — `SingleEchoClient`:
- Connects to `localhost:9100`
- Sends the string `"Hello, server"` using `PrintWriter`
- Reads and prints the response with `BufferedReader`

Run the server first in one terminal, then the client in another. Show that the echo arrives correctly.

### Exercise 2.2: Multi-Message Session

Extend the exercise above so the client sends five numbered messages (`"Message 1"` through `"Message 5"`) one by one, printing each echo before sending the next. The server should handle all five messages in a single connection (do not close between messages) and echo each one.

### Exercise 2.3: Multi-Client Server

Rewrite the server so it can serve multiple clients concurrently:
- The server loops on `accept()` indefinitely
- Each accepted connection is dispatched to a new `Thread` that handles the full session
- The handler reads lines until the client disconnects, echoing each one

Start the server, then launch three simultaneous clients (each in its own thread within a test `main`). Verify that all three sessions complete without blocking each other.

### Exercise 2.4: Socket Timeouts

Write a client that:
1. Opens a `Socket` to `localhost:9100` (the server from earlier exercises)
2. Sets a read timeout of 2 000 ms with `setSoTimeout`
3. Sends a message and attempts to read the reply
4. Catches `SocketTimeoutException` separately from other `IOExceptions` and prints a distinct message for each

Start the server, send a message, and let it echo normally. Then modify the server to add a 3-second sleep before replying — rerun the client and confirm that `SocketTimeoutException` is caught.

---

## Exercise Set 3: UDP

### Exercise 3.1: Single UDP Exchange

**Server** — `UdpEchoServer`:
- Binds a `DatagramSocket` to port 9200
- Receives one datagram, prints its content and the sender's address and port
- Sends back `"UDP echo: " + received` to the sender
- Closes after one exchange

**Client** — `UdpEchoClient`:
- Creates a `DatagramSocket` (no fixed port)
- Sets a receive timeout of 3 000 ms
- Sends `"ping"` to `localhost:9200`
- Reads and prints the reply

### Exercise 3.2: Unreliable Delivery Simulation

UDP does not guarantee delivery. Simulate this:
- Write a server that intentionally drops every other packet (use a counter and skip the reply on odd-numbered packets)
- Write a client that sends the same message up to 5 times with a 1-second timeout between attempts, stopping as soon as it receives a reply
- Print each retry attempt so you can see the retry logic working

---

## Exercise Set 4: HTTP with HttpClient

### Exercise 4.1: GET Request

Using `java.net.http.HttpClient`, write a method `get(String url)` that:
- Sends a GET request to the given URL
- Returns the response body as a `String`
- Throws a checked `IOException` if the HTTP status is not 2xx (use `response.statusCode()` to check)
- Sets a connect timeout of 10 seconds and a request timeout of 15 seconds

Call it with `"https://httpbin.org/get"` and print the status and first 200 characters of the body.

### Exercise 4.2: POST with JSON Body

Write a method `post(String url, String jsonBody)` that sends a POST request with `Content-Type: application/json`. Print the response status and body.

Use `"https://httpbin.org/post"` and pass a JSON string such as `{"name":"Alice","language":"Java"}`. The httpbin service echoes back exactly what you sent — verify that your JSON appears in the response.

### Exercise 4.3: Async Requests

Make three simultaneous HTTP GET requests using `sendAsync`:
- `"https://httpbin.org/delay/1"` (1-second artificial delay)
- `"https://httpbin.org/delay/2"` (2-second artificial delay)
- `"https://httpbin.org/get"` (no delay)

Use `CompletableFuture.allOf` to wait for all three. Print the URL and status code for each response as it arrives. Measure the total elapsed time and observe that three sequential blocking requests would take at least 3 seconds, while the async version should complete in around 2 seconds.

### Exercise 4.4: Error Handling

Write a method `safeFetch(String url)` that:
- Attempts an HTTP GET
- Catches `UnknownHostException` and prints `"Host not found"`
- Catches `HttpTimeoutException` and prints `"Request timed out"`
- Catches any other `IOException` and prints `"Network error: " + message`
- Returns `Optional<String>` — empty on any failure, the body on success

Test it with a valid URL, a non-existent domain, and a URL that is known to time out (e.g., `"http://10.0.0.0"` — a non-routable address — with a 2-second timeout).

---

## Exercise Set 5: Putting It Together

### Exercise 5.1: HTTP to TCP Bridge

Write a small application with two components:
1. An HTTP client that fetches a list of words from `"https://random-word-api.herokuapp.com/word?number=5"` (returns a JSON array of 5 random words)
2. A TCP server on port 9300 that accepts a connection, receives a line, and replies with that line reversed character by character

In `main`:
1. Start the TCP server on a background thread
2. Fetch 5 random words via HTTP
3. Connect to the TCP server with a TCP client
4. Send each word, receive the reversed word, and print both

### Exercise 5.2: Retry with Exponential Backoff

Network calls fail transiently — a retry strategy is essential for robust code. Write a method:

```java
public static String fetchWithRetry(String url, int maxAttempts) throws IOException
```

- Attempts the HTTP GET up to `maxAttempts` times
- After each failure (non-2xx status or IOException), waits before retrying: 1 s, 2 s, 4 s, ... (doubling each time)
- Logs each attempt and its result to `System.out`
- Throws `IOException` if all attempts are exhausted
- Returns the response body on success

Test it by first pointing at a working URL, then at `"https://httpbin.org/status/503"` (always returns 503).

---

## Common Mistakes

### Not setting timeouts

Every blocking network call can hang indefinitely without a timeout. A thread stuck in `socket.read()` or `httpClient.send()` with no timeout will hold that thread forever, eventually exhausting thread pools.

```java
// Bad — can block forever
Socket socket = new Socket("example.com", 80);
BufferedReader reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));
String line = reader.readLine();  // hangs if server never sends data

// Good — read times out after 5 seconds
socket.setSoTimeout(5000);
```

### Leaking sockets

Forgetting to close a socket leaks a file descriptor. On most operating systems, a process has a hard limit on open file descriptors — exhausting it causes `Too many open files` errors.

```java
// Bad — if readLine() throws, close() is never called
Socket socket = new Socket(host, port);
BufferedReader reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));
String data = reader.readLine();
socket.close();

// Good — closed automatically even if an exception is thrown
try (Socket socket = new Socket(host, port);
     BufferedReader reader = new BufferedReader(
             new InputStreamReader(socket.getInputStream()))) {
    String data = reader.readLine();
}
```

### Assuming HTTP 200 means success

`HttpClient.send()` does not throw an exception for non-2xx responses — it returns the response with the status code. Always check `response.statusCode()` before treating the body as valid data.

```java
// Bad — treats a 404 or 500 body as valid data
String body = client.send(request, BodyHandlers.ofString()).body();

// Good — check the status first
HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
if (response.statusCode() / 100 != 2) {
    throw new IOException("HTTP error: " + response.statusCode());
}
String body = response.body();
```

### Catching `IOException` and printing nothing

An empty or near-empty catch block discards the exception and leaves you with no information about what went wrong. At minimum, log the exception type and message.

```java
// Bad — the exception vanishes; you have no idea what failed
try {
    sendData(socket, payload);
} catch (IOException e) {
    // silently swallow
}

// Good — surface the failure with context
try {
    sendData(socket, payload);
} catch (IOException e) {
    System.out.println("Failed to send data to " + socket.getInetAddress()
            + ": " + e.getMessage());
}
```

---

Solutions for these exercises are in the `solutions/` subfolder.
