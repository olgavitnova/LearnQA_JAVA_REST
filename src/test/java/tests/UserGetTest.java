package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests=new ApiCoreRequests();

    @Epic("Тест кейсы по получению данных пользователя")
    @Feature("Проверки по получнию пользователя с разными комбинациями")
    @Owner("Витнова О.А")
    @Severity(value = SeverityLevel.NORMAL)
    @Issue(value = "FGY-4627")
    @TmsLinks({@TmsLink(value = "TL-135"), @TmsLink(value = "TL-158")})

    @Test
    @Description("Тест на запрос данных другого пользователя")
    @DisplayName("Проверка запроса данных другого пользователя")
    public void testGetUserDetailsAuthOtherUser() {
        Map<String,String> userData= DataGenerator.getRegistrationData();
        Map<String,String> authData=new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth =apiCoreRequests.makeRequestGetUserData("https://playground.learnqa.ru/api/user/login",
                authData);
        Response responseUserData = apiCoreRequests.makeRequestGetOtherUserData("https://playground.learnqa.ru/api/user/1");
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }
    @Test
    @Description("Тест на неавторизованный запрос на данные")
    @DisplayName("Позитивная проверка запроса данных пользователя")
    public void testGetUserDataNotAuth() {
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }
        @Test
        @Description("Тест на авторизованный запрос на данные пользователя")
        @DisplayName("Позитивная проверка запроса данных пользователя")
        public void testGetUserDetailsAuthAsSameUser() {
          Map<String,String> authData=new HashMap<>();
          authData.put("email", "vitkotov@example.com");
          authData.put("password","1234");

          Response responseGetAuth =RestAssured
                  .given()
                  .body(authData)
                  .post("https://playground.learnqa.ru/api/user/login")
                  .andReturn();

          String header =this.getHeader(responseGetAuth,"x-csrf-token");
            String cookie =this.getCookie(responseGetAuth,"auth_sid");

            Response responseUserData= RestAssured
                    .given()
                    .header("x-csrf-token",header)
                    .cookie("auth_sid",cookie)
                    .get("https://playground.learnqa.ru/api/user/2")
                    .andReturn();
String[] expectedField ={"username","firstName","lastName","email" };
Assertions.assertJsonHasFields(responseUserData,expectedField);

        }
    }

