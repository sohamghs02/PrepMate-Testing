package api.test;

import api.base.AdminLoginHelper;
import api.base.LoginHelper;
import api.config.Config;
import api.payloads.SubtopicRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

public class SubtopicApiTest {

    private static final String SUBJECT_ID = "4abec639-d80b-4418-bc46-4013ae797d33";
    private static final String GET_SUBJECT_ID = "74f6adeb-bbee-4dd0-b316-0ed4105d0c54";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Config.BASE_URI;
    }

    // ---------------------------------------------------
    // GET Subtopics for a Subject
    // ---------------------------------------------------
    @Test(priority = 1)
    public void getSubTopicsForASubject() {

        RestAssured
                .given()
                .when()
                .get("/subtopics/" + GET_SUBJECT_ID)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].subject_id", equalTo(GET_SUBJECT_ID));
    }

    // ---------------------------------------------------
    // Add Subtopic without Token (Negative Test)
    // ---------------------------------------------------
    @Test(priority = 2)
    public void addSubTopicsWithoutToken() {

        SubtopicRequest subtopic =
                new SubtopicRequest(SUBJECT_ID, "Introduction");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(subtopic)
                .when()
                .post("/subtopics/addsubtopic")
                .then()
                .statusCode(403);
    }

    // ---------------------------------------------------
    // Add Subtopic with Non-Admin Token (Negative Test)
    // ---------------------------------------------------
    @Test(priority = 3)
    public void addSubTopicWithNonAdminToken() {

        LoginHelper.login();

        SubtopicRequest subtopic =
                new SubtopicRequest(SUBJECT_ID, "OSI Model");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(subtopic)
                .header("Authorization", "Bearer " + LoginHelper.token)
                .when()
                .post("/subtopics/addsubtopic")
                .then()
                .statusCode(403);
    }

    // ---------------------------------------------------
    // Add Subtopic with Admin Token (Positive Test)
    // ---------------------------------------------------
    @Test(priority = 4)
    public void addSubTopicWithValidAdminToken() {

        AdminLoginHelper.login();

        SubtopicRequest subtopic =
                new SubtopicRequest(SUBJECT_ID, "Network Layer");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(subtopic)
                .header("Authorization", "Bearer " + AdminLoginHelper.token)
                .when()
                .post("/subtopics/addSubtopic")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("message", equalTo("Subtopic added successfully"))
                .body("subtopic.subject_id", equalTo(subtopic.getSubject_id()))
                .body("subtopic.name", equalTo(subtopic.getName()))
                .body("subtopic.id", notNullValue())
                .body("subtopic.created_at", notNullValue())
                .log().body();
    }
}
