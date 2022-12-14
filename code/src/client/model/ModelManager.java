package client.model;

import client.networking.Client;
import server.model.User;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ModelManager implements Model {
    private PropertyChangeSupport support;
    private Client client;
    private String username;
    private Chat currentlyOpened;

    public ModelManager(Client client) {
        this.client = client;
        client.startClient();
        support = new PropertyChangeSupport(this);
        client.addListener("NewMessage", this::receive);
        client.addListener("AddedToChat", this::addedToChat);
        client.addListener("ColourChanged", this::changeColour);
        username = null;
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
        support.firePropertyChange("MessageReceived", null, msg);
        System.out.println("Model Manager::receive " + msg);
    }

    @Override
    public void addedToChat(PropertyChangeEvent event) {
        Chat chat = (Chat) event.getNewValue();
        System.out.println("ModelManager2");
        support.firePropertyChange("AddedToChat", null, chat);
        System.out.println("Model Manager::added to " + chat);

    }

    @Override
    public void changeUsername(String username) {
        this.username = username;
    }

    @Override
    public void signUp(String firstName, String lastName, String username, String password) {
        client.signUp(firstName, lastName, username, password);
    }

    @Override
    public List<String> getUsernames() {
        return client.getUsernames();
    }

    @Override
    public void disconnect() {
        client.disconnect(username);
    }

    @Override
    public String getPassword(String username) {
        return client.getPassword(username);
    }

    @Override
    public void updatePassword(String username, String password) {
        client.updatePassword(username, password);
    }

    @Override
    public void updateFirstName(String username, String firstName) {
        client.updateFirstName(username, firstName);

    }

    @Override
    public void updateLastName(String username, String lastName) {
        client.updateLastName(username, lastName);
    }

    //B

    @Override
    public void changeCurrentChat(Chat chat) {
        //chatHistory.changeCurrentChat(chat);
    }

    @Override
    public void startChatWith(String username, String chatName) {
        try {
            System.out.println("ModelManager");
            client.startChatWith(this.username, username, chatName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addUser(String username, Chat chat) {
        client.addUserToChat(username, chat.getId());
    }

    @Override
    public void leaveChat(String username, int id) {
        client.leaveChat(username, id);
    }

    @Override
    public List<Chat> loadChats() {
        return client.loadChats(username);
    }

    @Override
    public Map<Integer, List<Message>> loadMessages() {
        return client.loadMessages(username);
    }

    @Override
    public void updateUser(String firstName, String lastName, String username, String password) {
        client.updateUser(firstName, lastName, username, password);
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public User getUser() {
        return client.getUser(username);
    }

    @Override
    public void changeColour(Chat chat, String colour) {
        try {
            client.changeColour(chat, colour);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeColour(PropertyChangeEvent event) {
        support.firePropertyChange("ColourChanged", event.getOldValue(), event.getNewValue());
    }

}