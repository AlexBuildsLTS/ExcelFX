package se.alex.lexicon.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Override
    public void start(Stage stage) {
        amountField = new TextField();
        fromCurrencyBox = new ComboBox<>();
        toCurrencyBox = new ComboBox<>();
        apiBox = new ComboBox<>();
        parameterField = new TextField();
        fromDateField = new TextField();
        toDateField = new TextField();
        convertButton = new Button("Convert");
        exportButton = new Button("Export to Excel");
        resultLabel = new Label();

        VBox layout = new VBox(10, amountField, fromCurrencyBox, toCurrencyBox, apiBox, parameterField, fromDateField, toDateField, convertButton, exportButton, resultLabel);
        Scene scene = new Scene(layout, 300, 400);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void should_contain_ui_elements() {
        verifyThat(".text-field", isVisible());
        verifyThat(".combo-box", isVisible());
        verifyThat(".button", isVisible());
        verifyThat(".label", isVisible());
    }

    @Test
    public void should_convert_currency() {
        clickOn(amountField).write("100");
        clickOn(fromCurrencyBox).clickOn("USD");
        clickOn(toCurrencyBox).clickOn("SEK");
        clickOn(apiBox).clickOn("SWESTR Interest Rate");
        clickOn(parameterField).write("SEK");
        clickOn(fromDateField).write("2023-01-01");
        clickOn(toDateField).write("2023-12-31");

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
        clickOn(parameterField).write("SEK");
        clickOn(fromDateField).write("2023-01-01");
        clickOn(toDateField).write("2023-12-31");

        clickOn(exportButton);

        // Assuming the resultLabel gets updated with the export confirmation
        verifyThat(resultLabel, hasText("Exported to exchange_rates.xlsx"));
    }
}
