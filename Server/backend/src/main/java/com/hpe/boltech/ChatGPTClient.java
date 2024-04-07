package com.hpe.boltech;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ChatGPTClient {

    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static String API_KEY;

    static {
        // Cargar la API key desde el archivo de configuración
        try (InputStream input = ChatGPTClient.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            API_KEY = prop.getProperty("openai.api.key");
        } catch (IOException ex) {
            ex.printStackTrace();
            // Si no se puede cargar la API key desde el archivo, muestra un error
            throw new RuntimeException("Error al cargar la API key desde el archivo de configuración", ex);
        }
    }

    public static String getChatGPTResponse(String userMessage) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Construir el cuerpo de la solicitud
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "ft:gpt-3.5-turbo-0613:personal:boltech:93TKf2Ee");
            
            // Construir los objetos de mensajes
            JsonObject systemMessage = new JsonObject();
            systemMessage.addProperty("role", "system");
            systemMessage.addProperty("content", "Eres un experto en primeros auxilios intentando ayudar a una persona que no sabe cómo actuar frente a una situación de primeros auxilios. Vas a decirle los pasos a seguir frente a la correspondiente emergencia");

            JsonObject userMessageObject = new JsonObject();
            userMessageObject.addProperty("role", "user");
            userMessageObject.addProperty("content", userMessage);

            // Agregar los objetos de mensaje al arreglo de mensajes
            JsonArray messagesArray = new JsonArray();
            messagesArray.add(systemMessage);
            messagesArray.add(userMessageObject);

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
