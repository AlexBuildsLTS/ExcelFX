package se.alex.lexicon;

import com.google.gson.JsonElement;

import java.io.IOException;

public class ExcelFetcher {
    private ApiClient apiClient;
    private ExcelExporter excelExporter;

    public ExcelFetcher() {
        this.apiClient = new ApiClient();
        this.excelExporter = new ExcelExporter();
    }

    public void fetchAndExport(String endpoint, String fileName) {
        try {
            JsonElement data = apiClient.fetchData(endpoint);
            excelExporter.exportDataToExcel(fileName, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
