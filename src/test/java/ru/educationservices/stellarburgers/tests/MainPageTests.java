package ru.educationservices.stellarburgers.tests;

import ru.educationservices.stellarburgers.constants.ApiUrls;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.educationservices.stellarburgers.pageobjects.MainPage;

import static ru.educationservices.stellarburgers.constants.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Проверки конструктора (главной страницы)")
public class MainPageTests {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    @Step("Запуск браузера и открытие главной страницы")
    public void startUp() {

        // Создание WebDriver через фабрику, браузер берется из системной переменной -Dbrowser
        driver = getWebDriver();
        driver.get(ApiUrls.URL_MAIN_PAGE);
        mainPage = new MainPage(driver);
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Step("Нажатие на вкладку Булки")
    @DisplayName("Проверка работы вкладки Булки в разделе с ингредиентами")
    @Description("Проверяется, что при переключении на вкладку 'Булки' ингредиенты прокручиваются до нужной позиции на странице.")
    public void checkNavBunsIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();

        mainPage.clickToppingsButton();
        mainPage.clickBunsButton();

        MatcherAssert.assertThat(
                "Ингредиенты не прокрутились до булок",
                mainPage.getBunsLocation(),
                equalTo(expectedLocation)
        );
    }

    @Test
    @Step("Нажатие на вкладку Соусы")
    @DisplayName("Проверка работы вкладки Соусы в разделе с ингредиентами")
    @Description("Проверяется, что при переключении на вкладку 'Соусы' ингредиенты прокручиваются до нужной позиции на странице.")
    public void checkNavToppingsIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();

        mainPage.clickToppingsButton();

        MatcherAssert.assertThat(
                "Ингредиенты не прокрутились до соусов",
                mainPage.getToppingsLocation(),
                equalTo(expectedLocation)
        );
    }

    @Test
    @Step("Нажатие на вкладку Начинки")
    @DisplayName("Проверка работы вкладки Начинки в разделе с ингредиентами")
    @Description("Проверяется, что при переключении на вкладку 'Начинки' ингредиенты прокручиваются до нужной позиции на странице.")
    public void checkNavFillingsIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();

        mainPage.clickFillingsButton();

        MatcherAssert.assertThat(
                "Ингредиенты не прокрутились до начинок",
                mainPage.getFillingsLocation(),
                equalTo(expectedLocation)
        );
    }
}
