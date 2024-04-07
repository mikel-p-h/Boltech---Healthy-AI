package com.hpe.boltech;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LlamaClient {
    private static final String API_URL = "https://api-inference.huggingface.co/models/meta-llama/Llama-2-7b-chat-hf";
    private static final String API_KEY = System.getenv("LLAMA_API_KEY");


    public static String getLlamaResponse(String userMessage) {
        String response = null;
        try {
            // Construir el payload como un objeto JSON con el mensaje del usuario
            String payload = "{\"inputs\": \"" + userMessage + "\"}";

            // Establecer la conexión HTTP
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            // Escribir el payload en la solicitud
            OutputStream os = connection.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();

            // Leer la respuesta
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            response = sb.toString();

            // Cerrar la conexión
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
