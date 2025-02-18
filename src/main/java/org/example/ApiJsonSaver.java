package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiJsonSaver {

    public static void main(String[] args) {
        String apiUrl = "https://randomuser.me/api/";
        String filePath = "usuario.json"; // He cambiado el nombre del archivo para reflejar la lista de imágenes.

        try {
            String data = fetchData(apiUrl);
            JsonArray loadedData = loadJson(filePath);
            saveToJson(data, filePath, loadedData);
            System.out.println("Datos guardados en el archivo JSON.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para realizar la solicitud HTTP y obtener los datos de la API.
    public static String fetchData(String apiUrl) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } finally {
            connection.disconnect();
        }
    }

    // Método para cargar datos del archivo JSON existente.
    public static JsonArray loadJson(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonArray()) {
                return jsonElement.getAsJsonArray();
            }
        } catch (IOException e) {
            System.out.println("Archivo JSON no encontrado, creando uno nuevo.");
        }
        return new JsonArray();
    }

    // Método para guardar los datos en un archivo JSON, combinándolos con los existentes.
    public static void saveToJson(String newData, String filePath, JsonArray existingData) throws IOException {
        JsonObject newJsonObject = JsonParser.parseString(newData).getAsJsonObject();
        existingData.add(newJsonObject);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(existingData.toString());
        }
    }
}




