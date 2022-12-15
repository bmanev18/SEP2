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
        //broadcast.addListener("NewMessage", listener);
        clients.put(client, listener);
        pcl.add(listener);

        System.out.println("Passed");
    }

    @Override
    public void broadcast(Message message) throws RemoteException {
        //Add the message to Database
        model.addMessage(message);

        //Fetch all receivers
        List<ClientCallback> clients = model.loadReceivers(message.getToChat());

        //Create a list of receivers' listeners
        List<PropertyChangeListener> list = new ArrayList<>();

        //Fetch listeners from this.clients and add to listeners
        for (ClientCallback client : clients) {
            list.add(this.clients.get(client));
        }

        // Broadcast the message
        broadcast.broadcastMessage(message, list);
    }

    public List<String> getAllUsernames() {
        return model.getAllUsername();
    }

    @Override
    public void disconnect(String username) {
        // Remove client from model and save the object for next step
        ClientCallback client = model.removeClient(username);
        // Remove the listener from clients
        clients.remove(client);

    }

    @Override
    public boolean getPassword(String username, String password) {
        return model.getPassword(username).equals(password);
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
    public User loadUser(String username, ClientCallback client) {
        return model.loadUsername(username);
    }

    @Override
    public Chat startChatWith(String creator, String username, String chatName) throws RemoteException {
        System.out.println("Server1");
        Chat chat = model.createChatWith(creator, username, chatName);
        System.out.println("Server2");
        broadcast.sendNewChat(model.getClient(username), chat);
        return chat;
    }

    @Override
    public void addUser(String username, int id) {
        model.addUser(username, id);
        // Notify and send chat
    }

    @Override
    public void leaveChat(String username, int chatId) {
        model.leaveChat(username, chatId);
        try {
            Message message = new Message("@server@", chatId, username + " has left the chat");
            System.out.printf("%s -> %d: %s %s", message.getSender(), message.getToChat(), message.getMessageBody(), message.getDateTime());
            broadcast(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(String username, ClientCallback client) {
        return model.getUser(username, client);
    }

    @Override
    public void changeColour(Chat chat, String colour) {
        //broadcast to all receivers
        broadcast.changeColour(chat, model.changeColour(chat, colour), model.loadReceivers(chat.getId()));
    }

    @Override
    public boolean usernameAvailability(String username) {
        return model.usernameAvailability(username);
    }

    @Override
    public ArrayList<String> getInfoForUser(String username) {
        return model.getInfoForUser(username);
    }

    @Override
    public void updateUser(String firstName, String lastName, String password, String username) {
        model.updateUser(firstName, lastName, password, username);
    }
}
