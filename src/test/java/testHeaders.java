import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import io.restassured.http.Headers;
public class testHeaders {
    @Test

    public void testAssertHeaders() {
        Map <String,String> authData= new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response response = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers headers= response.getHeaders();
        System.out.println(headers);
        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"), "Значение headers не верное");
    }
}
