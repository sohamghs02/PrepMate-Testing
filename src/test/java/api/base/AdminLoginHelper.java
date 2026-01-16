package api.base;

import api.config.Config;
import api.payloads.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;

public class AdminLoginHelper {
    public static String token;
    public static void login(){
        String email = "arunavabanerjee2018@gmail.com", password = "123456";
        LoginRequest loginBody = new LoginRequest(email, password);
        RestAssured.baseURI = Config.BASE_URI;
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(loginBody)
                .when()
                .post("/auth/login");

        token = response.jsonPath().getString("token");
        if (token == null) {
            throw new RuntimeException("Login failed. Token not generated.");
        }
    }
}
