package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.ApiCoreRequests;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.HashMap;
import java.util.Map;

@Epic("Creating user cases")
@Feature("Creating user")
public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("Этот тест проверяет создание пользователя с емейлом без знака @")
    @DisplayName("Негативный тест на создание пользователя")
    public void testCreateUserWithUnCorrectEmail() {
        String email = "vinkotovexample.com";
        Map<String, String> userDate = new HashMap<>();
        userDate.put("email", email);
        userDate.put("username", "learnqa");
        userDate.put("firstName", "learnqa");
        userDate.put("lastName", "learnqa");
        userDate.put("password", "1234");
        Response responseCreateAuth = apiCoreRequests.makeRequestWithNonCorrectEmail("https://playground.learnqa.ru/api/user/",
                userDate);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }

    @Test
    @Description("Этот тест проверяет создание пользователя с уже существующим емейлом")
    @DisplayName("Негативный тест на создание пользователя")
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";
        Map<String, String> userDate = new HashMap<>();
        userDate.put("email", email);
        userDate = DataGenerator.getRegistrationData(userDate);
        Response responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email'" + email + "'is already exists");
    }

    @Test
    @Description("Успешное создание пользователя")
    @DisplayName("Позитивный тест на создание пользователя")
    public void testCreateUserSuccessfully() {
        Map<String, String> userDate = DataGenerator.getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userDate)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }

    @ParameterizedTest
    @ValueSource(strings = {"username", "firstName", "lastName", "email", "password"})
    @Description("Создание пользователя без указания обязательного параметра")
    @DisplayName("Негативный тест на создание пользователя при отсутствие одного из параметров")
    public void testCreateUserWithoutParam() {
        String email = "vinkotov@example.com";
        Map<String, String> userDate = new HashMap<>();
        userDate.put("email", email);
        userDate.put("username", "learnqa");
        userDate.put("firstName", "learnqa");
        userDate.put("lastName", "learnqa");
        Response responseCreateAuth = apiCoreRequests.makeRequestWithoutPassword("https://playground.learnqa.ru/api/user/",
                userDate);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The following required params are missed: password");
    }

    @Test
    @Description("Этот тест проверяет создание пользователя с коротким именем")
    @DisplayName("Негативный тест на создание пользователя")
    public void testCreateUserWithShortName() {
        Map<String, String> userDate = new HashMap<>();
        userDate.put("email", "vinkotov@example.com");
        userDate.put("username", "learnqa");
        userDate.put("firstName", "a");
        userDate.put("lastName", "learnqa");
        userDate.put("password", "1234");
        Response responseCreateAuth = apiCoreRequests.makeRequestWithShortName("https://playground.learnqa.ru/api/user/",
                userDate);
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");

    }

    @Test
    @Description("Этот тест проверяет создание пользователя с именем больше 250 символов")
    @DisplayName("Негативный тест на создание пользователя")
    public void testCreateUserWithLongName(String firstname) {
        Map<String, String> userDate = DataGenerator.getRegistrationData();
        if (firstname.length() >= 250) {
            Response responseCreateAuth = apiCoreRequests.makeRequestWithLongName("https://playground.learnqa.ru/api/user/",
                userDate);

                Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
                Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too long"); }
            else {

        }
    }
}