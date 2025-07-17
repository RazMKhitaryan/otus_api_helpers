package sqltest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import services.SqlService;
import springmvc.SpringMvcApplication;
import java.util.Map;

@SpringBootTest(classes = SpringMvcApplication.class)
public class MySqlDatabaseTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private SqlService sqlService;

  @BeforeMethod
  public void createTable() {
    sqlService.createTable();
  }

  @SneakyThrows
  @Test
  public void testInsertAndSelect() {
    SoftAssert softAssert = new SoftAssert();
    String testName = "Test Name";
    sqlService.insertRecord(testName);
    Map<String, Object> record = sqlService.selectRecord(testName);
    softAssert.assertEquals(record.get("id"), 1);
    softAssert.assertEquals(record.get("name"), testName);
    softAssert.assertAll();
  }
}