package api.test;

import api.base.LoginHelper;
import api.config.Config;
import api.payloads.ProgressRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class MarkProgressApiTest {
    private static final String userId = "644d20ba-c10f-4e7e-906d-60517c860493";
    private static final String subjectId = "74f6adeb-bbee-4dd0-b316-0ed4105d0c54";
    private static final String questionId = "2df6aa6b-ea98-4558-abd6-73b2a0bf288d";
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Config.BASE_URI;
    }
    // ---------------------------------------------------
    // Get Progress (Positive)
    // ---------------------------------------------------
    @Test(priority = 1)
    public void getProgressForSubject() {

        LoginHelper.login();

        RestAssured
                .given()
                .header("Authorization", "Bearer " + LoginHelper.token)
                .when()
                .get("/progress/" + userId + "/" + subjectId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completedQ", greaterThanOrEqualTo(0))
                .body("totalQ", greaterThanOrEqualTo(0))
                .body("progress", greaterThanOrEqualTo(0f))
                .body("progress", lessThanOrEqualTo(100f))
                .body("completed_questions", notNullValue());
//                .log().body();
    }
    // ---------------------------------------------------
    // Get Progress without Token (Negative)
    // ---------------------------------------------------
    @Test(priority = 2)
    public void getProgressWithoutToken() {

        RestAssured
                .given()
                .when()
                .get("/progress/" + userId + "/" + subjectId)
                .then()
                .statusCode(401);
    }

    // ---------------------------------------------------
    // Mark Question as Read (Positive)
    // ---------------------------------------------------
    @Test(priority = 3)
    public void markQuestionAsRead() {

        LoginHelper.login();
        ProgressRequest progressRequest = new ProgressRequest(questionId, userId, subjectId);
        RestAssured
                .given()
                .header("Authorization", "Bearer " + LoginHelper.token)
                .contentType(ContentType.JSON)
                .body(progressRequest)
                .when()
                .post("/progress/mark")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("user_id", notNullValue())
                .body("question_id", notNullValue())
                .body("is_read", org.hamcrest.Matchers.equalTo(true))
                .body("read_at", notNullValue())
                .log().body();
    }

    // ---------------------------------------------------
    // Mark Question with Missing Fields (Negative)
    // ---------------------------------------------------
    @Test(priority = 4)
    public void markQuestionWithMissingFields() {

        LoginHelper.login();

        ProgressRequest progressRequest = new ProgressRequest(userId, subjectId);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + LoginHelper.token)
                .body(progressRequest)
                .when()
                .post("/progress/mark")
                .then()
                .statusCode(400);
    }

    // ---------------------------------------------------
    // Unmark Question (Positive)
    // ---------------------------------------------------
    @Test(priority = 5)
    public void unmarkQuestion() {

        LoginHelper.login();

        ProgressRequest progressRequest = new ProgressRequest(questionId, userId, subjectId);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + LoginHelper.token)
                .body(progressRequest)
                .when()
                .post("/progress/unmark")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("count", greaterThanOrEqualTo(0))
                .log().body();
    }

}
