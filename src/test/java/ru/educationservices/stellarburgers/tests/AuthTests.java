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
import ru.educationservices.stellarburgers.pageobjects.AuthPage;
import ru.educationservices.stellarburgers.pageobjects.ForgotPasswordPage;
import ru.educationservices.stellarburgers.pageobjects.MainPage;
import ru.educationservices.stellarburgers.pageobjects.RegisterPage;

import java.util.UUID;

import static ru.educationservices.stellarburgers.constants.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Авторизация пользователя")
public class AuthTests {
    private WebDriver webDriver;
    private AuthPage authPage;
    private MainPage mainPage;
    private RegisterPage registerPage;
    private ForgotPasswordPage forgotPasswordPage;
    private String name, email, password;
    private ApiClient apiClient;

    @Before
    @Step("Запуск браузера и подготовка тестовых данных")
    public void startUp() {

        webDriver = getWebDriver();

        webDriver.get(ApiUrls.URL_MAIN_PAGE);

        authPage = new AuthPage(webDriver);
        mainPage = new MainPage(webDriver);
        registerPage = new RegisterPage(webDriver);
        forgotPasswordPage = new ForgotPasswordPage(webDriver);

        name = "name";
        email = "email_" + UUID.randomUUID() + "@gmail.com";
        password = "pass_" + UUID.randomUUID();

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);

        apiClient = new ApiClient();
        apiClient.createUser(name, email, password);
    }

    @After
    @Step("Закрытие браузера и очистка данных")
    public void tearDown() {

        if (webDriver != null) {
            webDriver.quit();
        }

        if (apiClient != null) {
            apiClient.deleteTestUser(email, password);
        }
    }

    @Step("Процесс авторизации")
    private void authUser() {
        authPage.setEmail(email);
        authPage.setPassword(password);
        authPage.clickAuthButton();
        authPage.waitFormSubmitted();
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    @Description("Проверяется успешная авторизация пользователя с главной страницы через кнопку «Войти в аккаунт». После авторизации ожидается, что кнопка в корзине отображает надпись «Оформить заказ».")
    public void authFromMainIsSuccess() {
        mainPage.clickAuthButton();
        authPage.waitAuthFormVisible();
        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    @Description("Проверяется успешная авторизация пользователя через кнопку «Личный кабинет» на главной странице. После авторизации кнопка в корзине должна отображать надпись «Оформить заказ».")
    public void authFromLinkToProfileIsSuccess() {
        mainPage.clickLinkToProfile();
        authPage.waitAuthFormVisible();
        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверяется успешная авторизация пользователя через ссылку авторизации на форме регистрации. После авторизации кнопка в корзине должна отображать надпись «Оформить заказ».")
    public void authLinkFromRegFormIsSuccess() {
        webDriver.get(ApiUrls.URL_REGISTER_PAGE);
        registerPage.clickAuthLink();
        authPage.waitAuthFormVisible();
        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверяется успешная авторизация пользователя через ссылку авторизации на форме восстановления пароля. После авторизации кнопка в корзине должна отображать надпись «Оформить заказ».")
    public void authLinkFromForgotPasswordFormIsSuccess() {
        webDriver.get(ApiUrls.URL_FORGOT_PASSWORD_PAGE);
        forgotPasswordPage.clickAuthLink();
        authPage.waitAuthFormVisible();
        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }
}