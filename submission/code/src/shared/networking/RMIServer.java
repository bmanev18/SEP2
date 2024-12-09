package shared.networking;

import client.model.Chat;
import server.model.User;
import shared.util.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RMIServer extends Remote {
    void startServer() throws RemoteException;

    void registerClient(ClientCallback client) throws RemoteException;

    void broadcast(Message message) throws RemoteException;

    void signUp(String firstName, String lastName, String username, String password) throws RemoteException;

    List<String> getAllUsernames() throws RemoteException;

    void disconnect(String username) throws RemoteException;

    boolean getPassword(String username, String password) throws RemoteException;

    void updatePassword(String username, String password) throws RemoteException;

    void updateFirstname(String username, String firstName) throws RemoteException;

    void updateLastname(String username, String lastName) throws RemoteException;

    //B

    Map<Integer, List<Message>> loadMessages(String username) throws RemoteException;

    List<Chat> loadChats(String username) throws RemoteException;

    User loadUser(String username, ClientCallback client) throws RemoteException;

    Chat startChatWith(String creator, String username, String chatName) throws RemoteException;

    void addUser(String username, int id) throws RemoteException;

    void leaveChat(String username, int chatId) throws RemoteException;

    void updateUser(String firstName, String lastName, String password, String username) throws RemoteException;

    User getUser(String username, ClientCallback client) throws RemoteException;

    void changeColour(Chat chatId, String colour) throws RemoteException;

    boolean usernameAvailability(String username) throws RemoteException;

    ArrayList<String> getInfoForUser(String username) throws RemoteException;
}
