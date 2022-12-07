package client.model;

import client.networking.Client;
import server.model.User;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public class ModelManager implements Model {
    private PropertyChangeSupport support;
    private Client client;
    private ChatHistory chatHistory;

    public ModelManager(Client client) {
        this.client = client;
        client.startClient();
        support = new PropertyChangeSupport(this);
        client.addListener("NewMessage", this::receive);
        chatHistory = new ChatHistory();
    }


    @Override
    public void send(Message message) {
        /*Message message = chatHistory.send(text);
        client.toCallback(message);
        return message;*/
        client.toCallback(message);

    }

    @Override
    public void receive(PropertyChangeEvent event) {
        Message msg = (Message) event.getNewValue();
        //chatHistory.receive(event);
        support.firePropertyChange("MessageReceived", null, msg);
        System.out.println("Model Manager::receive " + msg);
    }

    @Override
    public void changeUsername(String username) {
        //??
        //chatHistory.changeUsername(username);
        client.changeUsername(username);
    }

    //??
    @Override
    public void changeCurrentChat(Chat chat) {
        //chatHistory.changeCurrentChat(chat);
    }

    @Override
    public User search(String username) throws RemoteException {
        return client.requestSearchFromCallback(username);
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
