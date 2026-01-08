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

public class RegistrationPage {
    private WebDriver driver;

    // Заголовок страницы регистрации
    @FindBy(how = How.XPATH, using = "//h2[text()='Регистрация']")
    private WebElement registrationHeader;

    // Поле ввода имени
    @FindBy(how = How.XPATH, using = "//label[text()='Имя']/following-sibling::input")
    private WebElement nameInput;

    // Поле ввода email
    @FindBy(how = How.XPATH, using = "//label[text()='Email']/following-sibling::input")
    private WebElement emailInput;

    // Поле ввода пароля
    @FindBy(how = How.XPATH, using = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordInput;

    // Кнопка "Зарегистрироваться"
    @FindBy(how = How.XPATH, using = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    // Ссылка "Войти"
    @FindBy(how = How.XPATH, using = "//a[text()='Войти']")
    private WebElement loginLink;

    // Сообщение об ошибке для пароля
    @FindBy(how = How.XPATH, using = "//p[contains(@class, 'input__error')]")
    private WebElement passwordError;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Проверить загрузку страницы регистрации")
    public boolean isPageLoaded() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(registrationHeader))
                .isDisplayed();
    }

    @Step("Ввести имя: {name}")
    public void enterName(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
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

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        registerButton.click();
    }

    @Step("Нажать ссылку 'Войти'")
    public void clickLoginLink() {
        loginLink.click();
    }

    @Step("Зарегистрировать пользователя: {name}, {email}")
    public void registerUser(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }

    @Step("Заполнить форму регистрации")
    public void fillRegistrationForm(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
    }

    @Step("Проверить отображение ошибки пароля")
    public boolean isPasswordErrorDisplayed() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOf(passwordError))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}