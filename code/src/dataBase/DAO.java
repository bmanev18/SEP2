package dataBase;

import client.model.Chat;
import server.model.User;
import shared.util.Message;

import java.sql.SQLException;
import java.util.List;

public interface DAO{
    User create(String firstName,String lastName,String username,String password) throws SQLException;

    List<String> getUsernames() throws SQLException;

    String getPassword(String username) throws SQLException;

    String updatePassword(String username,String password) throws SQLException;
    String updateLastName(String username,String lastName) throws SQLException;
    String updateFirstName(String username,String firstName) throws SQLException;
    List<Chat> requestChats(String username) throws SQLException;

    public Chat createChat(String name) throws SQLException;

    Message createMessage(Message message) throws SQLException;
    List<Message> requestData(String user);

    List<Message> loadMessages(String username);

    void addMessagesToChats(Message message) throws SQLException;
    void addReceiverToChat(String username, int chat) throws SQLException;
    void removeReceiverFromChat(String username, int chatID) throws SQLException;

    User create() throws SQLException;
    User findUser(String user) throws SQLException;
}
