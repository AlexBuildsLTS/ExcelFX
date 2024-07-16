package se.alex.lexicon.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;

public class ApiClient {
    // Path to the configuration file
    private static final String CONFIG_FILE_PATH = "/api_config.json";
    // Map to store API endpoints
    private static Map<String, String> endpoints;

    // Static block to initialize the endpoints map
    static {
        try (InputStreamReader reader = new InputStreamReader(ApiClient.class.getResourceAsStream(CONFIG_FILE_PATH))) {
            Gson gson = new Gson();
            // Parse JSON file to a JsonObject
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            // Extract the "endpoints" object and convert it to a Map
            endpoints = gson.fromJson(jsonObject.get("endpoints"), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API endpoints from JSON configuration file", e);
        }
    }

    // Method to get an endpoint by key
    public static String getEndpoint(String key) {
        return endpoints.get(key);
    }
}
