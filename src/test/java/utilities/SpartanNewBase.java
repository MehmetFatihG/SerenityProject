package utilities;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class SpartanNewBase {

    public static RequestSpecification adminSpec;
    public static ResponseSpecification responseSpec;
    public static RequestSpecification userSpec;
    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://52.200.34.122";
        port = 7000;
        basePath = "/api";

        adminSpec = RestAssured.given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .log().all();

        userSpec = RestAssured.given().accept(ContentType.JSON)
                .auth().basic("user", "user")
                .log().all();

        responseSpec = RestAssured.expect().statusCode(200)
                .contentType(ContentType.JSON).logDetail(LogDetail.ALL);
    }

    @AfterAll
    public static void close(){
        //it resets the info we set above
        reset();
    }



}
