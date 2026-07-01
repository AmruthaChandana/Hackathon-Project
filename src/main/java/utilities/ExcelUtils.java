package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;

    public static void loadExcel(String excelPath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to load Excel file: " + e.getMessage());
        }
    }

    public static String getCellData(String testCaseId, String columnName) {

        if (sheet == null) {
            throw new RuntimeException("Excel sheet is not loaded. Please call loadExcel() first.");
        }

        int rowCount = sheet.getPhysicalNumberOfRows();

        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            throw new RuntimeException("Header row is missing in Excel sheet.");
        }

        int testCaseIdColumnIndex = -1;
        int requiredColumnIndex = -1;

        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {

            Cell headerCell = headerRow.getCell(i);

            if (headerCell == null) {
                continue;
            }

            String headerValue = getCellValueAsString(headerCell);

            if (headerValue.equalsIgnoreCase("TestCaseID")) {
                testCaseIdColumnIndex = i;
            }

            if (headerValue.equalsIgnoreCase(columnName)) {
                requiredColumnIndex = i;
            }
        }

        if (testCaseIdColumnIndex == -1) {
            throw new RuntimeException("TestCaseID column not found in Excel");
        }

        if (requiredColumnIndex == -1) {
            throw new RuntimeException("Column not found in Excel: " + columnName);
        }

        for (int i = 1; i < rowCount; i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            Cell testCaseCell = row.getCell(testCaseIdColumnIndex);

            if (testCaseCell == null) {
                continue;
            }

            String currentTestCaseId = getCellValueAsString(testCaseCell);

            if (currentTestCaseId.equalsIgnoreCase(testCaseId)) {

                Cell requiredCell = row.getCell(requiredColumnIndex);

                return getCellValueAsString(requiredCell);
            }
        }

        throw new RuntimeException("TestCase ID not found in Excel: " + testCaseId);
    }

    public static Object[][] getAllTestData() {

        if (sheet == null) {
            throw new RuntimeException("Excel sheet is not loaded. Please call loadExcel() first.");
        }

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][1];

        Row headerRow = sheet.getRow(0);

        for (int i = 1; i < rowCount; i++) {

            Map<String, String> rowData = new HashMap<>();

            Row dataRow = sheet.getRow(i);

            if (dataRow == null) {
                continue;
            }

            for (int j = 0; j < colCount; j++) {

                String key = getCellValueAsString(headerRow.getCell(j));
                String value = getCellValueAsString(dataRow.getCell(j));

                rowData.put(key, value);
            }

            data[i - 1][0] = rowData;
        }

        return data;
    }

    private static String getCellValueAsString(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell).trim();
    }
}
