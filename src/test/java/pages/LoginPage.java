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

public class LoginPage {
    private WebDriver driver;

    // Заголовок страницы входа
    @FindBy(how = How.XPATH, using = "//h2[text()='Вход']")
    private WebElement loginHeader;

    // Поле ввода email
    @FindBy(how = How.XPATH, using = "//label[text()='Email']/following-sibling::input")
    private WebElement emailInput;

    // Поле ввода пароля
    @FindBy(how = How.XPATH, using = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordInput;

    // Кнопка "Войти"
    @FindBy(how = How.XPATH, using = "//button[text()='Войти']")
    private WebElement loginButton;

    // Ссылка "Зарегистрироваться"
    @FindBy(how = How.XPATH, using = "//a[text()='Зарегистрироваться']")
    private WebElement registerLink;

    // Ссылка "Восстановить пароль"
    @FindBy(how = How.XPATH, using = "//a[text()='Восстановить пароль']")
    private WebElement recoveryLink;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Проверить загрузку страницы входа")
    public boolean isPageLoaded() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(loginHeader))
                .isDisplayed();
    }

    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        loginButton.click();
    }

    @Step("Нажать ссылку 'Зарегистрироваться'")
    public void clickRegisterLink() {
        registerLink.click();
    }

    @Step("Нажать ссылку 'Восстановить пароль'")
    public void clickRecoveryLink() {
        recoveryLink.click();
    }

    @Step("Выполнить вход с email: {email}")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Проверить отображение кнопки 'Войти'")
    public boolean isLoginButtonDisplayed() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(loginButton))
                .isDisplayed();
    }
}