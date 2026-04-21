package networking;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class HttpClientDemo {

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        // --- GET ---
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/get"))
                .header("Accept", "application/json")
                .GET()
                .timeout(Duration.ofSeconds(15))
                .build();

        HttpResponse<String> getResponse =
                client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("GET status: " + getResponse.statusCode());
        System.out.println("GET body (first 300 chars):");
        System.out.println(getResponse.body().substring(0, Math.min(300, getResponse.body().length())));

        System.out.println();

        // --- POST with JSON body ---
        String jsonBody = "{\"name\":\"Alice\",\"language\":\"Java\"}";

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/post"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .timeout(Duration.ofSeconds(15))
                .build();

        HttpResponse<String> postResponse =
                client.send(postRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("POST status: " + postResponse.statusCode());
        System.out.println("POST body (first 300 chars):");
        System.out.println(postResponse.body().substring(0, Math.min(300, postResponse.body().length())));

        System.out.println();

        // --- Async GET ---
        System.out.println("Sending async request...");
        client.sendAsync(getRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(status -> System.out.println("Async response status: " + status))
                .join();
    }
}
