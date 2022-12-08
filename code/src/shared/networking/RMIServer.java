package shared.networking;

import client.model.Chat;
import server.model.User;
import shared.util.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RMIServer extends Remote {
    void startServer() throws RemoteException;

    Map<Integer, List<Message>> loadMessages(String username) throws RemoteException;

    List<Chat> loadChats(String username) throws RemoteException;

    User loadUser(String username) throws RemoteException;

    void registerClient(ClientCallback client) throws RemoteException;

    void broadcast(Message message) throws RemoteException;

    Chat search(String username) throws RemoteException;

    void addUser(String username, int id) throws RemoteException;

    void leaveChat(String username, int id) throws RemoteException;

    void updateUser(String firstName, String lastName, String username, String password) throws RemoteException;
}
