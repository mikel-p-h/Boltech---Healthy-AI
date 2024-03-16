package com.example.application.views.main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.nimbusds.jose.shaded.gson.Gson;

public class DataService {

    private static final String BACKEND_URL = "https://backend-boltech.fly.dev/mensaje-enviado";

    public static String sendMessageToBackend(Message messageObj) throws IOException, InterruptedException {
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(messageObj);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BACKEND_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonMessage))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } 
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error al enviar mensaje al backend: " + e.getMessage();
        }
    }
}