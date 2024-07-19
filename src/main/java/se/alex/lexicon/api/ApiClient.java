package se.alex.lexicon.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    private static final String BASE_URL = "https://api.riksbank.se/swea/v1/";

    private Map<String, String> apiEndpoints;

    public ApiClient() {
        apiEndpoints = new HashMap<>();
        apiEndpoints.put("SWESTR Interest Rate", "swestr/v1/{interestRateId}");
        apiEndpoints.put("SWESTR All Interest Rates", "swestr/v1/all/{interestRateId}");
        apiEndpoints.put("SWESTR Average Latest", "swestr/v1/avg/latest/{compoundedAverageId}");
        apiEndpoints.put("SWESTR Index Latest", "swestr/v1/index/latest/{compoundedIndexId}");
        apiEndpoints.put("SWESTR Latest Interest Rate", "swestr/v1/latest/{interestRateId}");
        apiEndpoints.put("SWESTR Average", "swestr/v1/avg/{compoundedAverageId}");
        apiEndpoints.put("SWESTR Index", "swestr/v1/index/{compoundedIndexId}");
    }

    public double getExchangeRate(String currency, String selectedApi) throws IOException {
        String endpoint = apiEndpoints.get(selectedApi).replace("{interestRateId}", currency);
        String url = BASE_URL + endpoint;
        return fetchExchangeRate(url);
    }

    public double fetchExchangeRate(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);

        try {
            String json = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.get("rate").getAsDouble();
        } finally {
            response.close();
        }
    }
}
