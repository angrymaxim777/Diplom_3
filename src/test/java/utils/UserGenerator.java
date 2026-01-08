package utils;

import io.qameta.allure.Step;
import java.util.UUID;

public class UserGenerator {

    @Step("Сгенерировать email")
    public static String generateEmail() {
        return "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    @Step("Сгенерировать пароль")
    public static String generatePassword() {
        return "password_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Step("Сгенерировать имя")
    public static String generateName() {
        return "TestUser_" + UUID.randomUUID().toString().substring(0, 6);
    }
}