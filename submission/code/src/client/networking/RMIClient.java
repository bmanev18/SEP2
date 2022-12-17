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
import java.util.ArrayList;
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
        support.firePropertyChange("NewMessage", null, message);
    }

    @Override
    public void onNewChat(Chat chat) {
        System.out.println("Client2");
        support.firePropertyChange("AddedToChat", null, chat);
    }

    @Override
    public void changeColour(Chat oldChat, Chat newChat) {
        support.firePropertyChange("ChangedColour", oldChat, newChat);
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
    public void signUp(String firstName, String lastName, String username, String password) {
        try {
            server.signUp(firstName, lastName, username, password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<String> getUsernames() {
        try {
            return server.getAllUsernames();
        } catch (RemoteException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void disconnect(String username) {
        try {
            server.disconnect(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getPassword(String username, String password) {
        try {
            return server.getPassword(username, password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updatePassword(String username, String password) {
        try {
            server.updatePassword(username, password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateFirstName(String username, String firstName) {
        try {
            server.updateFirstname(username, firstName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateLastName(String username, String lastName) {
        try {
            server.updateLastname(username, lastName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    //B

    @Override
    public Chat startChatWith(String creator, String invited, String chatName) throws RemoteException {
        System.out.println("Client");
        return server.startChatWith(creator, invited, chatName);
    }

    @Override
    public void addUserToChat(String username, int id) {
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
            return server.loadUser(username, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(String username) {
        try {
            return server.getUser(username, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeColour(Chat chat, String colour) {
        try {
            server.changeColour(chat, colour);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean usernameAvailability(String username) {
        try {
            return server.usernameAvailability(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> getInfoForUser(String username) {
        try {
            return server.getInfoForUser(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(String firstName, String lastName, String password, String username) {
        try {
            server.updateUser(firstName, lastName, password, username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
