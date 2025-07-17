package services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class SqlService {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUser;

  @Value("${spring.datasource.password}")
  private String dbPassword;

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
  }

  public void createTable() {
    String createTableQuery = "CREATE TABLE IF NOT EXISTS test_table ("
        + "id INT AUTO_INCREMENT PRIMARY KEY, "
        + "name VARCHAR(255) NOT NULL, "
        + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    try (Connection connection = getConnection();
         Statement statement = connection.createStatement()) {
      statement.execute(createTableQuery);
      System.out.println("Table 'test_table' created or already exists.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertRecord(String name) {
    String insertQuery = "INSERT INTO test_table (name) VALUES (?)";
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
      preparedStatement.setString(1, name);
      int rowsInserted = preparedStatement.executeUpdate();
      System.out.println(rowsInserted > 0
          ? "Record inserted successfully: " + name
          : "Failed to insert record: " + name);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Map<String, Object> selectRecord(String name) {
    String selectQuery = "SELECT * FROM test_table WHERE name = ?";
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
      preparedStatement.setString(1, name);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          // Collect the result in a Map
          Map<String, Object> record = new HashMap<>();
          record.put("id", resultSet.getInt("id"));
          record.put("name", resultSet.getString("name"));
          record.put("created_at", resultSet.getTimestamp("created_at"));
          return record; // Return the record data
        } else {
          throw new RuntimeException("No record found with name: " + name);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Error while executing selectRecord", e);
    }
  }
}