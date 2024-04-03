package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import lib.ApiCoreRequests;

public class UserDeleteTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Epic("Тест кейсы по удалению пользователя")
    @Owner("Витнова О.А")
    @Severity(value = SeverityLevel.NORMAL)
    @Issue(value = "FGY-4627")
    @TmsLinks({@TmsLink(value = "TL-135"), @TmsLink(value = "TL-158")})

    @Test
    @Description("Тест на попытку удалить пользователя по ID 2")
    @DisplayName("Позитивные проверки на удаления данных пользователя")
    public void testEditJustCreatedTest() {

        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseEditUser = apiCoreRequests.makeRequestForDelete("https://playground.learnqa.ru/api/user/2", authData);

        Assertions.assertResponseTextEquals(responseEditUser, "{\"error\":\"Auth token not supplied\"}");
    }

    @Test
    @Description("Тест на удаление данных пользователя по ID")
    @DisplayName("Позитивные проверки на изменения данных пользователя")
    public void testDeleteById() {
//GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");
//LOGIN

        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makeRequestForAuthorization("https://playground.learnqa.ru/api/user/login",
                authData);

//DELETE

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(authData)
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        //GET

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        Assertions.assertResponseTextEquals(responseEditUser, "{\"error\":\"User is not exists\"}");



    }

    @Test
    @Description("Тест на удаление данных пользователя, будучи авторизованными другим пользователем")
    @DisplayName("Негативные проверки на изменения данных пользователя")
    public void testDeleteAuthorizedOtherUserTest() {
//GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreateAuth.getString("id");
//LOGIN

        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

//EDIT
        Map<String, String> editData = new HashMap<>();

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .delete("https://playground.learnqa.ru/api/user/95310")
                .andReturn();
        //GET

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/1")
                .andReturn();

        Assertions.assertResponseTextEquals(responseEditUser, "{\"error\":\"Auth token not supplied\"}");
    }
}