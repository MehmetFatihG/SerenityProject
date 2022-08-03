package eu8.spartan.admin;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;

@SerenityTest
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

    @Test
    public void getOneSpartan(){

        SerenityRest.given()
                .accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id",10)
                .when()
                .get("api/spartans/{id}");

        //if you send a request using SerenityRest, the response object
        //can be obtained from the method called lastResponse() without being saved separately
        //same with Response object
        System.out.println("Status Code= " + SerenityRest.lastResponse().statusCode());

        //print id
        System.out.println("Get id " + SerenityRest.lastResponse().jsonPath().getInt("id"));
    }

    @DisplayName("GET request with Serenity Assertion way")
    @Test
    public void getOneSpartanAssertion(){

        SerenityRest.given()
                .accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .pathParam("id",10)
                .when()
                .get("api/spartans/{id}");


        //Serenity way of assertion
        Ensure.that("Status code is 200", validatableResponse -> validatableResponse.statusCode(200));
        Ensure.that("Content type is JSON", each -> each.contentType(ContentType.JSON));
        Ensure.that("id is 10", each -> each.body("id",Matchers.is(10)));


    }

}
