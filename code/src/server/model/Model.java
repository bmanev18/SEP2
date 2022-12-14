package server.model;

import client.model.Chat;
import shared.Subject;
import shared.networking.ClientCallback;
import shared.util.Message;

import java.util.List;

public interface Model extends Subject {
    void signUp(String firstName, String lastName, String username, String password);

    List<String> getAllUsername();

    User getUser(String username, ClientCallback client);

    String getPassword(String username);

    void updatePassword(String username, String password);

    void updateFirstName(String username, String firstName);

    void updateLastName(String username, String lastName);


    //B
    void addMessage(Message message);

    List<ClientCallback> loadReceivers(int toChat);

    List<Message> loadMessages(String username);

    List<Chat> loadChats(String username);

    User loadUsername(String username);

    Chat createChatWith(String creator, String invited, String chatName);

    void addUser(String username, int id);

    void leaveChat(String username, int id);

    void addClient(String username, ClientCallback client);

    ClientCallback removeClient(String username);

    ClientCallback getClient(String creator);

    Chat changeColour(Chat chat, String colour);
}
