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

public class PasswordRecoveryPage {
    private WebDriver driver;

    // Заголовок страницы восстановления пароля
    @FindBy(how = How.XPATH, using = "//h2[text()='Восстановление пароля']")
    private WebElement recoveryHeader;

    // Поле ввода email
    @FindBy(how = How.XPATH, using = "//label[text()='Email']/following-sibling::input")
    private WebElement emailInput;

    // Кнопка "Восстановить"
    @FindBy(how = How.XPATH, using = "//button[text()='Восстановить']")
    private WebElement recoveryButton;

    // Ссылка "Войти"
    @FindBy(how = How.XPATH, using = "//a[text()='Войти']")
    private WebElement loginLink;

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Проверить загрузку страницы восстановления пароля")
    public boolean isPageLoaded() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(recoveryHeader))
                .isDisplayed();
    }

    @Step("Ввести email для восстановления: {email}")
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    @Step("Нажать кнопку 'Восстановить'")
    public void clickRecoveryButton() {
        recoveryButton.click();
    }

    @Step("Нажать ссылку 'Войти'")
    public void clickLoginLink() {
        loginLink.click();
    }
}