package se.alex.lexicon;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class AppTest extends ApplicationTest {

    private TextField amountField;
    private ComboBox<String> fromCurrencyBox;
    private ComboBox<String> toCurrencyBox;
    private ComboBox<String> apiBox;
    private TextField parameterField;
    private TextField fromDateField;
    private TextField toDateField;
    private Button convertButton;
    private Button exportButton;
    private Label resultLabel;
    private Label exportStatusLabel;

    @Override
    public void start(Stage stage) {
        apiBox = new ComboBox<>();
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

        fromCurrencyBox = new ComboBox<>();
        fromCurrencyBox.getItems().addAll("USD", "EUR", "SEK");
        fromCurrencyBox.setPromptText("From Currency");

        toCurrencyBox = new ComboBox<>();
        toCurrencyBox.getItems().addAll("USD", "EUR", "SEK");
        toCurrencyBox.setPromptText("To Currency");

        amountField = new TextField();
        amountField.setPromptText("Amount");

        convertButton = new Button("Convert");
        exportButton = new Button("Export to Excel");

        resultLabel = new Label();
        exportStatusLabel = new Label();

        VBox layout = new VBox(10,
                new Label("API:"), apiBox,
                new Label("From Currency:"), fromCurrencyBox,
                new Label("To Currency:"), toCurrencyBox,
                new Label("Amount:"), amountField,
                convertButton, exportButton, resultLabel, exportStatusLabel);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");

        Scene scene = new Scene(layout, 400, 500);

        stage.setScene(scene);
        stage.setTitle("Currency Converter");
        stage.show();
    }

    @Test
    public void should_contain_ui_elements() {
        verifyThat(".combo-box", isVisible());
        verifyThat(".text-field", isVisible());
        verifyThat(".button", isVisible());
        verifyThat(".label", isVisible());
    }

    @Test
    public void should_convert_currency() {
        clickOn(amountField).write("100");
        clickOn(fromCurrencyBox).clickOn("USD");
        clickOn(toCurrencyBox).clickOn("SEK");
        clickOn(apiBox).clickOn("SWESTR Interest Rate");

        clickOn(convertButton);

        // Assuming the resultLabel gets updated with the converted amount
        verifyThat(resultLabel, hasText("Converted amount..."));  // Adjust the expected text as necessary
    }

    @Test
    public void should_export_to_excel() {
        clickOn(amountField).write("100");
        clickOn(fromCurrencyBox).clickOn("USD");
        clickOn(toCurrencyBox).clickOn("SEK");
        clickOn(apiBox).clickOn("SWESTR Interest Rate");

        clickOn(exportButton);

        // Assuming the exportStatusLabel gets updated with the export confirmation
        verifyThat(exportStatusLabel, hasText("Data exported successfully."));
    }
}
