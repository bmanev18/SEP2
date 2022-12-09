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

    void registerClient(ClientCallback client) throws RemoteException;

    void broadcast(Message message) throws RemoteException;



    void signUp(String firstName, String lastName, String username, String password) throws RemoteException;

    List<String> getAllUsernames() throws RemoteException;

    void disconnect(ClientCallback clientCallback) throws RemoteException;

    String getPassword(String username) throws RemoteException;

    void updatePassword(String username,String password) throws RemoteException;
    void updateFirstname(String username,String firstName) throws RemoteException;
    void updateLastname(String username,String lastName) throws RemoteException;

    //B

    Map<Integer, List<Message>> loadMessages(String username) throws RemoteException;

    List<Chat> loadChats(String username) throws RemoteException;

    User loadUser(String username) throws RemoteException;

    Chat startChatWith(String creator, String username) throws RemoteException;

    void addUser(String username, int id) throws RemoteException;

    void leaveChat(String username, int id) throws RemoteException;

    void updateUser(String firstName, String lastName, String username, String password) throws RemoteException;
}
