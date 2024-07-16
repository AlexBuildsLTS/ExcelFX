package se.alex.lexicon.converter;

import se.alex.lexicon.api.ApiClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    // Convert currency by calling the appropriate API endpoint
    public double convert(String selectedEndpoint, String fromCurrency, String toCurrency, double amount) {
        String endpoint = ApiClient.getEndpoint(selectedEndpoint)
                .replace("{interestRateId}", fromCurrency); // Modify as per the actual endpoint parameters
        double exchangeRate = fetchExchangeRate(endpoint);
        return amount * exchangeRate;
    }

    // Fetch the exchange rate from the API
    private double fetchExchangeRate(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response from the API
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                if (scanner.useDelimiter("\\A").hasNext()) {
                    String response = scanner.next();
                    // Parse and return the exchange rate from the response (implementation depends on API response structure)
                    return Double.parseDouble(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
