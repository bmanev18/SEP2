package server.networking;

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
    public String getStats() {
        StringBuilder string = new StringBuilder();
        string.append(clients.keySet().size());
        string.append(" users -> ");
        for (ClientCallback client : clients.keySet()) {
            try {
                string.append(client.getUsername());
                string.append(" ");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        return String.valueOf(string);
    }

    @Override
    public User search(String username) throws RemoteException {
        //TODO
        return null;
    }
}
