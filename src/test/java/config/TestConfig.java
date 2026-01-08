package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestConfig {
    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            // Используем значения по умолчанию
            setDefaultProperties();
        }
    }

    private static void setDefaultProperties() {
        properties.setProperty("base.url", "https://stellarburgers.education-services.ru");
        properties.setProperty("browser", "chrome");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "15");

        // Динамически определяем ОС
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            properties.setProperty("yandex.driver.path", "src/test/resources/drivers/windows/yandexdriver.exe");
            properties.setProperty("yandex.browser.path",
                    System.getenv("LOCALAPPDATA") + "/Yandex/YandexBrowser/Application/browser.exe");
        } else if (os.contains("mac")) {
            properties.setProperty("yandex.driver.path", "src/test/resources/drivers/macos/yandexdriver");
            properties.setProperty("yandex.browser.path", "/Applications/Yandex.app/Contents/MacOS/Yandex");
        } else if (os.contains("linux")) {
            properties.setProperty("yandex.driver.path", "src/test/resources/drivers/linux/yandexdriver");
            properties.setProperty("yandex.browser.path", "/usr/bin/yandex-browser");
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getBrowser() {
        return System.getProperty("browser", properties.getProperty("browser", "chrome"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "15"));
    }

    public static String getYandexDriverPath() {
        return properties.getProperty("yandex.driver.path");
    }

    public static String getYandexBrowserPath() {
        return properties.getProperty("yandex.browser.path");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }
}