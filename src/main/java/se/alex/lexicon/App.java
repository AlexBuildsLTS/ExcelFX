package se.alex.lexicon;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import se.alex.lexicon.converter.CurrencyConverter;
import se.alex.lexicon.exporter.ExcelExporter;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label fromCurrencyLabel = new Label("From Currency:");
        TextField fromCurrencyField = new TextField();

        Label toCurrencyLabel = new Label("To Currency:");
        TextField toCurrencyField = new TextField();

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        Label resultLabel = new Label("Result:");
        TextField resultField = new TextField();
        resultField.setEditable(false);

        Label apiEndpointLabel = new Label("API Endpoint:");
        ComboBox<String> apiEndpointComboBox = new ComboBox<>();
        apiEndpointComboBox.getItems().addAll(
                "SWESTR Interest Rate",
                "SWESTR All Interest Rates",
                "SWESTR Average Latest",
                "SWESTR Index Latest",
                "SWESTR Latest Interest Rate",
                "SWESTR Average",
                "SWESTR Index"
        );

        Button convertButton = new Button("Convert");
        Button exportButton = new Button("Export to Excel");

        // Set button actions
        convertButton.setOnAction(e -> {
            String fromCurrency = fromCurrencyField.getText();
            String toCurrency = toCurrencyField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String selectedEndpoint = apiEndpointComboBox.getValue();
            CurrencyConverter converter = new CurrencyConverter();
            double result = converter.convert(selectedEndpoint, fromCurrency, toCurrency, amount);
            resultField.setText(String.valueOf(result));
        });

        exportButton.setOnAction(e -> {
            ExcelExporter exporter = new ExcelExporter();
            // Create sample data to export (this should be your actual data)
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("FromCurrency", fromCurrencyField.getText());
            rowData.put("ToCurrency", toCurrencyField.getText());
            rowData.put("Amount", Double.parseDouble(amountField.getText()));
            rowData.put("Result", Double.parseDouble(resultField.getText()));
            exporter.exportToExcel("output.xlsx", List.of(rowData));
        });

        // Arrange components in a grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        grid.add(fromCurrencyLabel, 0, 0);
        grid.add(fromCurrencyField, 1, 0);
        grid.add(toCurrencyLabel, 0, 1);
        grid.add(toCurrencyField, 1, 1);
        grid.add(amountLabel, 0, 2);
        grid.add(amountField, 1, 2);
        grid.add(resultLabel, 0, 3);
        grid.add(resultField, 1, 3);
        grid.add(apiEndpointLabel, 0, 4);
        grid.add(apiEndpointComboBox, 1, 4);
        grid.add(convertButton, 0, 5);
        grid.add(exportButton, 1, 5);

        // Create and set the scene
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Currency Converter");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
