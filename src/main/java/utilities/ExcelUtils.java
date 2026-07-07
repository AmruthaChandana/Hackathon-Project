package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger logger =
            LogManager.getLogger(
                    ExcelUtils.class);

    private static Workbook workbook;
    private static Sheet sheet;

    private static final DataFormatter formatter =
            new DataFormatter();

    public static void loadExcel(
            String excelPath,
            String sheetName) {

        try (FileInputStream fis =
                     new FileInputStream(
                             excelPath)) {

            workbook =
                    new XSSFWorkbook(
                            fis);

            sheet =
                    workbook.getSheet(
                            sheetName);

            if (sheet == null) {

                logger.error(
                        "Sheet not found: {}",
                        sheetName);

                throw new RuntimeException(
                        "Sheet not found: "
                                + sheetName);
            }

            logger.info(
                    "Excel loaded successfully. Sheet: {}",
                    sheetName);

        } catch (Exception e) {

            logger.error(
                    "Unable to load Excel file: {}",
                    excelPath,
                    e);

            throw new RuntimeException(
                    "Unable to load Excel file: "
                            + e.getMessage(),
                    e);
        }
    }

    public static String getCellData(
            String testCaseId,
            String columnName) {

        validateSheetLoaded();

        Row headerRow =
                sheet.getRow(0);

        if (headerRow == null) {

            logger.error(
                    "Header row is missing in Excel sheet.");

            throw new RuntimeException(
                    "Header row is missing in Excel sheet.");
        }

        int testCaseIdColumnIndex =
                -1;

        int requiredColumnIndex =
                -1;

        for (int i = 0;
             i < headerRow.getPhysicalNumberOfCells();
             i++) {

            Cell headerCell =
                    headerRow.getCell(i);

            if (headerCell == null) {

                continue;
            }

            String headerValue =
                    getCellValueAsString(
                            headerCell);

            if (headerValue.equalsIgnoreCase(
                    "TestCaseID")) {

                testCaseIdColumnIndex =
                        i;
            }

            if (headerValue.equalsIgnoreCase(
                    columnName)) {

                requiredColumnIndex =
                        i;
            }
        }

        if (testCaseIdColumnIndex == -1) {

            logger.error(
                    "TestCaseID column not found.");

            throw new RuntimeException(
                    "TestCaseID column not found in Excel");
        }

        if (requiredColumnIndex == -1) {

            logger.error(
                    "Column not found: {}",
                    columnName);

            throw new RuntimeException(
                    "Column not found in Excel: "
                            + columnName);
        }

        int rowCount =
                sheet.getPhysicalNumberOfRows();

        for (int i = 1;
             i < rowCount;
             i++) {

            Row row =
                    sheet.getRow(i);

            if (row == null) {

                continue;
            }

            Cell testCaseCell =
                    row.getCell(
                            testCaseIdColumnIndex);

            if (testCaseCell == null) {

                continue;
            }

            String currentTestCaseId =
                    getCellValueAsString(
                            testCaseCell);

            if (currentTestCaseId.equalsIgnoreCase(
                    testCaseId)) {

                Cell requiredCell =
                        row.getCell(
                                requiredColumnIndex);

                String value =
                        getCellValueAsString(
                                requiredCell);

                logger.debug(
                        "Excel Data Retrieved -> TestCaseID: {}, Column: {}, Value: {}",
                        testCaseId,
                        columnName,
                        value);

                return value;
            }
        }

        logger.error(
                "TestCase ID not found: {}",
                testCaseId);

        throw new RuntimeException(
                "TestCase ID not found in Excel: "
                        + testCaseId);
    }

    public static Object[][] getAllTestData() {

        validateSheetLoaded();

        Row headerRow =
                sheet.getRow(0);

        if (headerRow == null) {

            logger.error(
                    "Header row is missing in Excel sheet.");

            throw new RuntimeException(
                    "Header row is missing in Excel sheet.");
        }

        int rowCount =
                sheet.getPhysicalNumberOfRows();

        int colCount =
                headerRow.getPhysicalNumberOfCells();

        Object[][] data =
                new Object[rowCount - 1][1];

        for (int i = 1;
             i < rowCount;
             i++) {

            Map<String, String> rowData =
                    new HashMap<>();

            Row dataRow =
                    sheet.getRow(i);

            if (dataRow == null) {

                data[i - 1][0] =
                        rowData;

                continue;
            }

            for (int j = 0;
                 j < colCount;
                 j++) {

                String key =
                        getCellValueAsString(
                                headerRow.getCell(j));

                String value =
                        getCellValueAsString(
                                dataRow.getCell(j));

                rowData.put(
                        key,
                        value);
            }

            data[i - 1][0] =
                    rowData;
        }

        logger.info(
                "Loaded {} rows of test data from Excel.",
                rowCount - 1);

        return data;
    }

    private static String getCellValueAsString(
            Cell cell) {

        if (cell == null) {

            logger.debug(
                    "Encountered null cell while reading Excel.");

            return "CELL_EMPTY";
        }

        return formatter
                .formatCellValue(cell)
                .trim();
    }

    private static void validateSheetLoaded() {

        if (sheet == null) {

            logger.error(
                    "Excel sheet is not loaded.");

            throw new RuntimeException(
                    "Excel sheet is not loaded. Please call loadExcel() first.");
        }
    }
}