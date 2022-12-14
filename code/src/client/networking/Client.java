package client.networking;

//B

import client.model.Chat;
import server.model.User;
//------

import shared.util.Message;
import shared.Subject;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Client extends Subject {
    void toCallback(Message message);

    void startClient();

    void changeUsername(String username) throws RemoteException;

    void signUp(String firstName, String lastName, String username, String password);

    List<String> getUsernames();

    void disconnect(String username);

    String getPassword(String username);

    void updatePassword(String username, String password);

    void updateFirstName(String username, String firstName);

    void updateLastName(String username, String lastName);

    //B

    void startChatWith(String creator, String invited, String chatName) throws RemoteException;

    void addUserToChat(String username, int id);

    void leaveChat(String username, int id);

    List<Chat> loadChats(String username);

    Map<Integer, List<Message>> loadMessages(String username);

    User loadUser(String username);

    void updateUser(String firstName, String lastName, String username, String password);

    User getUser(String username);

    void changeColour(Chat chatId, String colour) throws RemoteException;
}