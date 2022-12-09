package dataBase;

import shared.util.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDAO
{
  Message createMessage(Message message, int chat) throws SQLException;
  List<Message> requestData(String user);

  List<Message> loadMessages(String username);
  // void deleteMessage(Message message) throws  SQLException;
}
