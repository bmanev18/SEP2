package dataBase;

import shared.util.Message;

import java.sql.*;

public class MessageDAOImpl implements MessageDAO
{
  private static MessageDAOImpl instance;

  private MessageDAOImpl() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized MessageDAOImpl getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new MessageDAOImpl();
    }
    return instance;
  }

  public Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/sep2_database?currentSchema=sep2_database",
        "postgres", "admin");
  }

  @Override public Message createMessage(Message message) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO messages(sender,toChat,text,datetime,ID) VALUES (?,?,?,?,?)");
      ResultSet Id = statement.getGeneratedKeys();
      statement.setString(1, message.getUsername());
      statement.setInt(2, 1);
      statement.setString(3, message.getMessageBody());
      statement.setString(4, message.dateTime());
      statement.execute();
    }
    return new Message(message.getMessageBody(), message.getUsername());
  }

  /*@Override public void deleteMessage(Message message) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "DELETE FROM messages WHERE ID = ?");
      statement.setInt(1, message.getId());
      statement.executeUpdate();
    }
  }*/
}