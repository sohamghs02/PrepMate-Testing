package api.test;

import api.base.AdminLoginHelper;
import api.base.LoginHelper;
import api.config.Config;
import api.payloads.SubjectRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasKey;

public class SubjectApiTest {
    @BeforeMethod
    public void init() {
        RestAssured.baseURI = Config.BASE_URI;
    }
    @Test (priority = 0)
    public void getAllSubjects() {
        RestAssured
                .given()
                .when()
                .get("/subjects")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", notNullValue())
                .body("[0]", hasKey("id"))
                .body("[0]", hasKey("question_count"))
                .log().body();

    }
    @Test (priority = 1)
    public void getSubjectByInvalidId() {
        RestAssured
                .given()
                .when()
                .get("/subjects/123")
                .then()
                .log().body()
                .statusCode(500) //Defects : Server should return 500 but it returns 200
                .body("error", equalTo("Failed to fetch subject"));
    }
    @Test (priority = 2)
    public void getSubjectValidId() {
        RestAssured
                .given()
                .when()
                .get("/subjects/74f6adeb-bbee-4dd0-b316-0ed4105d0c54")
                .then()
                .statusCode(200)
                .body("id", equalTo("74f6adeb-bbee-4dd0-b316-0ed4105d0c54"));
    }

    @Test (priority = 3)
    public void addSubjectWithValidAdminToken(){
        AdminLoginHelper.login();
        SubjectRequest body = new SubjectRequest("Test Subject1","Test Description1","Cpu");
        RestAssured
                .given()
                    .header("Authorization", "Bearer " + AdminLoginHelper.token)
                    .contentType(ContentType.JSON)
                    .body(body)
                .when()
                    .post("/subjects/addsubject")
                .then()
                    .log().body()
                    .statusCode(201)
                    .body("message", equalTo("Subject added successfully"));
    }
    @Test (priority = 4)
    public void addSubjectWithInValidAdminToken(){
        LoginHelper.login();
        SubjectRequest body = new SubjectRequest("Test Subject","Test Description","Cpu");
        RestAssured
                .given()
                    .header("Authorization", "Bearer " + LoginHelper.token)
                    .contentType(ContentType.JSON)
                .body(body)
                .when()
                    .post("/subjects/addsubject")
                .then()
                    .statusCode(403)
                    .body("error", equalTo("Forbidden: admin access required"));
    }



}
