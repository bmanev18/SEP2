package client.networking;

import server.networking.RMIServerImpl;
import shared.networking.ClientCallback;
import shared.networking.RMIServer;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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

    private void denied(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange("SignUpDenied",null,null);
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
    public String requestStats() throws RemoteException {
        return server.getStats();
    }

    @Override
    public void signUp(String firstName, String lastName, String username, String password) throws RemoteException {
        server.signUp(firstName,lastName,username,password);
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }
    @Override
    public List<String> getUsernames() {
        try {
            return server.getAllUsernames();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
