package se.alex.lexicon.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.alex.lexicon.CurrencyConverter;
import se.alex.lexicon.ExcelFetcher;

public class SimpleFX extends Application {

    @Override
    public void start(Stage stage) {
        // ComboBox for API selection
        ComboBox<String> apiBox = new ComboBox<>();
        apiBox.getItems().addAll(
                "SWESTR Interest Rate",
                "SWESTR All Interest Rates",
                "SWESTR Average Latest",
                "SWESTR Index Latest",
                "SWESTR Latest Interest Rate",
                "SWESTR Average",
                "SWESTR Index"
        );
        apiBox.setPromptText("API");

        // ComboBox for 'from' currency
        ComboBox<String> fromCurrencyBox = new ComboBox<>();
        fromCurrencyBox.getItems().addAll("USD", "EUR", "SEK");
        fromCurrencyBox.setPromptText("From Currency");

        // ComboBox for 'to' currency
        ComboBox<String> toCurrencyBox = new ComboBox<>();
        toCurrencyBox.getItems().addAll("USD", "EUR", "SEK");
        toCurrencyBox.setPromptText("To Currency");

        // TextField for amount
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        // Button for converting currency
        Button convertButton = new Button("Convert");

        // Button for exporting to Excel
        Button exportButton = new Button("Export to Excel");

        // Label to show result
        Label resultLabel = new Label();

        // Label to show export status
        Label exportStatusLabel = new Label();

        // Set up the layout
        VBox layout = new VBox(10,
                new Label("API:"), apiBox,
                new Label("From Currency:"), fromCurrencyBox,
                new Label("To Currency:"), toCurrencyBox,
                new Label("Amount:"), amountField,
                convertButton, exportButton, resultLabel, exportStatusLabel);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");

        Scene scene = new Scene(layout, 400, 500);

        // Set the stage
        stage.setScene(scene);
        stage.setTitle("Currency Converter");
        stage.show();

        // Set button actions
        convertButton.setOnAction(event -> {
            String fromCurrency = fromCurrencyBox.getValue();
            String toCurrency = toCurrencyBox.getValue();
            double amount = Double.parseDouble(amountField.getText());
            CurrencyConverter converter = new CurrencyConverter();
            double convertedAmount = converter.convert(amount, fromCurrency, toCurrency);
            resultLabel.setText(String.format("Converted Amount: %.2f %s", convertedAmount, toCurrency));
        });

        exportButton.setOnAction(event -> {
            String endpoint = apiBox.getValue();
            String fileName = "exchange_rates.xlsx";
            ExcelFetcher fetcher = new ExcelFetcher();
            fetcher.fetchAndExport(endpoint, fileName);
            exportStatusLabel.setText("Data exported successfully.");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
