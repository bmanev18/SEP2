package client.networking;

import client.model.Chat;
import server.model.User;
import shared.util.Message;
import shared.Subject;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Client extends Subject {
    void toCallback(Message message);

    void toModel(Message message);

    void startClient();

    void changeUsername(String username);

    Chat requestSearchFromCallback(String username) throws RemoteException;

    void addUser(String username, int id);

    void leaveChat(String username, int id);

    List<Chat> loadChats(String username);

    Map<Integer, List<Message>> loadMessages(String username);

    User loadUser(String username);

    void updateUser(String firstName, String lastName, String username, String password);
}
