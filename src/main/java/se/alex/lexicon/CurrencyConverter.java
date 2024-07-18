package se.alex.lexicon;

public class CurrencyConverter {
    // Placeholder for currency conversion logic
    public double convert(double amount, String fromCurrency, String toCurrency) {
        // Implement conversion logic
        // For simplicity, let's assume a fixed conversion rate (this should be replaced with actual API call)
        double rate = getExchangeRate(fromCurrency, toCurrency);
        return amount * rate;
    }

    // Placeholder for getting exchange rate
    private double getExchangeRate(String fromCurrency, String toCurrency) {
        // In a real scenario, this method would call an API to get the current exchange rate
        if (fromCurrency.equals("USD") && toCurrency.equals("SEK")) {
            return 8.5;
        } else if (fromCurrency.equals("SEK") && toCurrency.equals("USD")) {
            return 0.12;
        } else if (fromCurrency.equals("EUR") && toCurrency.equals("SEK")) {
            return 10.0;
        } else if (fromCurrency.equals("SEK") && toCurrency.equals("EUR")) {
            return 0.10;
        } else {
            return 1.0; // Fallback exchange rate
        }
    }
}
