package Assesment;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleSearch {
    public static void main(String[] args) {
        try {
            // Step 1: Setup WebDriver
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Step 2: Open the Excel File
            String filePath = "Resources/Excel.xlsx"; // Update with correct path
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);

            // Step 3: Get today's sheet
            String todaySheetName = getTodaySheetName();
            Sheet sheet = workbook.getSheet(todaySheetName);
            if (sheet == null) {
                System.out.println("No sheet found for today: " + todaySheetName);
                driver.quit();
                return;
            }

            // Step 4: Process each keyword
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                Cell keywordCell = row.getCell(1); // Column B: "Keyword"
                Cell keywordNameCell = row.getCell(2); // Column C: "Keyword Name"
                if (keywordCell == null || keywordNameCell == null) continue;

                String keyword = keywordNameCell.getStringCellValue();
                if (keyword.isEmpty()) continue;

                // Step 5: Perform Google Search
                driver.get("https://www.google.com/");
                WebElement searchBox = driver.findElement(By.name("q"));
                searchBox.sendKeys(keyword);
                Thread.sleep(2000); // Wait for suggestions
                List<WebElement> suggestions = driver.findElements(By.cssSelector("li.sbct span"));

                // Step 6: Find longest and shortest suggestions
                String longest = "", shortest = keyword;
                for (WebElement suggestion : suggestions) {
                    String text = suggestion.getText().trim();
                    if (!text.isEmpty()) {
                        if (text.length() > longest.length()) longest = text;
                        if (text.length() < shortest.length()) shortest = text;
                    }
                }

                // Step 7: Write back to Excel
                row.createCell(3, CellType.STRING).setCellValue(longest); // Column D: "Longest Option"
                row.createCell(4, CellType.STRING).setCellValue(shortest); // Column E: "Shortest Option"
            }

            // Step 8: Save Excel File
            file.close();
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            // Step 9: Close WebDriver
            driver.quit();
            System.out.println("Process completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to get today's sheet name
    private static String getTodaySheetName() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return switch (dayOfWeek) {
            case MONDAY -> "Monday";
            case TUESDAY -> "Tuesday";
            case WEDNESDAY -> "Wednesday";
            case THURSDAY -> "Thursday";
            case FRIDAY -> "Friday";
            case SATURDAY -> "Saturday";
            case SUNDAY -> "Sunday";
        };
    }
}
