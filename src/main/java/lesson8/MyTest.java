package lesson8;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MyTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void shouldCheckGetRequestWithParams() {
        String testId = "123";
        given()
                .queryParam("id", testId)
                .when()
                .get("/get")
                .then()
                .log().body()
                .statusCode(200)
                .body("args.id", equalTo(testId));
    }

    @Test
    public void shouldCheckPostRequestWithJson() {
        Person person = new Person(555);
        given()
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(person.getId()));
    }

    @Test
    public void shouldCheckPutRequestWithJson() {
        Person person = new Person(777);
        given()
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .put("/put")
                .then()
                .statusCode(200)
                .body("json.id", equalTo(person.getId()));
    }

    @Test
    public void shouldCheckPatchRequestWithJson() {
        Person person = new Person(999);
        given()
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .patch("/patch")
                .then()
                .statusCode(200)
                .body("json.id", equalTo(person.getId()));
    }

    @Test
    public void shouldCheckDeleteRequestWithParams() {
        String deleteId = "404";
        given()
                .queryParam("id", deleteId)
                .when()
                .delete("/delete")
                .then()
                .statusCode(200)
                .body("args.id", equalTo(deleteId));
    }
}