package eu8.spartan.admin;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;

public class SpartanAdminGetTest {

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://52.200.34.122:7000";

    }

    @Test
    public void getAllSpartan(){

        RestAssured.given()
                            .accept(ContentType.JSON)
                            .auth().basic("admin", "admin")
                    .when()
                            .get("api/spartans")
                    .then()
                            .statusCode(200)
                            .contentType(ContentType.JSON);
    }

}
