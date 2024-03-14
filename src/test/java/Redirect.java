import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class Redirect {
    @Test

    public void TestRestAssured(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        int statusCode=response.getStatusCode();
        System.out.println(statusCode);

    }
}