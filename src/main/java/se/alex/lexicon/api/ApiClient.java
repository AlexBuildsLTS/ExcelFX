package se.alex.lexicon.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class ApiClient {

    private static final Map<String, String> API_ENDPOINTS;

    static {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("src/main/resources/api_config.json"));
            Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
            Map<String, Map<String, String>> config = gson.fromJson(reader, type);
            API_ENDPOINTS = config.get("endpoints");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API endpoints from JSON configuration file", e);
        }
    }

    public static double getExchangeRate(String api, String parameter, String fromDate, String toDate) throws IOException {
        String apiUrl = API_ENDPOINTS.get(api)
                .replace("{interestRateId}", parameter)
                .replace("{compoundedAverageId}", parameter)
                .replace("{compoundedIndexId}", parameter)
                .replace("{fromDate}", fromDate)
                .replace("{toDate}", toDate != null ? toDate : "");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(apiUrl);
        CloseableHttpResponse response = httpClient.execute(request);

        try {
            String json = EntityUtils.toString(response.getEntity());
            System.out.println("API Response: " + json);  // Print the JSON response for debugging
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

            if (jsonArray.size() > 0) {
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                if (jsonObject.has("rate")) {
                    return jsonObject.get("rate").getAsDouble();
                } else {
                    System.err.println("Error: API response does not contain 'rate' field");
                    throw new IOException("Invalid API response: 'rate' field missing");
                }
            } else {
                throw new IOException("Empty API response");
            }
        } finally {
            response.close();
        }
    }
}
