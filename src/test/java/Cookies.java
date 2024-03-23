import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;

public class Cookies {

@Test
public void testCookies (){
    Map <String,String> authData= new HashMap<>();
    authData.put("email", "vinkotov@example.com");
    authData.put("password", "1234");
    Response response = RestAssured
            .given()
            .post("https://playground.learnqa.ru/api/homework_cookie")
            .andReturn();
Map <String,String> cookies= response.getCookies();
System.out.println(cookies);
assertTrue(cookies.containsValue("hw_value"), "Значение cookie не верное");
        }
}
