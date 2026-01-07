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

public class ProfilePage {
    private WebDriver driver;

    // Заголовок "Профиль" в сайдбаре
    @FindBy(how = How.XPATH, using = "//a[text()='Профиль' and contains(@class, 'active')]")
    private WebElement profileActiveLink;

    // Поле ввода имени
    @FindBy(how = How.XPATH, using = "//label[text()='Имя']/following-sibling::input")
    private WebElement nameInput;

    // Поле ввода email
    @FindBy(how = How.XPATH, using = "//label[text()='Логин']/following-sibling::input")
    private WebElement emailInput;

    // Поле ввода пароля
    @FindBy(how = How.XPATH, using = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordInput;

    // Кнопка "Сохранить"
    @FindBy(how = How.XPATH, using = "//button[text()='Сохранить']")
    private WebElement saveButton;

    // Кнопка "Выход"
    @FindBy(how = How.XPATH, using = "//button[text()='Выход']")
    private WebElement logoutButton;

    // Кнопка "Конструктор"
    @FindBy(how = How.XPATH, using = "//p[text()='Конструктор']")
    private WebElement constructorButton;

    // Логотип Stellar Burgers
    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'AppHeader_header__logo')]")
    private WebElement logo;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Проверить загрузку страницы профиля")
    public boolean isPageLoaded() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(profileActiveLink))
                .isDisplayed();
    }

    @Step("Получить значение поля 'Имя'")
    public String getNameValue() {
        return nameInput.getAttribute("value");
    }

    @Step("Получить значение поля 'Email'")
    public String getEmailValue() {
        return emailInput.getAttribute("value");
    }

    @Step("Нажать кнопку 'Выход'")
    public void clickLogoutButton() {
        logoutButton.click();
    }

    @Step("Нажать кнопку 'Конструктор'")
    public void clickConstructorButton() {
        constructorButton.click();
    }

    @Step("Нажать логотип Stellar Burgers")
    public void clickLogo() {
        logo.click();
    }

    @Step("Ожидание загрузки страницы профиля")
    public void waitForProfilePage() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(nameInput));
    }
}