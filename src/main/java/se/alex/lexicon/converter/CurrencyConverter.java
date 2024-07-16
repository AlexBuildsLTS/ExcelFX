package se.alex.lexicon.converter;

import se.alex.lexicon.api.ApiClient;
import java.io.IOException;

public class CurrencyConverter {

    public static double convert(String api, String parameter, String fromDate, String toDate, double amount) throws IOException {
        double rate = ApiClient.getExchangeRate(api, parameter, fromDate, toDate);
        return amount * rate;
    }
}
