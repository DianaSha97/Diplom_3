package ru.educationservices.stellarburgers.constants;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class WebDriverFactory {

    public static WebDriver getWebDriver() {

        WebDriver driver;
        String browser = System.getProperty("browser", "chrome");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        if ("yandex".equalsIgnoreCase(browser)) {

            WebDriverManager.chromedriver().browserVersion("144").setup();

            options.setBinary("C:\\Program Files (x86)\\Yandex\\YandexBrowser\\Application\\browser.exe");

            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");

            driver = new ChromeDriver(options);

        } else {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;
    }
}
