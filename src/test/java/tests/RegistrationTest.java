package tests;

import api.LoginRequest;
import api.UserRequest;
import api.UserApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
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
    private boolean userCreated = false;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        // Инициализация переменных
        userCreated = false;
        accessToken = null;
    }

    @After
    @DisplayName("Очистка тестовых данных")
    @Description("Удаление тестового пользователя после выполнения теста")
    public void cleanup() {
        // Удаляем пользователя после теста через API
        if (userCreated && email != null && password != null) {
            try {
                // Получаем токен через API для удаления
                LoginRequest loginRequest = new LoginRequest(email, password);
                Response loginResponse = UserApi.loginUser(loginRequest);

                if (loginResponse.getStatusCode() == 200) {
                    accessToken = UserApi.getAccessToken(loginResponse);
                    UserApi.deleteUser(accessToken);
                    System.out.println("Тестовый пользователь удален");
                }
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
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

        // Отмечаем, что пользователь создан
        userCreated = true;
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

        // Пользователь не создан, поэтому не будем удалять
        userCreated = false;
    }
}