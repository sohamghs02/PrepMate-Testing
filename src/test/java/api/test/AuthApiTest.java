package api.test;

import api.config.Config;
import api.payloads.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class AuthApiTest {

    @BeforeMethod
    public void init() {
        RestAssured.baseURI = Config.BASE_URI;
    }

    // ===================== POSITIVE TEST =====================

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        LoginRequest body =
                new LoginRequest("arunavabanerjee2018@gmail.com", "123456");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasKey("message"))
                .body("$", hasKey("token"))
                .body("$", hasKey("user"))
                .log().body();

    }

    // ===================== NEGATIVE TESTS =====================

    // 1. Invalid Email
    @Test
    public void loginWithInvalidEmail() {
        LoginRequest body =
                new LoginRequest("invalid@gmail.com", "123456");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Invalid credentials"));
    }

    // 2. Invalid Password
    @Test
    public void loginWithInvalidPassword() {
        LoginRequest body =
                new LoginRequest("arunavabanerjee2018@gmail.com", "wrongpass");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Invalid credentials"));
    }

    // 3. Invalid Email and Password
    @Test
    public void loginWithInvalidCredentials() {
        LoginRequest body =
                new LoginRequest("wrong@gmail.com", "wrong123");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Invalid credentials"));
    }

    // 4. Empty Email
    @Test
    public void loginWithEmptyEmail() {
        LoginRequest body =
                new LoginRequest("", "123456");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(400)
                .body("error", notNullValue());
    }

    // 5. Empty Password
    @Test
    public void loginWithEmptyPassword() {
        LoginRequest body =
                new LoginRequest("arunavabanerjee2018@gmail.com", "");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(400)
                .body("error", notNullValue());
    }


}
