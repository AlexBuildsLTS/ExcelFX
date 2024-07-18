package se.alex.lexicon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Riksbank API Data to Excel");

        // Create a button to start the process
        Button btn = new Button();
        btn.setText("Fetch Data and Export to Excel");
        btn.setOnAction(event -> {
            // Fetch data and export to Excel
            ExcelFetcher fetcher = new ExcelFetcher();
            String endpoint = "https://api.riksbank.se/path_to_endpoint"; // Replace with actual endpoint
            String fileName = "output.xlsx";

            fetcher.fetchAndExport(endpoint, fileName);
        });

        // Create a layout and add the button to it
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        // Create a scene with the layout
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
