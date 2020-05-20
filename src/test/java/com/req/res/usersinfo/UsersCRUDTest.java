package com.req.res.usersinfo;

import com.req.res.model.UsersPojo;
import com.req.res.testbase.TestBase;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.matchers.JUnitMatchers.hasItems;


// Below @RunWith line for generating serenity report
@RunWith(SerenityRunner.class)
//@FixMethodOrder all test cases in alphabet order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersCRUDTest extends TestBase {
    static String firstName;
    static String job;

    @Title("Create an User Successfully and verify name added")
    @Test
    public void test001() {

        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setName(firstName = "prad");
        usersPojo.setJob(job = "Acting");

        SerenityRest.rest().given()
                .log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(usersPojo)
                .post("/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", equalTo(firstName));

    }

    @Title("Getting User from the record")
    @Test
    public void test002() {

        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setName(firstName = "Michael");

        SerenityRest.rest()
                .header("Content-Type", "application/json")
                .given()
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .body("data.first_name", hasItems(firstName));

    }

    @Title("This test will update details of an user with ID5")
    @Test
    public void test003() {
        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setName(firstName = "Johny");
        usersPojo.setJob(job = "Golf");

        SerenityRest.rest().given()
                .log().all()
                .header("Content-Type", "application/json")
                .when().log().body()
                .body(usersPojo)
                .put("/5")
                .then()
                .statusCode(200).log().body()
                .body("name", equalTo(firstName),
                        "job", equalTo(job));

    }

    @Title("This test will delete an User")
    @Test
    public void test004() {
        SerenityRest.rest().given()
                .when()
                .delete("/users/2")
                .then().statusCode(204)
                .log().status()
                .log().ifValidationFails();

    }

}