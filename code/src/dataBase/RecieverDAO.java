package dataBase;

import shared.util.Message;

import java.sql.SQLException;

public interface RecieverDAO
{
  void addMessagesToChats(Message message) throws SQLException;
  void addReceiverToChat(String username, int chat) throws SQLException;
  void removeReceiverFromChat(String username, int chatID) throws SQLException;
}
