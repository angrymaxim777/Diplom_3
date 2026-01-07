package config;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.time.Duration;

public class WebDriverFactory {

    @Step("Создать драйвер для браузера: {browserName}")
    public static WebDriver getDriver(String browserName) {
        if (browserName == null || browserName.isEmpty()) {
            browserName = "chrome";
        }

        System.out.println("Создаю драйвер для браузера: " + browserName);
        System.out.println("Текущая рабочая директория: " + System.getProperty("user.dir"));

        WebDriver driver = null;

        if (browserName.equalsIgnoreCase("chrome")) {
            driver = setupChromeDriver();
        } else if (browserName.equalsIgnoreCase("yandex")) {
            driver = setupYandexDriver();
        } else {
            System.err.println("Неизвестный браузер: " + browserName + ". Использую Chrome.");
            driver = setupChromeDriver();
        }

        // Общие настройки
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        return driver;
    }

    @Step("Настроить ChromeDriver")
    private static WebDriver setupChromeDriver() {
        System.out.println("Настраиваю ChromeDriver...");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    @Step("Настроить YandexDriver")
    private static WebDriver setupYandexDriver() {
        System.out.println("Настраиваю YandexDriver...");

        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("Операционная система: " + os);

        // Проверяем ОС
        if (!os.contains("win")) {
            throw new RuntimeException("Этот код настроен только для Windows. Ваша ОС: " + os);
        }

        // Путь к драйверу
        String relativePath = "src/test/resources/drivers/windows/yandexdriver.exe";
        String projectDir = System.getProperty("user.dir");
        String absolutePath = projectDir + File.separator + relativePath;

        System.out.println("Ищу драйвер по пути: " + absolutePath);

        File driverFile = new File(absolutePath);

        // Проверяем существование файла
        if (!driverFile.exists()) {
            System.err.println("ОШИБКА: Файл драйвера не найден!");
            System.err.println("Пожалуйста, поместите файл 'yandexdriver.exe' в папку:");
            System.err.println(projectDir + File.separator + "src/test/resources/drivers/windows/");

            throw new RuntimeException("YandexDriver не найден. Поместите yandexdriver.exe в " + relativePath);
        }

        System.out.println("Драйвер найден. Размер: " + driverFile.length() + " байт");

        // Устанавливаем системное свойство
        System.setProperty("webdriver.chrome.driver", absolutePath);

        // Настройки ChromeOptions для Yandex
        ChromeOptions options = new ChromeOptions();

        // Пробуем найти Yandex Browser
        String[] possiblePaths = {
                System.getenv("LOCALAPPDATA") + "\\Yandex\\YandexBrowser\\Application\\browser.exe",
                System.getenv("ProgramFiles") + "\\Yandex\\YandexBrowser\\Application\\browser.exe",
                System.getenv("ProgramFiles(x86)") + "\\Yandex\\YandexBrowser\\Application\\browser.exe",
                "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe",
                "C:\\Program Files (x86)\\Yandex\\YandexBrowser\\Application\\browser.exe"
        };

        String browserPath = null;
        for (String path : possiblePaths) {
            if (path != null && new File(path).exists()) {
                browserPath = path;
                break;
            }
        }

        if (browserPath != null) {
            System.out.println("Найден Yandex Browser по пути: " + browserPath);
            options.setBinary(browserPath);
        } else {
            System.err.println("ПРЕДУПРЕЖДЕНИЕ: Yandex Browser не найден в стандартных расположениях");
            System.err.println("Установите Yandex Browser или тесты могут не работать");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(options);
    }
}