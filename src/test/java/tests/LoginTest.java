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

    @Step("Создание тестового пользователя через API")
    private void createTestUser() {
        // Создаем пользователя через API перед тестом
        email = UserGenerator.generateEmail();
        password = UserGenerator.generatePassword();
        name = UserGenerator.generateName();

        System.out.println("Создаю тестового пользователя:");
        System.out.println("Email: " + email);
        System.out.println("Имя: " + name);

        Response response = UserApi.createUser(email, password, name);
        if (response.getStatusCode() == 200) {
            accessToken = UserApi.getAccessToken(response);
            System.out.println("Пользователь успешно создан");
        } else {
            // Если пользователь уже существует, попробуем получить токен через логин
            System.out.println("Пользователь уже существует, пытаюсь авторизоваться...");
            Response loginResponse = UserApi.loginUser(email, password);
            if (loginResponse.getStatusCode() == 200) {
                accessToken = UserApi.getAccessToken(loginResponse);
                System.out.println("Авторизация прошла успешно");
            }
        }
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Проверка входа пользователя через основную кнопку входа на главной странице")
    public void testLoginFromMainPage() {
        createTestUser();

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
        createTestUser();

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
        createTestUser();

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
        createTestUser();

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