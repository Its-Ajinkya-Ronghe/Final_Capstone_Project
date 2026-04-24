package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {
    private static String path = "src/test/resources/testdata/para_bank.xlsx";

    public static Map<String, String> getRowData(int rowNum) {
        Map<String, String> rowData = new HashMap<>();
        DataFormatter formatter = new DataFormatter();
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            Row targetRow = sheet.getRow(rowNum);
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String key = headerRow.getCell(i).getStringCellValue().trim();
                String value = formatter.formatCellValue(targetRow.getCell(i)).trim();
                rowData.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowData;
    }
}