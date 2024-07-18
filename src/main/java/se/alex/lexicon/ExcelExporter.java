package se.alex.lexicon;

import com.google.gson.JsonElement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {
    public void exportDataToExcel(String fileName, JsonElement data) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("API Data");

        // Populate sheet with data (placeholder logic)
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("From Currency");
        header.createCell(1).setCellValue("To Currency");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Converted Amount");

        // Example data (replace with actual data handling)
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("USD");
        row.createCell(1).setCellValue("SEK");
        row.createCell(2).setCellValue(100);
        row.createCell(3).setCellValue(850);

        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
