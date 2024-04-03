package exercises;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirect {
    @Test

    public void TestRestAssured() {
        int statusCode;
        do {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get("https://playground.learnqa.ru/api/long_redirect")
                    .andReturn();

            String headerLocationValue = response.getHeader("Location");
            Response response1 = RestAssured
                    .given()
                    .redirects()
                    .follow(true)
                    .when()
                    .get(headerLocationValue)
                    .andReturn();
            statusCode = response1.getStatusCode();
            System.out.println(statusCode);
        } while (statusCode >200);

    }
}