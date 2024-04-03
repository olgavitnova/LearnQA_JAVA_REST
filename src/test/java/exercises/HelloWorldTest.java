package exercises;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    @Test

    public void TestHelloWorld(){
      Response response = RestAssured
              .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
              .andReturn();
      response.prettyPrint();
    }
}
