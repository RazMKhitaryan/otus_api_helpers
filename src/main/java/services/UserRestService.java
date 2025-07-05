package services;

import io.restassured.RestAssured;
import models.ScoreModel;
import models.UserModel;
import org.springframework.stereotype.Service;

@Service
public class UserRestService extends AbsRestService {

  public ScoreModel getUserById(int userId) {
    return RestAssured.given(requestSpecification())
        .when()
        .get("/user/get/" + userId)
        .then()
        .spec(responseSpecification())
        .extract()
        .as(ScoreModel.class);
  }

  public UserModel getAllUsers() {
    return RestAssured.given(requestSpecification())
        .when()
        .get("/user/get/all")
        .then()
        .spec(responseSpecification())
        .extract()
        .as(UserModel.class);
  }
}
