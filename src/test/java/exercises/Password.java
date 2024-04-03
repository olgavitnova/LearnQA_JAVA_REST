package exercises;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Password {
    @Test

    public void TestRestAssured(){
        ArrayList<String> list = new ArrayList<>();
        list.add("welcome");
        list.add("monkey");
        list.add("trustno1");
        list.add("shadow");
        list.add("1234567");
        list.add("starwars");
        String firstElement = list.get(0);
        Map<String,String> data =new HashMap<>();
        data.put("login", "super_admin");
        data.put("password", String.valueOf(firstElement));
        Response responseForGet = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                .andReturn();
        String responseCookie= responseForGet.getCookie("auth_cookie");
        System.out.println(responseCookie);
        Map <String,String> cookies =new HashMap<>();
        if (responseCookie!=null){
            cookies.put("auth_cookie",responseCookie);
        }
        Response responseForCheck =RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                .andReturn();
        responseForCheck.print();


    }
}
