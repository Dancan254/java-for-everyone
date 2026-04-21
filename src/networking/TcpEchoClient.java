package networking;

import java.io.*;
import java.net.*;

public class TcpEchoClient {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 9000;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to " + host + ":" + port);

            String[] messages = {"Hello", "Networking in Java", "Goodbye"};
            for (String message : messages) {
                out.println(message);
                String response = in.readLine();
                System.out.println("Server replied: " + response);
            }
        }

        System.out.println("Client done.");
    }
}
