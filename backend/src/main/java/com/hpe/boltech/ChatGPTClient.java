package com.hpe.boltech;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ChatGPTClient {

    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";

    public static String getChatGPTResponse(String message) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Construir el cuerpo de la solicitud
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "gpt-3.5-turbo-0613");
            
            // Construir el objeto de mensajes
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty("role", "user");
            messageObject.addProperty("content", message);

            // Agregar el objeto de mensaje al arreglo de mensajes
            JsonArray messagesArray = new JsonArray();
            messagesArray.add(messageObject);

            requestBody.add("messages", messagesArray);
            requestBody.addProperty("max_tokens", 1000); // Número máximo de tokens para la respuesta

            // Construye la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CHATGPT_API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(requestBody)))
                    .build();

            // Envía la solicitud y procesa la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error al enviar mensaje a ChatGPT: " + e.getMessage();
        }
    }
}