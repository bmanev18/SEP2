package client.model;

import client.networking.Client;
import shared.networking.RMIServer;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
    public void requestStats() {
        String s = client.requestStats();
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
