package base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import services.CourseRestService;
import services.UserRestService;
import springmvc.SpringMvcApplication;

@SpringBootTest(classes = SpringMvcApplication.class)
public class TestBase extends AbstractTestNGSpringContextTests {
  @Autowired
  protected UserRestService userRestService;

  @Autowired
  protected CourseRestService courseRestService;
}
