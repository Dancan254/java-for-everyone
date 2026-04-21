package networking;

import java.io.*;
import java.net.*;

public class TcpEchoServer {

    public static void main(String[] args) throws IOException {
        int port = 9000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Echo server listening on port " + port);
            System.out.println("Start TcpEchoClient in another terminal.");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: "
                    + clientSocket.getInetAddress().getHostAddress());

            try (clientSocket;
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Received: " + line);
                    out.println("Echo: " + line);
                }
            }
        }

        System.out.println("Server shut down.");
    }
}
