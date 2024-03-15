import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.net.URLConnection;

public class LongRedirect {
    @Test

    public void TestRestAssured(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                 .andReturn();

        String headerLocationValue = response.getHeader("Location");
          Response response1=RestAssured
                  .given()
                  .redirects()
                  .follow(true)
                  .when()
                  .get(headerLocationValue)
                  .andReturn();
        int statusCode= response1.getStatusCode();
        System.out.println(statusCode);
       }

    }
