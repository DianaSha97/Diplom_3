package ru.educationservices.stellarburgers.constants;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver getWebDriver(String browserName) {

        // читаем параметры из запуска
        String browser = System.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        ChromeOptions options = new ChromeOptions();

        // headless режим
        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);

            case "yandex":
                // используем ChromeDriver + бинарник Яндекса
                WebDriverManager.chromedriver().setup();

                String programFiles = System.getenv("ProgramFiles(x86)");
                options.setBinary(programFiles + "\\Yandex\\YandexBrowser\\Application\\browser.exe");

                return new ChromeDriver(options);

            default:
                throw new RuntimeException("Unknown browser: " + browser);
        }
    }
}
