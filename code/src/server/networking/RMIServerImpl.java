package server.networking;

import client.model.Chat;
import server.model.Broadcast;
import server.model.BroadcastImpl;
import server.model.ModelImpl;
import server.model.User;
import shared.networking.ClientCallback;
import shared.networking.RMIServer;
import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RMIServerImpl implements RMIServer {

    private Broadcast broadcast;
    private Map<ClientCallback, PropertyChangeListener> clients;
    private ModelImpl model;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ArrayList<PropertyChangeListener> pcl;

    public RMIServerImpl() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        model = new ModelImpl();
        broadcast = new BroadcastImpl();
        clients = new HashMap<>();
        pcl = new ArrayList<>();
    }


    @Override
    public void startServer() throws RemoteException {
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(1099);
            registry.bind("Server", this);
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server online");
    }


    @Override
    public void registerClient(ClientCallback client) throws RemoteException {
        PropertyChangeListener listener;

        listener = evt -> {
            try {
                client.receive((Message) evt.getNewValue());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        };
        broadcast.addListener("NewMessage", listener);
        pcl.add(listener);

        clients.put(client, listener);
        System.out.println("Passed");
    }

    @Override
    public void broadcast(Message message) throws RemoteException {
        broadcast.broadcast(message);
    }

    public List<String> getAllUsernames() {
        return model.   getAllUsername();
    }

    @Override
    public void disconnect(ClientCallback clientCallback) {
        PropertyChangeListener remove = clients.remove(clientCallback);
        broadcast.removeListener("NewMessage", remove);
        pcl.remove(remove);

    }

    @Override
    public String getPassword(String username) {
        return model.getPassword(username);
    }

    @Override
    public void updatePassword(String username, String password) {
        model.updatePassword(username, password);
    }

    @Override
    public void updateFirstname(String username, String firstName) {
        model.updateFirstName(username, firstName);

    }

    @Override
    public void updateLastname(String username, String lastName) {
        model.updateLastName(username, lastName);
    }

    @Override
    public void signUp(String firstName, String lastName, String username, String password) {
        model.signUp(firstName, lastName, username, password);
    }

    //B

    @Override
    public Map<Integer, List<Message>> loadMessages(String username) {
        Map<Integer, List<Message>> map = new HashMap<>();
        List<Message> messages = model.loadMessages(username);

        for (Message message : messages) {
            int toChat = message.getToChat();
            map.putIfAbsent(toChat, new ArrayList<>());
            map.get(toChat).add(message);
        }
        return map;
    }

    @Override
    public List<Chat> loadChats(String username) {
        return model.loadChats(username);
    }

    @Override
    public User loadUser(String username) {
        return model.loadUser(username);
    }

    @Override
    public Chat startChatWith(String creator, String username) throws RemoteException {
        Chat chat = model.createChatWith(creator, username);
        // Notify and send chat
        return chat;
    }

    @Override
    public void addUser(String username, int id) {
        model.addUser(username, id);
        // Notify and send chat
    }

    @Override
    public void leaveChat(String username, int id) {
        model.leaveChat(username, id);
        //TODO
    }

    @Override
    public void updateUser(String firstName, String lastName, String username, String password) {
        //TODO
    }
}
