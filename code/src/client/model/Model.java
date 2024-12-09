package client.model;

import server.model.User;
import shared.Subject;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Model extends Subject {

    void send(Message message);

    void receive(PropertyChangeEvent event);

    void changeUsername(String username);

    void changeCurrentChat(Chat chat);

    Chat startChatWith(String username, String chatName);

    void signUp(String firstName, String lastName, String username, String password);

    List<String> getUsernames();

    void disconnect();

    boolean getPassword(String username, String password);

    void updatePassword(String username, String password);

    void updateFirstName(String username, String firstName);

    void updateLastName(String username, String lastName);

    // B
    void addUser(String username, Chat currentlyOpenedChat);

    void leaveChat(String username, int id);

    List<Chat> loadChats();

    Map<Integer, List<Message>> loadMessages();

    void addedToChat(PropertyChangeEvent propertyChangeEvent);

    void updateUser(String firstName, String lastName, String password, String username);

    User getUser();

    void changeColour(Chat chatId, String colour);

    void changeColour(PropertyChangeEvent event);

    boolean usernameAvailability(String username);

    ArrayList<String> getInfoForUser(String username);

    String getUsername();
}
