package dataBase;

import shared.util.Message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecieverDAOImpl implements RecieverDAO
{
  private static RecieverDAOImpl instance;

  private RecieverDAOImpl() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized RecieverDAOImpl getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new RecieverDAOImpl();
    }
    return instance;
  }

  public Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2_database","postgres","2121");
  }

  @Override public void addMessagesToChats(Message message) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO chats(id) VALUES (?)");
      statement.setInt(1, message.getToChat());
      statement.execute();
      //needs to be worked on
    }

  }

  @Override public void addReceiverToChat(String username, int chat)
      throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO receivers(username,chat) VALUES (?,?)");
      statement.setString(1, username);
      statement.setInt(2, chat);
      statement.execute();
    }

  }

  @Override public void removeReceiverFromChat(String username, int chatID)
      throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "DELETE FROM receivers(username,chat) VALUES (?,?)");
      statement.setString(1, username);
      statement.setInt(2, chatID);
      statement.executeUpdate();
    }
  }
}

