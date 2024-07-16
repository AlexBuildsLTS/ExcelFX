package se.alex.lexicon.exporter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {

    public static void export(String filePath, String api, String parameter, double amount, double result) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Exchange Rates");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("API");
        header.createCell(1).setCellValue("Parameter");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Converted Amount");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(api);
        dataRow.createCell(1).setCellValue(parameter);
        dataRow.createCell(2).setCellValue(amount);
        dataRow.createCell(3).setCellValue(result);

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
    }
}
