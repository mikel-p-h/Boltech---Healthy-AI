package com.hpe.boltech;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @PostMapping("/mensaje-enviado")
    public String receiveMessage(@RequestBody String message) {
        // Enviar el mensaje a ChatGPT y obtener la respuesta
        String response = ChatGPTClient.getChatGPTResponse(message);

        // Procesar la respuesta de la API de ChatGPT
        String content = extractContentFromResponse(response);

        return content;
    }

    private String extractContentFromResponse(String response) {
        // Parsear la respuesta JSON
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        // Obtener el contenido del mensaje
        JsonArray choices = jsonObject.getAsJsonArray("choices");
        if (choices != null && choices.size() > 0) {
            JsonObject choice = choices.get(0).getAsJsonObject();
            JsonObject message = choice.getAsJsonObject("message");
            if (message != null) {
                return message.get("content").getAsString();
            }
        }

        // Si no se puede extraer el contenido, devuelve una cadena vacía o un mensaje de error
        return "Ha habido algún problema al procesar tu mensaje.";
    }
}