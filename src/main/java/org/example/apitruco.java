package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class apitruco {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Crea el cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Define la solicitud a la API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.coindesk.com/v1/bpi/currentprice.json"))
                .build();

        // Envía la solicitud y obtiene la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Convierte la respuesta en un objeto JSON
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(response.body(), JsonElement.class);

        // Guarda el objeto JSON en un archivo
        try (FileWriter file = new FileWriter("datos.json")) {
            gson.toJson(jsonElement, file);
        }

        System.out.println("Datos guardados en datos.json");
    }
}
