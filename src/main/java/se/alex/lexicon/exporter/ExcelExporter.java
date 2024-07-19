package se.alex.lexicon.exporter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelExporter {

    public void exportToExcel(String filePath, String from, String to, double amount, double convertedAmount) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Conversion");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("From Currency");
        header.createCell(1).setCellValue("To Currency");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Converted Amount");

        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue(from);
        row.createCell(1).setCellValue(to);
        row.createCell(2).setCellValue(amount);
        row.createCell(3).setCellValue(convertedAmount);

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
    }

    public void editExcel(String filePath, String from, String to, double amount, double convertedAmount) throws IOException {
        try (FileInputStream fileIn = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(fileIn);
            Sheet sheet = workbook.getSheetAt(0);

            int lastRowNum = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRowNum + 1);
            row.createCell(0).setCellValue(from);
            row.createCell(1).setCellValue(to);
            row.createCell(2).setCellValue(amount);
            row.createCell(3).setCellValue(convertedAmount);

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }
}
