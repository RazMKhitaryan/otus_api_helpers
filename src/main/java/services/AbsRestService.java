package services;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public abstract class AbsRestService {
  protected RequestSpecification requestSpecification() {
    RestAssured.defaultParser = Parser.JSON;
    return RestAssured.given().baseUri(System.getProperty("base.uri"))
        .basePath("/wiremock");
  }

  protected ResponseSpecification responseSpecification() {
    RestAssured.defaultParser = Parser.JSON;
    return RestAssured.given()
        .then()
        .statusCode(200);
  }
}
