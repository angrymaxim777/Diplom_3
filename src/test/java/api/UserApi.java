package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    static {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Создать пользователя")
    public static Response createUser(UserRequest userRequest) {
        return given()
                .header("Content-type", "application/json")
                .body(userRequest)
                .when()
                .post("/auth/register");
    }

    @Step("Авторизовать пользователя")
    public static Response loginUser(LoginRequest loginRequest) {
        return given()
                .header("Content-type", "application/json")
                .body(loginRequest)
                .when()
                .post("/auth/login");
    }

    @Step("Удалить пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete("/auth/user");
    }

    @Step("Получить accessToken из ответа")
    public static String getAccessToken(Response response) {
        return response.then().extract().path("accessToken");
    }
}