package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    // Конструктор
    @FindBy(how = How.XPATH, using = "//h1[text()='Соберите бургер']")
    private WebElement constructorHeader;

    // Кнопка "Войти в аккаунт"
    @FindBy(how = How.XPATH, using = "//button[text()='Войти в аккаунт']")
    private WebElement loginButton;

    // Кнопка "Личный кабинет"
    @FindBy(how = How.XPATH, using = "//p[text()='Личный Кабинет']")
    private WebElement personalAccountButton;

    // Кнопка "Оформить заказ"
    @FindBy(how = How.XPATH, using = "//button[text()='Оформить заказ']")
    private WebElement makeOrderButton;

    // Разделы конструктора
    @FindBy(how = How.XPATH, using = "//span[text()='Булки']/parent::div")
    private WebElement bunsSection;

    @FindBy(how = How.XPATH, using = "//span[text()='Соусы']/parent::div")
    private WebElement saucesSection;

    @FindBy(how = How.XPATH, using = "//span[text()='Начинки']/parent::div")
    private WebElement fillingsSection;

    // Индикаторы активных разделов
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Булки']")
    private WebElement activeBunsTab;

    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Соусы']")
    private WebElement activeSaucesTab;

    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Начинки']")
    private WebElement activeFillingsTab;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Нажать кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }

    @Step("Нажать кнопку 'Личный кабинет'")
    public void clickPersonalAccountButton() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(personalAccountButton))
                .click();
    }

    @Step("Нажать раздел 'Булки'")
    public void clickBunsSection() {
        bunsSection.click();
    }

    @Step("Нажать раздел 'Соусы'")
    public void clickSaucesSection() {
        saucesSection.click();
    }

    @Step("Нажать раздел 'Начинки'")
    public void clickFillingsSection() {
        fillingsSection.click();
    }

    @Step("Проверить, что раздел 'Булки' активен")
    public boolean isBunsSectionActive() {
        try {
            return activeBunsTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что раздел 'Соусы' активен")
    public boolean isSaucesSectionActive() {
        try {
            return activeSaucesTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что раздел 'Начинки' активен")
    public boolean isFillingsSectionActive() {
        try {
            return activeFillingsTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить загрузку страницы")
    public boolean isPageLoaded() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(constructorHeader))
                .isDisplayed();
    }

    @Step("Проверить отображение кнопки 'Оформить заказ'")
    public boolean isMakeOrderButtonDisplayed() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOf(makeOrderButton))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}