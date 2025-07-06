package wiremocktests.soap.httptests;

import wiremocktests.soap.base.TestBase;
import models.ScoreModel;
import models.UserModel;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.Random;

public class UsersTest extends TestBase {

  @Test
  public void getUserByIdValidation() {
    SoftAssert softAssert = new SoftAssert();
    ScoreModel userById = userRestService.getUserById(new Random().nextInt());
    softAssert.assertEquals("Test user", userById.getName(), "ScoreModel name does not match");
    softAssert.assertEquals(78, userById.getScore(), "ScoreModel score does not match");
    softAssert.assertAll();
  }

  @Test
  public void getAllUsersJsonSchemaValidation() {
    SoftAssert softAssert = new SoftAssert();
    UserModel user = userRestService.getAllUsers();
    softAssert.assertEquals(23, user.getAge(), "User age does not match");
    softAssert.assertEquals("Test user", user.getName(), "User name does not match");
    softAssert.assertEquals("QA", user.getCource(), "User course does not match");
    softAssert.assertEquals("test@test.test", user.getEmail(), "User email does not match");
    softAssert.assertAll();
  }
}
