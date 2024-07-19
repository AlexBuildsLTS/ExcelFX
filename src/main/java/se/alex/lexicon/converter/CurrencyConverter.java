package se.alex.lexicon.converter;

import se.alex.lexicon.api.ApiClient;

import java.io.IOException;

public class CurrencyConverter {

    private ApiClient apiClient;

    public CurrencyConverter() {
        this.apiClient = new ApiClient();
    }

    public double convert(String from, String to, double amount, String selectedApi) {
        try {
            double fromRate = apiClient.getExchangeRate(from, selectedApi);
            double toRate = apiClient.getExchangeRate(to, selectedApi);
            return (amount / fromRate) * toRate;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
