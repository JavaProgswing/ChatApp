package chatapp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Requests {

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public Requests() {
    }

    public static Response get(String url) throws IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().header("User-Agent", "Mozilla/5.0").build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Response(response.statusCode(), response.body(), null);
    }

    public static Response get(String url, String auth) throws IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().header("User-Agent", "Mozilla/5.0").header("Authorization", auth).build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Response(response.statusCode(), response.body(), null);
    }

    public static Response post(String url, String postParams, String auth) throws IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).POST(HttpRequest.BodyPublishers.ofString(postParams)).header("User-Agent", "Mozilla/5.0").header("Content-Type", "application/json").header("Authorization", String.valueOf(auth)).build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Response(response.statusCode(), response.body(), null);
    }

    public static Response patch(String url, String patchParams, String auth) throws IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).method("PATCH", HttpRequest.BodyPublishers.ofString(patchParams)).header("User-Agent", "Mozilla/5.0").header("Content-Type", "application/json").header("Authorization", String.valueOf(auth)).build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Response(response.statusCode(), response.body(), null);
    }

    public static Response delete(String url, String deleteParams, String auth) throws IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).method("DELETE", HttpRequest.BodyPublishers.ofString(deleteParams)).header("User-Agent", "Mozilla/5.0").header("Authorization", String.valueOf(auth)).build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Response(response.statusCode(), response.body(), null);
    }
}
