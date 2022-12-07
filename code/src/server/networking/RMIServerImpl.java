package server.networking;

import server.model.Broadcast;
import server.model.BroadcastImpl;
import server.model.Model;
import server.model.ModelImpl;
import shared.Subject;
import shared.networking.ClientCallback;
import shared.networking.RMIServer;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
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

public class RMIServerImpl implements RMIServer{

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

    public List<String> getAllUsernames(){
        return model.getAllUsername();
    }

    @Override
    public void disconnect(ClientCallback clientCallback) {
        PropertyChangeListener remove = clients.remove(clientCallback);
        broadcast.removeListener("NewMessage",remove);
        pcl.remove(remove);

    }

    @Override
    public String getPassword(String username){
        return model.getPassword(username);
    }

    @Override
    public void signUp(String firstName, String lastName, String username, String password) {
        model.signUp(firstName,lastName,username,password);
    }


}
