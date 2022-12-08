package shared.networking;

import client.networking.RMIClient;
import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIServer extends Remote {
    public void startServer() throws RemoteException;

    void registerClient(ClientCallback client) throws RemoteException;

    void broadcast(Message message) throws RemoteException;

    String getStats() throws RemoteException;

    void signUp(String firstName, String lastName, String username, String password) throws RemoteException;

    List<String> getAllUsernames() throws RemoteException;

    void disconnect(ClientCallback clientCallback) throws RemoteException;

    String getPassword(String username) throws RemoteException;

    void updatePassword(String username,String password) throws RemoteException;
    void updateFirstname(String username,String firstName) throws RemoteException;
    void updateLastname(String username,String lastName) throws RemoteException;
}
