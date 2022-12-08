package server.networking;

import client.model.Chat;
import server.model.Broadcast;
import server.model.BroadcastImpl;
import server.model.User;
import shared.networking.ClientCallback;
import shared.networking.RMIServer;
import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RMIServerImpl implements RMIServer {

    private Broadcast broadcast;
    private Map<ClientCallback, PropertyChangeListener> clients;

    public RMIServerImpl() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        broadcast = new BroadcastImpl();
        clients = new HashMap<>();
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
    public Map<Integer, List<Message>> loadMessages(String username) {
        //TODO
        Map<Integer, List<Message>> map = new HashMap<>();
        /*List<Message> messages = dao.requestMessages(username);

        for (Message message : messages) {
            int toChat = message.getToChat();
            map.putIfAbsent(toChat, new ArrayList<>());
            map.get(toChat).add(message);
        }*/
        return map;
    }

    @Override
    public List<Chat> loadChats(String username) {
        //TODO
        //return dao.requestChats(username);

        return null;
    }

    @Override
    public User loadUser(String username) {
        //return UserDao.requestUser(username)
        return null;
    }


    @Override
    public void registerClient(ClientCallback client) throws RemoteException {
        PropertyChangeListener listener;

        listener = evt -> {
            try {
                client.receive((Message) evt.getNewValue());
            } catch (RemoteException e) {
                throw new RuntimeException("Lambda");
            }
        };
        broadcast.addListener("NewMessage", listener);

        clients.put(client, listener);
        System.out.println("Passed");
    }

    @Override
    public void broadcast(Message message) throws RemoteException {
        broadcast.broadcast(message);
    }

    @Override
    public Chat search(String username) throws RemoteException {
        //TODO find user(if not existent return null), create a new chat in |Chat|, subscribe both users to this chat
        return null;
    }

    @Override
    public void addUser(String username, int id) {
        //TODO
    }

    @Override
    public void leaveChat(String username, int id) {
        //TODO
    }

    @Override
    public void updateUser(String firstName, String lastName, String username, String password) {
        //TODO
    }
}
