package services;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import java.sql.*;

@Service
public class SqlService {

  private static final String DB_URL = "jdbc:mysql://45.132.17.22:3306/project_database";
  private static final String DB_USER = "project_user";
  private static final String DB_PASSWORD = "user_password_placeholder";

  @SneakyThrows
  public Connection getConnection() {
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
  }

  @SneakyThrows
  public void createTable() {
    Connection connection = getConnection();
    Statement statement = connection.createStatement();
    String createTableQuery = "CREATE TABLE IF NOT EXISTS test_table (" +
        "id INT AUTO_INCREMENT PRIMARY KEY, " + "name VARCHAR(255) NOT NULL, " +
        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")";
    statement.execute(createTableQuery);
    System.out.println("Table 'test_table' created or already exists.");
  }

  @SneakyThrows
  public void insertRecord(String name) {
    String insertQuery = "INSERT INTO test_table (name) VALUES (?)";
    Connection connection = getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
    preparedStatement.setString(1, name);
    int rowsInserted = preparedStatement.executeUpdate();
    System.out.println(rowsInserted > 0 ?
        "Record inserted successfully: " + name :
        "Failed to insert record: " + name);
  }

  @SneakyThrows
  public ResultSet selectRecord(String name) {
    String selectQuery = "SELECT * FROM test_table WHERE name = ?";
    Connection connection = getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
    preparedStatement.setString(1, name);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      System.out.println("ID: " + resultSet.getInt("id"));
      System.out.println("Name: " + resultSet.getString("name"));
      System.out.println("Created At: " + resultSet.getTimestamp("created_at"));
    } else {
      System.out.println("No record found with name: " + name);
    }
    return resultSet;
  }
}