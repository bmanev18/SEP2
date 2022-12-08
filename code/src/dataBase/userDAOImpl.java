package dataBase;

import server.model.User;

import java.sql.*;

public class userDAOImpl implements userDAO
{
  private static userDAOImpl instance;

  private userDAOImpl() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized userDAOImpl getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new userDAOImpl();
    }
    return instance;
  }

  public Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/sep2_database?currentSchema=sep2_database",
        "postgres", "admin");
  }

  @Override public User create() throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO users(firstName,lastName,password,username) VALUES (?,?,?,?);");
      statement.setString(1, "Cristiano");
      statement.setString(2, "Ronaldo");
      statement.setString(3, "SIU");
      statement.setString(4, "CR7");
      statement.execute();
    }
    return new User("CR7", "Cristiano", "Ronaldo", "SIU");
  }

  @Override public User findUser(String user) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM users WHERE username=?;");
      statement.setString(1, user);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        String firstName = resultSet.getNString("firstName");
        String lastName = resultSet.getNString("lastName");
        String username = resultSet.getNString("username");
        String password = resultSet.getNString("password");

        return new User(firstName, lastName, username, password);
      }
      else
      {
        return null;
      }
    }
  }
}


