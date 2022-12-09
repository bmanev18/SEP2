package server.model;

import client.model.Chat;
import shared.Subject;
import shared.util.Message;

import java.util.List;

public interface Model extends Subject {
    void signUp(String firstName,String lastName,String username,String password);

    List<String> getAllUsername();

    String getPassword(String username);

    void updatePassword(String username, String password);
    void updateFirstName(String username,String firstName);
    void updateLastName(String username,String lastName);

    //B

    List<Message> loadMessages(String username);

    List<Chat> loadChats(String username);

    User loadUser(String username);

    Chat createChatWith(String creator, String username);

    void addUser(String username, int id);

    void leaveChat(String username, int id);
}
