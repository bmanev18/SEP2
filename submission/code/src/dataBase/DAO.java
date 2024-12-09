package dataBase;

import client.model.Chat;
import server.model.User;
import shared.util.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DAO {
    User create(String firstName, String lastName, String username, String password) throws SQLException;

    List<String> getUsernames() throws SQLException;

    User loadUser(String username) throws SQLException;

    String getPassword(String username) throws SQLException;

    String updatePassword(String username, String password) throws SQLException;

    String updateLastName(String username, String lastName) throws SQLException;

    String updateFirstName(String username, String firstName) throws SQLException;

    List<Chat> loadChats(String username) throws SQLException;

    Chat createChat(String name) throws SQLException;

    Message addMessage(Message message) throws SQLException;

    List<Message> loadMessages(String username);

    void addReceiverToChat(String username, int chat) throws SQLException;

    void removeReceiverFromChat(String username, int chatID) throws SQLException;

    User findUser(String user) throws SQLException;

    List<String> getReceivers(int toChat) throws SQLException;

    Chat changeColour(int id, String colour);

    ArrayList<String> getInfoForUser(String username) throws SQLException;

    void updateUser(String firstName, String lastName, String password, String username) throws SQLException;
}
