package api.test;

import api.base.AdminLoginHelper;
import api.base.LoginHelper;
import api.config.Config;
import api.payloads.QuestionRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class QuestionTest {
    String id1, id2, id3;
    @BeforeClass
    public void init() {
        RestAssured.baseURI = Config.BASE_URI;
    }

    //Get Questions by Subtopic ID
    @Test (priority = 1)
    public void getQuestionsBySubtopicId() {
        String subtopicId = "fde610b7-8f09-452c-afc5-2b73d9710182";

        RestAssured
                .given()
                .when()
                .get("/questions/" + subtopicId)
                .then()
                .log().body()
                .statusCode(200)
                // 2. Verify the response is an array
                .body("size()", greaterThan(0))
                .body("[0].subtopic_id", equalTo(subtopicId));

    }

    //Add question without Token (Negative Test)
    @Test (priority = 2)
    public void addQuestionWithoutToken() {
        String subtopicId = "fde610b7-8f09-452c-afc5-2b73d9710182";
        String subjectId = "74f6adeb-bbee-4dd0-b316-0ed4105d0c54";

        String questionText = "What is Disk Scheduling 1?";
        String answerText = "Disk scheduling is the method by which the operating system decides the order in which disk I/O requests are to be serviced. 1";
        QuestionRequest requestBody = new QuestionRequest(subjectId, subtopicId, questionText, answerText);
        Response resp = RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/questions/add")
                .then()
                .log().body()
                //Test 1 : Non-authorized access should be forbidden
                .statusCode(404)
                .extract().response();

    }

    //Add question with normal user's Token (Negative Test)
    @Test (priority = 3)
    public void addQuestionWithUserToken() {
        LoginHelper.login();
        String subtopicId = "fde610b7-8f09-452c-afc5-2b73d9710182";
        String subjectId = "74f6adeb-bbee-4dd0-b316-0ed4105d0c54";

        String questionText = "What is Disk Scheduling 2?";
        String answerText = "Disk scheduling is the method by which the operating system decides the order in which disk I/O requests are to be serviced. 2";
        QuestionRequest requestBody = new QuestionRequest(subjectId, subtopicId, questionText, answerText);
        Response resp = RestAssured
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + LoginHelper.token)
                .body(requestBody)
                .when()
                .post("/questions/add")
                .then()
                .log().body()
                //Test 1 : Non-authorized access should be forbidden
                .statusCode(404)
                .extract().response();
    }
    // Add question with Admin Token (Positive Test)
    @Test (priority = 4)
    public void addQuestionWithAdminToken() {
        AdminLoginHelper.login();
        String subtopicId = "fde610b7-8f09-452c-afc5-2b73d9710182";
        String subjectId = "74f6adeb-bbee-4dd0-b316-0ed4105d0c54";
        String questionText = "What is Disk Scheduling?";
        String answerText = "Disk scheduling is the method by which the operating system decides the order in which disk I/O requests are to be serviced.";
        QuestionRequest requestBody = new QuestionRequest(subjectId, subtopicId, questionText, answerText);
        Response resp = RestAssured
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + AdminLoginHelper.token)
                .body(requestBody)
                .when()
                .post("/questions/add")
                .then()
                .log().body()
                // Test 1: Authorized access should be successful
                .statusCode(201)
                // Test 2: Verify the response body contains the correct question text
                .body("question.question_text", equalTo(questionText))
                // Test 3: Verify the response time is reasonable (under 3000ms)
                .time(lessThan(4000L))
                .extract().response();
        id3 = resp.jsonPath().getString("question.id");

    }
    //Update question with Admin Token (Positive Test)
    @Test (priority = 5)
    public void updateQuestionWithAdminToken() {
        AdminLoginHelper.login();
        String questionId = "548f707b-f203-403a-9ca1-c92f4c4b566a";
        String questionText = "What is CPU Scheduling? 2";
        String answerText = "CPU scheduling is the process of determining which of the processes in the ready queue will be allocated the CPU next.";
        QuestionRequest requestBody = new QuestionRequest(null, null, questionText, answerText);
        RestAssured
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + AdminLoginHelper.token)
                .body(requestBody)
                .when()
                .put("/questions/" + questionId)
                .then()
                .log().body()
                // Test 1: Authorized access should be successful
                .statusCode(200)
                // Test 2: Verify the response body contains the updated question text
                .body("question_text", equalTo(questionText))
                // Test 3: Verify the response time is reasonable (under 3000ms)
                .time(lessThan(4000L));
        }

        @Test (priority = 6)
        public void deleteQuestionWithAdminToken() {
            AdminLoginHelper.login();
            RestAssured
                    .given()
                    .header("Authorization", "Bearer " + AdminLoginHelper.token)
                    .when()
                    .delete("/questions/" + id3)
                    .then()
                    .log().body()
                    // Test 1: Authorized access should be successful
                    .statusCode(200)
                    //Test 2 : Message should confirm deletion
                    .body("message", equalTo("Question deleted successfully"));
        }

}
