package se.alex.lexicon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.alex.lexicon.api.ApiClient;
import se.alex.lexicon.converter.CurrencyConverter;
import se.alex.lexicon.exporter.ExcelExporter;

import java.io.File;

public class JavaFXApp extends Application {

    private CurrencyConverter converter;
    private ExcelExporter excelExporter;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        // Initialize converter and exporter
        converter = new CurrencyConverter();
        excelExporter = new ExcelExporter();

        // Labels
        Label fromCurrencyLabel = new Label("From Currency:");
        Label toCurrencyLabel = new Label("To Currency:");
        Label amountLabel = new Label("Amount:");
        Label apiLabel = new Label("Select API:");

        // TextField for amount
        TextField amountField = new TextField();

        // ComboBoxes for currencies
        ComboBox<String> fromCurrency = new ComboBox<>();
        ComboBox<String> toCurrency = new ComboBox<>();
        ComboBox<String> apiSelector = new ComboBox<>();

        // Add currency options
        fromCurrency.getItems().addAll("USD", "SEK", "EUR", "KRW", "NOK", "DKK");
        toCurrency.getItems().addAll("USD", "SEK", "EUR", "KRW", "NOK", "DKK");

        // Add API options
        apiSelector.getItems().addAll("SWESTR Interest Rate", "SWESTR All Interest Rates", "SWESTR Average Latest", "SWESTR Index Latest", "SWESTR Latest Interest Rate", "SWESTR Average", "SWESTR Index");

        // Button for conversion
        Button convertButton = new Button("Convert");
        Label resultLabel = new Label();

        // Button for exporting to Excel
        Button exportButton = new Button("Export to Excel");

        // Button for editing an existing Excel file
        Button editButton = new Button("Edit Existing Excel");

        // Set action for convert button
        convertButton.setOnAction(e -> {
            try {
                String from = fromCurrency.getValue();
                String to = toCurrency.getValue();
                double amount = Double.parseDouble(amountField.getText());
                String selectedApi = apiSelector.getValue();

                double result = converter.convert(from, to, amount, selectedApi);
                resultLabel.setText(String.format("Converted Amount: %.2f %s", result, to));
            } catch (Exception ex) {
                resultLabel.setText("Error in conversion: " + ex.getMessage());
            }
        });

        // Set action for export button
        exportButton.setOnAction(e -> {
            try {
                String from = fromCurrency.getValue();
                String to = toCurrency.getValue();
                double amount = Double.parseDouble(amountField.getText());
                double convertedAmount = Double.parseDouble(resultLabel.getText().split(" ")[2]);
                File file = new File("conversion_results.xlsx");

                excelExporter.exportToExcel(file.getPath(), from, to, amount, convertedAmount);
                resultLabel.setText("Exported successfully to " + file.getPath());
            } catch (Exception ex) {
                resultLabel.setText("Error in exporting: " + ex.getMessage());
            }
        });

        // Set action for edit button
        editButton.setOnAction(e -> {
            try {
                String from = fromCurrency.getValue();
                String to = toCurrency.getValue();
                double amount = Double.parseDouble(amountField.getText());
                double convertedAmount = Double.parseDouble(resultLabel.getText().split(" ")[2]);
                File file = new File("conversion_results.xlsx");

                excelExporter.editExcel(file.getPath(), from, to, amount, convertedAmount);
                resultLabel.setText("Edited successfully in " + file.getPath());
            } catch (Exception ex) {
                resultLabel.setText("Error in editing: " + ex.getMessage());
            }
        });

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                fromCurrencyLabel, fromCurrency,
                toCurrencyLabel, toCurrency,
                amountLabel, amountField,
                apiLabel, apiSelector,
                convertButton, resultLabel,
                exportButton, editButton
        );

        Scene scene = new Scene(layout, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
