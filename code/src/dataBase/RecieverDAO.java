package dataBase;

import server.model.User;
import shared.util.Message;

import java.sql.SQLException;

public interface RecieverDAO
{
  void addMessagesToChats(Message message) throws SQLException;
  void addRecieverToChat(User username, int chat) throws SQLException;
  void removeRecieverFromChat(User username, int chatID) throws SQLException;
}
