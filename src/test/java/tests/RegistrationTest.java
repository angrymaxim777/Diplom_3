package tests;

import api.UserApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.UserGenerator;
import static org.junit.Assert.assertTrue;

@DisplayName("Тестирование регистрации пользователя")
public class RegistrationTest extends BaseTest {
    private String email;
    private String password;
    private String name;
    private String accessToken;

    @After
    @DisplayName("Очистка тестовых данных")
    @Description("Удаление тестового пользователя после выполнения теста")
    public void cleanup() {
        // Удаляем пользователя после теста через API
        if (accessToken != null && !accessToken.isEmpty()) {
            UserApi.deleteUser(accessToken);
            System.out.println("Тестовый пользователь удален");
        }
    }

    @Step("Генерация тестовых данных для регистрации")
    private void generateTestData() {
        email = UserGenerator.generateEmail();
        password = UserGenerator.generatePassword();
        name = UserGenerator.generateName();

        System.out.println("Тестовые данные для регистрации:");
        System.out.println("Email: " + email);
        System.out.println("Имя: " + name);
    }

    @Step("Генерация тестовых данных с коротким паролем")
    private void generateTestDataWithShortPassword() {
        email = UserGenerator.generateEmail();
        password = "12345"; // Менее 6 символов
        name = UserGenerator.generateName();

        System.out.println("Тестовые данные с коротким паролем:");
        System.out.println("Email: " + email);
        System.out.println("Пароль: " + password + " (5 символов)");
        System.out.println("Имя: " + name);
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка успешной регистрации нового пользователя с валидными данными")
    public void testSuccessfulRegistration() {
        generateTestData();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.registerUser(name, email, password);

        // Проверяем, что произошел переход на страницу логина
        assertTrue("После регистрации должен быть переход на страницу логина",
                loginPage.isLoginButtonDisplayed());

        // Логинимся через API, чтобы получить токен для последующего удаления
        Response loginResponse = UserApi.loginUser(email, password);
        if (loginResponse.getStatusCode() == 200) {
            accessToken = UserApi.getAccessToken(loginResponse);
            System.out.println("Токен получен для удаления пользователя");
        }
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    @Description("Проверка отображения ошибки при регистрации с паролем менее 6 символов")
    public void testRegistrationWithShortPassword() {
        generateTestDataWithShortPassword();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.clickRegisterButton();

        // Проверяем отображение ошибки для некорректного пароля
        assertTrue("Должна отображаться ошибка для короткого пароля",
                registrationPage.isPasswordErrorDisplayed());
    }
}