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
}
