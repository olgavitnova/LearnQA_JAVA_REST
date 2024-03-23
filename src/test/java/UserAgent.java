import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class UserAgent {

    @ParameterizedTest
    @ValueSource(strings = {"platform", "browser", "device"})
    public void testUserAgent(String name) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("platform", name);
        queryParams.put("browser",name);
        queryParams.put("device",name);
            JsonPath response = RestAssured
                    .given()
                    .queryParams(queryParams)
                    .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                    .andReturn()
                    .jsonPath();
            String userAgent = response.getString("browser");
            String userAgent1 = response.getString("device");
            String userAgent2 = response.getString("platform");
         assertEquals("platform': 'Mobile', 'browser': 'No', 'device': 'Android",userAgent,"Результат не верный");
         System.out.println(userAgent);


        }
    }

