package sqlTest;

import lombok.SneakyThrows;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import services.SqlService;
import java.sql.ResultSet;

public class MySqlDatabaseTest {

  private SqlService sqlService;

  @BeforeMethod
  public void createTable() {
    sqlService = new SqlService();
    sqlService.createTable();
  }

  @SneakyThrows
  @Test
  public void testInsertAndSelect() {
    SoftAssert softAssert = new SoftAssert();
    String testName = "Test Name";
    sqlService.insertRecord(testName);
    ResultSet resultSet = sqlService.selectRecord(testName);
    softAssert.assertEquals(resultSet.getString("name"), testName, "Record name does not match");
    softAssert.assertEquals(resultSet.getInt("id"), 1, "Record id does not match");
    softAssert.assertAll();
  }
}