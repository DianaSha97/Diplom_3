package ru.educationservices.stellarburgers.tests;

import ru.educationservices.stellarburgers.handlers.ApiClient;
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
import ru.educationservices.stellarburgers.pageobjects.RegisterPage;

import java.util.UUID;

import static ru.educationservices.stellarburgers.constants.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Регистрация пользователя")
public class RegisterPageTests {
    private WebDriver webDriver;
    private RegisterPage registerPage;
    private String email, name, password;

    @Before
    @Step("Запуск браузера и подготовка тестовых данных")
    public void startUp() {

        // Создание WebDriver через фабрику, браузер берется из системной переменной -Dbrowser
        webDriver = getWebDriver();
        webDriver.get(ApiUrls.URL_REGISTER_PAGE);
        registerPage = new RegisterPage(webDriver);

        email = "email_" + UUID.randomUUID() + "@gmail.com";
        name = "name";
        password = "pass_" + UUID.randomUUID();

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);
    }

    @After
    @Step("Закрытие браузера и очистка данных")
    public void tearDown() {
        webDriver.quit();
        new ApiClient().deleteTestUser(email, password);
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверяется успешная регистрация нового пользователя. После регистрации форма должна перезагрузиться и открыться страница авторизации.")
    public void registerNewUserIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        registerPage.setEmail(email);
        registerPage.setName(name);
        registerPage.setPassword(password);

        registerPage.clickRegisterButton();
        registerPage.waitFormSubmitted("Вход");

        checkFormReload();
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    @Description("Проверяется, что регистрация с паролем меньше минимальной длины завершается ошибкой. На форме должно появиться сообщение 'Некорректный пароль'.")
    public void registerNewUserLowPasswordIsFailed() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        registerPage.setEmail(email);
        registerPage.setName(name);
        registerPage.setPassword(password.substring(0, 5));

        registerPage.clickRegisterButton();
        registerPage.waitErrorIsVisible();

        checkErrorMessage();
    }

    @Step("Проверка перезагрузки формы регистрации")
    private void checkFormReload() {
        MatcherAssert.assertThat(
                "Форма регистрации не перезагрузилась",
                webDriver.getCurrentUrl(),
                containsString("/login")
        );
    }

    @Step("Проверка появления сообщения об ошибке")
    private void checkErrorMessage() {
        MatcherAssert.assertThat(
                "Некорректное сообщение об ошибке",
                registerPage.getErrorMessage(),
                equalTo("Некорректный пароль")
        );
    }
}
