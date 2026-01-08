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
import pages.PasswordRecoveryPage;
import pages.RegistrationPage;
import utils.UserGenerator;
import static org.junit.Assert.assertTrue;

@DisplayName("Тестирование авторизации пользователя")
public class LoginTest extends BaseTest {
    private String email;
    private String password;
    private String name;
    private String accessToken;

    @Before
    @Override
    public void setUp() {
        // Сначала вызываем setUp из BaseTest для инициализации драйвера
        super.setUp();

        // Создаем тестового пользователя через API
        createTestUser();
    }

    @After
    @DisplayName("Очистка тестовых данных")
    @Description("Удаление тестового пользователя после выполнения теста")
    public void cleanup() {
        // Удаляем пользователя после теста через API
        if (accessToken != null && !accessToken.isEmpty()) {
            try {
                UserApi.deleteUser(accessToken);
                System.out.println("Тестовый пользователь удален");
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }
    }

    @Step("Создание тестового пользователя через API")
    private void createTestUser() {
        // Создаем пользователя через API перед тестом
        try {
            email = UserGenerator.generateEmail();
            password = UserGenerator.generatePassword();
            name = UserGenerator.generateName();

            System.out.println("Создаю тестового пользователя:");
            System.out.println("Email: " + email);
            System.out.println("Имя: " + name);

            // Создаем пользователя через API
            UserRequest userRequest = new UserRequest(email, password, name);
            Response response = UserApi.createUser(userRequest);

            if (response.getStatusCode() == 200) {
                accessToken = UserApi.getAccessToken(response);
                System.out.println("Пользователь успешно создан");
            } else {
                // Если пользователь уже существует, попробуем получить токен через логин
                System.out.println("Пользователь уже существует, пытаюсь авторизоваться...");
                LoginRequest loginRequest = new LoginRequest(email, password);
                Response loginResponse = UserApi.loginUser(loginRequest);

                if (loginResponse.getStatusCode() == 200) {
                    accessToken = UserApi.getAccessToken(loginResponse);
                    System.out.println("Авторизация прошла успешно");
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при создании тестового пользователя: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Проверка входа пользователя через основную кнопку входа на главной странице")
    public void testLoginFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        // Проверяем, что произошел вход
        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isMakeOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверка входа пользователя через кнопку личного кабинета в хедере")
    public void testLoginFromPersonalAccount() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isMakeOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверка входа пользователя со страницы регистрации по ссылке 'Войти'")
    public void testLoginFromRegistrationPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLoginLink();

        loginPage.login(email, password);

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isMakeOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверка входа пользователя со страницы восстановления пароля по ссылке 'Войти'")
    public void testLoginFromPasswordRecoveryPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRecoveryLink();

        PasswordRecoveryPage recoveryPage = new PasswordRecoveryPage(driver);
        recoveryPage.clickLoginLink();

        loginPage.login(email, password);

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isMakeOrderButtonDisplayed());
    }
}