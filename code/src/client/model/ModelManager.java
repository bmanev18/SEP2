package client.model;

import client.networking.Client;
import server.model.User;
import shared.networking.ClientCallback;
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
    private ChatHistory chatHistory;
    private String username;

    public ModelManager(Client client) {
        this.client = client;
        client.startClient();
        support = new PropertyChangeSupport(this);
        client.addListener("NewMessage", this::receive);
        chatHistory = new ChatHistory();
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
        //chatHistory.receive(event);
        support.firePropertyChange("MessageReceived", null, msg);
        System.out.println("Model Manager::receive " + msg);
    }

    @Override
    public void changeUsername(String username) {
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

    //B

    @Override
    public void changeCurrentChat(Chat chat) {
        //chatHistory.changeCurrentChat(chat);
    }

    @Override
    public Chat startChatWith(String username) {
        try {
            return client.startChatWith(this.username,username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
            //TODO pop up
        }
    }

    @Override
    public User startChatWith() {
        return client.loadUser(username);
    }

    @Override
    public void addUser(User user, Chat chat) {
        client.addUser(user.getUsername(), chat.getId());
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

}