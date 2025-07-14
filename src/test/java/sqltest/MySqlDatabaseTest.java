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
import java.sql.ResultSet;

@SpringBootTest(classes = SpringMvcApplication.class)
public class MySqlDatabaseTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private SqlService sqlService;

  @BeforeMethod
  public void createTable() {
    sqlService.createTable();
  }


  @Test
  public void testInsertAndSelect() {
    SoftAssert softAssert = new SoftAssert();
    String testName = "Test Name";
    sqlService.insertRecord(testName); // Insert a record
    boolean recordExists = sqlService.selectRecord(testName);
    softAssert.assertTrue(recordExists, "Record should exist in the database");
    softAssert.assertAll();
  }
}