package client.model;

import server.model.User;
import shared.Subject;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Map;

public interface Model extends Subject {

    void send(Message message);

    void receive(PropertyChangeEvent event);

    void changeUsername(String username);

    void changeCurrentChat(Chat chat);

    Chat startChatWith(String username);

    void signUp(String firstName, String lastName, String username, String password);

    List<String> getUsernames();

    void disconnect();

    String getPassword(String username);

    void updatePassword(String username,String password);
    void updateFirstName(String username,String firstName);
    void updateLastName(String username,String lastName);

    // B
    User startChatWith();

    void addUser(User user, Chat currentlyOpenedChat);

    void leaveChat(String username, int id);

    List<Chat> loadChats();

    Map<Integer, List<Message>> loadMessages();

    void updateUser(String firstName, String lastName, String username, String password);
}
