package tests;

import config.WebDriverFactory;
import io.qameta.allure.Attachment;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

@io.qameta.allure.junit4.DisplayName("Базовый тестовый класс")
public class BaseTest {
    protected WebDriver driver;
    protected static final String BASE_URL = "https://stellarburgers.education-services.ru";

    @Rule
    public TestWatcher screenshotRule = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            if (driver != null) {
                saveScreenshot();
            }
        }

        @Override
        protected void finished(Description description) {
            if (driver != null) {
                driver.quit();
            }
        }
    };

    @Before
    @io.qameta.allure.Step("Настройка драйвера и открытие браузера")
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        System.out.println("Запуск теста с браузером: " + browser);
        driver = WebDriverFactory.getDriver(browser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        System.out.println("Страница загружена: " + driver.getTitle());
    }

    @After
    @io.qameta.allure.Step("Завершение теста")
    public void tearDown() {
    }

    @Attachment(value = "Скриншот при падении", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}