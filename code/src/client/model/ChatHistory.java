package client.model;

import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHistory {
    /*private Map<Integer, List<Message>> map;
    private List<Chat> chats;
    private Chat currentChat;
    private String username;

    public ChatHistory() {
        map = new HashMap<>();
        chats = new ArrayList<>();
        currentChat = null;
        username = "empty";
        ;
    }

    @Override
    public void send(Message text) {
        return new Message(username, currentChat.getId(), text);
    }

    @Override
    public void receive(PropertyChangeEvent event) {
        Message msg = (Message) event.getNewValue();
        int toChat = msg.getToChat();
        map.putIfAbsent(toChat, new ArrayList<>());
        map.get(toChat).add(msg);
    }

    @Override
    public void changeUsername(String username) {
        this.username = username;
    }

    @Override
    public void changeCurrentChat(Chat chat) {
        this.currentChat = chat;
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
    }*/
}