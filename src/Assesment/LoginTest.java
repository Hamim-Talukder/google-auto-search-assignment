package Assesment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

    public static void main(String[] args) throws InterruptedException {
        // Set the path to your ChromeDriver
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        // Arrays of usernames and passwords
        String[] usernames = {
                "csehamim@gmail.com", "user2@example.com", "user3@example.com",
                "user4@example.com", "user5@example.com", "user6@example.com",
                "admin@email.com", "csehamim@gmail.com", "csehamim@gmail.com", "admin@email.com"
        };

        String[] passwords = {
                "9577", "password2", "password3", "password4", "password5",
                "password6", "9577", "9511", "9577", "9577"
        };

        // Loop through each username/password pair
        for (int i = 0; i < usernames.length; i++) {
            // Start new browser session
            WebDriver driver = new ChromeDriver();

            try {
                // Open login page
                driver.get("http://182.160.97.196:8088/studentportal/authorise/login");
                driver.manage().window().maximize();
                Thread.sleep(1000); // Wait for page load

                // Find fields and fill in credentials
                WebElement studentIdInput = driver.findElement(By.id("username"));
                WebElement passwordInput = driver.findElement(By.id("password"));
                WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));

                studentIdInput.clear();
                studentIdInput.sendKeys(usernames[i]);

                passwordInput.clear();
                passwordInput.sendKeys(passwords[i]);

                loginBtn.click();

                Thread.sleep(2000); // Wait for page response

                // Validate login result
                String currentUrl = driver.getCurrentUrl();
                if (currentUrl.contains("dashboard") || currentUrl.contains("home")) {
                    System.out.println("✅ Login successful for: " + usernames[i]);
                } else {
                    System.out.println("❌ Login failed for: " + usernames[i]);
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error occurred for user: " + usernames[i]);
                e.printStackTrace();
            } finally {
                // Close the browser
                driver.quit();
            }
        }
    }
}