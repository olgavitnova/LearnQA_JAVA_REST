package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {
    @Step("Make a Get-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth-sid", cookie)
                .get(url)
                .andReturn();

    }

    @Step("Make a Get-request with auth cookie only")
    public Response makeGetRequestWithCookie(String url, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth-sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a Get-request with token only")
    public Response makeGetRequestWithToken(String url, String token) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Make a Post-request")
    public Response makePostRequest(String url, Map<String, String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Make request with Email without @")
    public Response makeRequestWithNonCorrectEmail(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make request without password")
    public Response makeRequestWithoutPassword(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make request with short name")
    public Response makeRequestWithShortName(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make request with long name")
    public Response makeRequestWithLongName(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make request for get user's data")
    public Response makeRequestGetUserData(String url, Map<String, String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();

    }

    @Step("Make request for get other user's data")
    public Response makeRequestGetOtherUserData(String url) {
        return given()
                .filter(new AllureRestAssured())
                .get(url)
                .andReturn();
    }

    @Step("Make request for delete user")
    public Response makeRequestForDelete(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .delete(url)
                .andReturn();
    }
    @Step("Make request for authorization")
    public Response makeRequestForAuthorization(String url, Map<String, String> authData){
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

}