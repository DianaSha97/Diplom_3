package ru.educationservices.stellarburgers.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By headerLinks = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    private final By basketButton = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket__container')]/button");
    private final By ingredientsButtons = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div");
    private final By ingredientsTitles = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients__menuContainer')]/h2");
    private final By header = By.xpath(".//main//h1");
    private final By modalOverlay = By.xpath(".//div[contains(@class,'Modal_modal_overlay')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitOverlayDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
    }

    public void waitHeaderVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    private List<WebElement> getIngredientsButtons() {
        return driver.findElements(ingredientsButtons);
    }

    private List<WebElement> getIngredientsTitles() {
        return driver.findElements(ingredientsTitles);
    }

    public void clickAuthButton() {
        waitOverlayDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(basketButton)).click();
    }

    public void clickLinkToProfile() {
        waitOverlayDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(headerLinks));
        driver.findElements(headerLinks).get(2).click();
    }

    public void clickBunsButton() {
        clickIngredientTab(0);
    }

    public void clickToppingsButton() {
        clickIngredientTab(1);
    }

    public void clickFillingsButton() {
        clickIngredientTab(2);
    }

    private void clickIngredientTab(int index) {
        waitOverlayDisappear();
        WebElement tab = getIngredientsButtons().get(index);
        wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
        waitIngredientsScrolled(index);
    }

    private void waitIngredientsScrolled(int index) {
        int expected = getIngredientTitleExpectedLocation();

        wait.until(driver -> {
            int actual = getIngredientsTitles().get(index).getLocation().getY();
            return Math.abs(actual - expected) < 15;
        });
    }

    public String getBasketButtonText() {
        return driver.findElement(basketButton).getText();
    }

    public int getIngredientTitleExpectedLocation() {
        WebElement firstTab = getIngredientsButtons().get(0);
        return firstTab.getLocation().getY() + firstTab.getSize().getHeight();
    }

    public int getBunsLocation() {
        return getIngredientsTitles().get(0).getLocation().getY();
    }

    public int getToppingsLocation() {
        return getIngredientsTitles().get(1).getLocation().getY();
    }

    public int getFillingsLocation() {
        return getIngredientsTitles().get(2).getLocation().getY();
    }
}
