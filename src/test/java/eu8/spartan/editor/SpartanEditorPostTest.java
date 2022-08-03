package eu8.spartan.editor;

import io.cucumber.java.af.En;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.SpartanNewBase;
import utilities.SpartanUtil;

import java.util.LinkedHashMap;
import java.util.Map;

@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {

    @DisplayName("Editor should be able to POST")
    @Test
    public void postSpartanAsEditor(){

        //when you need serialization or deserialization, you don't need to add dependency
        //create one spartan using util
        Map<String, Object> bodyMap = SpartanUtil.getRandomSpartanMap();
        System.out.println(bodyMap);

        //send a post request as Editor
         SerenityRest.given().accept(ContentType.JSON)
                .auth().basic("editor", "editor")
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .log().body()
                .when()
                .post("/spartans")
                .then().log().all();

        //status code is 201
        Ensure.that("Status Code is 201", statusCode -> statusCode.statusCode(201));

        //content type is Json
        Ensure.that("contentType = JSON",content -> content.contentType(ContentType.JSON));

        //success message is A Spartan is Born!
        Ensure.that("success message", message -> message
                .body("success", Matchers.is("A Spartan is Born!")));

        //id is not null>
        Ensure.that("id is not null",id -> id.body("data.id",Matchers
                .notNullValue()));

        //name is correct
        Ensure.that("name is correct",name -> name.body("data.name",
                    Matchers.equalTo(bodyMap.get("name")) ));

        //gender is correct
        Ensure.that("gender is correct",name -> name.body("data.gender",
                Matchers.equalTo(bodyMap.get("gender")) ));

        //phone is correct
        Ensure.that("phone is correct",name -> name.body("data.phone",
                Matchers.equalTo(bodyMap.get("phone")) ));

        //check location header ends with newly generated id
        String id = SerenityRest.lastResponse().jsonPath().getString("data.id");
        Ensure.that("location header",
                header -> header.header("Location", Matchers.endsWith(id)));
    }

          /* we can give name to each execution using name = ""
            and if you want to get index of iteration we can use {index}
            and also if you to include parameter in your test name
            {0} , {1},{2} --> based on the order you provide as argument.*/
    @ParameterizedTest(name = "New Spartan {index} - name: {0}")
    @CsvFileSource(resources = "/SpartanData.csv",numLinesToSkip = 1)
    public void postSpartanWithCsv(String name, String gender, long phone){

        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name",name);
        bodyMap.put("gender",gender);
        bodyMap.put("phone",phone);

        //send a post request as Editor
        SerenityRest.given().accept(ContentType.JSON)
                .auth().basic("editor", "editor")
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .log().body()
                .when()
                .post("/spartans")
                .then().log().all();

        //status code is 201
        Ensure.that("Status Code is 201", statusCode -> statusCode.statusCode(201));

        //content type is Json
        Ensure.that("contentType = JSON",content -> content.contentType(ContentType.JSON));

        //success message is A Spartan is Born!
        Ensure.that("success message", message -> message
                .body("success", Matchers.is("A Spartan is Born!")));

        //id is not null>
        Ensure.that("id is not null",id -> id.body("data.id",Matchers
                .notNullValue()));

        //name is correct
        Ensure.that("name is correct",correctName -> correctName.body("data.name",
                Matchers.equalTo(bodyMap.get("name")) ));

        //gender is correct
        Ensure.that("gender is correct",correctGender -> correctGender.body("data.gender",
                Matchers.equalTo(bodyMap.get("gender")) ));

        //phone is correct
        Ensure.that("phone is correct",correctPhone -> correctPhone.body("data.phone",
                Matchers.equalTo(bodyMap.get("phone")) ));

        //check location header ends with newly generated id
        String id = SerenityRest.lastResponse().jsonPath().getString("data.id");
        Ensure.that("location header",
                header -> header.header("Location", Matchers.endsWith(id)));
    }

}
