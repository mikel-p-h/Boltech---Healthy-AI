package com.example.application.views.main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.nimbusds.jose.shaded.gson.Gson;

public class DataService {

    private static final String BACKEND_URL = "http://localhost:8000/mensaje-enviado/";

    public static String sendMessageToBackend(Message messageObj) throws IOException, InterruptedException {
        // Convertir el objeto Message a JSON usando Gson
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(messageObj);
        System.err.println("Message being sent: " + jsonMessage);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BACKEND_URL))
                .header("Content-Type", "application/json") // Set Content-Type to application/json
                .POST(HttpRequest.BodyPublishers.ofString(jsonMessage)) // Enviar el JSON del mensaje
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Manejar la excepción según sea necesario (por ejemplo, relanzarla o devolver un mensaje de error)
            return "Error al enviar mensaje al backend: " + e.getMessage();
        }
    }
}
