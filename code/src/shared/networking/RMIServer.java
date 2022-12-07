package shared.networking;

import client.networking.RMIClient;
import server.model.User;
import shared.util.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer extends Remote {
    public void startServer() throws RemoteException;

    void registerClient(ClientCallback client) throws RemoteException;

    void broadcast(Message message) throws RemoteException;

    String getStats() throws RemoteException;

    User search(String username) throws RemoteException;
}
