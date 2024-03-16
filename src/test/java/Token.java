import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Token {
    @Test
    public void testRestAssured() throws InterruptedException {
        JsonPath response = RestAssured
                .given()
                .queryParams("token", "QOxoTNxoDOxAiNx0yMw0CNyAjM")
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        Thread.sleep(Long.parseLong("500"));
        String Status = response.get("status");
        if (Status == null) {
            System.out.println("The key is absent");
        }else{
            System.out.println(Status);
        }
    }
}