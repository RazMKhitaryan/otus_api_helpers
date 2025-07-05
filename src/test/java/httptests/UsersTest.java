package httptests;

import base.TestBase;
import models.ScoreModel;
import models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;

public class UsersTest extends TestBase {

  @Autowired
  ApplicationContext ctx;

  @Test
  public void getUserByIdValidation() {
    ScoreModel userById = userRestService.getUserById(new Random().nextInt());
    Assert.assertEquals("Test user", userById.getName(), "ScoreModel name does not match");
    Assert.assertEquals(78, userById.getScore(), "ScoreModel score does not match");
  }

  @Test
  public void getAllUsersJsonSchemaValidation() {
    UserModel user = userRestService.getAllUsers();
    Assert.assertEquals(23, user.getAge(), "User age does not match");
    Assert.assertEquals("Test user", user.getName(), "User name does not match");
    Assert.assertEquals("QA", user.getCource(), "User course does not match");
    Assert.assertEquals("test@test.test", user.getEmail(), "User email does not match");
  }
}
