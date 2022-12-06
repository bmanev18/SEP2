package dataBase;

import shared.util.Message;

import java.sql.SQLException;

public interface MessageDAO
{
  Message createMessage(Message message) throws SQLException;
 // void deleteMessage(Message message) throws  SQLException;
}
