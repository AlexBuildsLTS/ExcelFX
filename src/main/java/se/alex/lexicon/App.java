package se.alex.lexicon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.alex.lexicon.converter.CurrencyConverter;
import se.alex.lexicon.exporter.ExcelExporter;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // UI components setup
        Label label = new Label("Enter amount to convert:");
        TextField amountField = new TextField();
        ComboBox<String> fromCurrencyBox = new ComboBox<>();
        ComboBox<String> toCurrencyBox = new ComboBox<>();
        ComboBox<String> apiBox = new ComboBox<>();
        TextField parameterField = new TextField("Enter parameter");
        TextField fromDateField = new TextField("Enter fromDate (YYYY-MM-DD)");
        TextField toDateField = new TextField("Enter toDate (optional)");
        Button convertButton = new Button("Convert");
        Button exportButton = new Button("Export to Excel");
        Label resultLabel = new Label();

        fromCurrencyBox.getItems().addAll("USD", "SEK", "EUR"); // Add more currencies as needed
        toCurrencyBox.getItems().addAll("USD", "SEK", "EUR");

        apiBox.getItems().addAll(
                "SWESTR Interest Rate",
                "SWESTR All Interest Rates",
                "SWESTR Average Latest",
                "SWESTR Index Latest",
                "SWESTR Latest Interest Rate",
                "SWESTR Average",
                "SWESTR Index"
        );

        // Event handlers
        convertButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String selectedApi = apiBox.getValue();
                String parameter = parameterField.getText();
                String fromDate = fromDateField.getText();
                String toDate = toDateField.getText();
                double result = CurrencyConverter.convert(selectedApi, parameter, fromDate, toDate, amount);
                resultLabel.setText(String.format("%.2f", result));
            } catch (IOException | NumberFormatException ex) {
                resultLabel.setText("Error: " + ex.getMessage());
                ex.printStackTrace(); // Print the stack trace for debugging
            }
        });

        exportButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String selectedApi = apiBox.getValue();
                String parameter = parameterField.getText();
                String fromDate = fromDateField.getText();
                String toDate = toDateField.getText();
                double result = CurrencyConverter.convert(selectedApi, parameter, fromDate, toDate, amount);
                ExcelExporter.export("exchange_rates.xlsx", selectedApi, parameter, amount, result);
                resultLabel.setText("Exported to exchange_rates.xlsx");
            } catch (IOException | NumberFormatException ex) {
                resultLabel.setText("Error: " + ex.getMessage());
                ex.printStackTrace(); // Print the stack trace for debugging
            }
        });

        // Layout setup
        VBox layout = new VBox(10, label, amountField, fromCurrencyBox, toCurrencyBox, apiBox, parameterField, fromDateField, toDateField, convertButton, exportButton, resultLabel);
        Scene scene = new Scene(layout, 300, 400);

        primaryStage.setTitle("BankApiRate Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
