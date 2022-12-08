package client.model;

import client.networking.Client;
import shared.networking.ClientCallback;
import shared.networking.RMIServer;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;

public class ModelManager implements Model {
    private PropertyChangeSupport support;
    private Client client;
    private String username;

    public ModelManager(Client client) {
        this.client = client;
        client.startClient();
        support = new PropertyChangeSupport(this);
        client.addListener("NewMessage", this::receive);
        username = "none";
    }
    
    @Override
    public void send(String text) {
        client.toCallback(new Message(text, username));
    }

    @Override
    public void receive(PropertyChangeEvent event) {
        Message msg = (Message) event.getNewValue();
        System.out.println("Model Manager::receive " + msg);
        support.firePropertyChange("MessageReceived", null, msg);
    }

    @Override
    public void changeUsername(String username) {
        client.changeUsername(username);
        this.username = username;
    }

    @Override
    public void signUp(String firstName,String lastName,String username,String password){
        client.signUp(firstName,lastName,username,password);
    }

    @Override
    public List<String> getUsernames() {
        return client.getUsernames();
    }

    @Override
    public void disconnect(){
        client.disconnect((ClientCallback) client);
    }

    @Override
    public String getPassword(String username) {
        return client.getPassword(username);
    }

    @Override
    public void updatePassword(String username, String password) {
        client.updatePassword(username,password);
    }

    @Override
    public void updateFirstName(String username, String firstName) {
        client.updateFirstName(username,firstName);

    }

    @Override
    public void updateLastName(String username, String lastName) {
        client.updateLastName(username,lastName);
    }

    @Override
    public void requestStats() {
        String s = null;

        try {
            s = client.requestStats();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Message message = new Message(s, "Server says");
        support.firePropertyChange("MessageReceived", null, message);

    }


    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName, listener);
    }

}
