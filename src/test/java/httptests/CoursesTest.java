package httptests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import base.TestBase;
import org.testng.annotations.Test;

public class CoursesTest extends TestBase {

  @Test
  public void checkAllCourses() {
    courseRestService.getAllCourses().assertThat()
        .body(matchesJsonSchemaInClasspath("schemas/courses-schema.json"));
  }
}