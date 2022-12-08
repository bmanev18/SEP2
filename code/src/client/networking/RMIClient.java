package client.networking;

import client.model.Chat;
import server.model.User;
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
import java.util.List;
import java.util.Map;

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
    public void changeUsername(String username) {
        this.username = username;
    }

    @Override
    public Chat requestSearchFromCallback(String username) throws RemoteException {
        return server.search(username);
    }


    @Override
    public String getUsername() throws RemoteException {
        return username;
    }

    @Override
    public void addUser(String username, int id) {
        try {
            server.addUser(username, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void leaveChat(String username, int id) {
        try {
            server.leaveChat(username, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Chat> loadChats(String username) {
        try {
            return server.loadChats(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, List<Message>> loadMessages(String username) {
        try {
            return server.loadMessages(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User loadUser(String username) {
        try {
            return server.loadUser(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(String firstName, String lastName, String username, String password) {
        try {
            server.updateUser(firstName, lastName, username, password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);

    }
}
