package client.networking;

import shared.networking.ClientCallback;
import shared.networking.RMIServer;
import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements Client, ClientCallback {
    private RMIServer server;
    private PropertyChangeSupport support;
    private String username;

    public RMIClient() {
        support = new PropertyChangeSupport(this);
        username = "<< none >>";
    }

    @Override
    public void toCallback(Message message) {
        send(message);
    }

    @Override
    public void send(Message message) {
        try {
            server.broadcast(message);
        } catch (RemoteException e) {
            throw new RuntimeException("Can not broadcast");
        }
    }

    @Override
    public void receive(Message message) {
        toModel(message);
    }

    @Override
    public void toModel(Message message) {
        support.firePropertyChange("NewMessage", null, message);
    }

    @Override
    public void startClient() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            server = (RMIServer) registry.lookup("Server");
            server.registerClient(this);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connected");
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);

    }

    @Override
    public void changeUsername(String username) {
        this.username = username;
    }

    @Override
    public String requestStats() {
        try {
            return server.getStats();
        } catch (RemoteException e) {
            throw new RuntimeException("Can not return stats");
        }
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }
}
