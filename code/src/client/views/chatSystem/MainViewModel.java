package client.views.chatSystem;

import client.model.Chat;
import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.User;
import shared.util.Message;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel {

    private Model model;
    private StringProperty messageSend;
/*    private StringProperty searchRequest;
    private StringProperty writeChatName;*/

    private ObservableList<Chat> chatCatalog;
    // A catalog of chats visible on the left side of the GUI.
    private Map<Integer, List<Message>> chatMap;
    // All chats from the catalog with their respective messages.
    private ObservableList<Message> currentConversation;
    // after selecting a chat, all the messages shown on the right side of the GUI.
    private Chat currentlyOpenedChat;
    // the chat selected from the Catalog.
    private User user;
    // Sender
    private StringProperty username;

    // The username of the sender(used for optimizing sendMessage()).
    private StringProperty chatTitle;

    public MainViewModel(Model model) {
        this.model = model;
        messageSend = new SimpleStringProperty();
        username = new SimpleStringProperty();
        chatTitle = new SimpleStringProperty();
        /*searchRequest = new SimpleStringProperty();
        writeChatName = new SimpleStringProperty();*/
        currentConversation = FXCollections.observableArrayList();
        currentlyOpenedChat = new Chat(-1, "Select Chat");
        chatCatalog = FXCollections.observableArrayList();


        chatMap = new HashMap<>();
        user = null;
        username.setValue("");
        chatTitle.setValue(currentlyOpenedChat.getName());
        model.addListener("MessageReceived", this::onMessageReceived);
        model.addListener("AddedToChat", this::onNewChat);
        model.addListener("ColourChanged", this::onColourChange);

        loadData();
    }

    public void loadData() {
        chatCatalog.addAll(model.loadChats());
        chatMap = model.loadMessages();
        setUser(model.getUser());
    }

    private void onColourChange(PropertyChangeEvent event) {
        Chat newChat = (Chat) event.getNewValue();
        Chat oldChat = (Chat) event.getOldValue();

        chatCatalog.set(chatCatalog.indexOf(oldChat), newChat);
        if (currentlyOpenedChat.equals(oldChat)) {
            currentlyOpenedChat = newChat;
        }
    }

    public void onNewChat(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            Chat chat = (Chat) event.getNewValue();
            chatCatalog.add(chat);
            chatMap.put(chat.getId(), new ArrayList<>());
        });

    }

    /*public void load() {
        chatMap.put(1, new ArrayList<>());
        chatMap.put(2, new ArrayList<>());
        Chat chat1 = new Chat(1, "new");
        Chat chat2 = new Chat(2, "new2");
        chatCatalog.addAll(chat1, chat2);
        setUser(new User("user"));
    }*/

    public void onMessageReceived(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            Message msg = (Message) evt.getNewValue();
            int toChat = msg.getToChat();
            chatMap.putIfAbsent(toChat, new ArrayList<>() {
            });
            chatMap.get(toChat).add(msg);
            if (toChat == currentlyOpenedChat.getId()) {
                currentConversation.add(msg);
            }
            /*New messages will always be added to chatMap
             * and in case the received message is for the opened chat,
             * it will be added to the opened chat as well */

        });

    }


    public void sendMessage(String text) {
        if (text != null && !text.equals("")) {
            model.send(new Message(user.getUsername(), currentlyOpenedChat.getId(), text));
        }
    }

    public void switchChat(Chat chat) {
        currentConversation.clear();
        currentConversation.addAll(chatMap.get(chat.getId()));
        currentlyOpenedChat = chat;
        chatTitle.setValue(currentlyOpenedChat.getName());
    }

    public void startChatWith(String username, String chatName) {
        System.out.println("MainViewModel");
        model.startChatWith(username, chatName);
    }

    public void setUser(User user) {
        this.user = user;
        username.setValue(user.getUsername());
    }


    public boolean isInSameChat(Chat selectedItem) {
        return currentlyOpenedChat.equals(selectedItem);
    }


    public void addUser(User user) {
        model.addUser(user, currentlyOpenedChat);
    }

    public void leaveChat() {
        chatCatalog.remove(currentlyOpenedChat);
        int id = currentlyOpenedChat.getId();
        chatMap.remove(id);
        model.leaveChat(user.getUsername(), id);

        chatTitle.setValue(currentlyOpenedChat.getName());
        currentlyOpenedChat = new Chat(-1, "Select Chat");
    }

    public void downloadChat() {
        //TODO save into file
    }

    public Property<String> messageSend() {
        return messageSend;
    }

    public ObservableList<Message> messageReceived() {
        return currentConversation;
    }

    public ObservableList<Chat> chats() {
        return chatCatalog;
    }

    public void onColourChange(String colour) {
        model.changeColour(currentlyOpenedChat, colour);
    }

    public StringProperty username() {
        return username;
    }

    public StringProperty chatTitle() {
        return chatTitle;
    }

/*    public StringProperty searchRequest() {
        return searchRequest;
    }

    public StringProperty chatName() {
        return writeChatName;
    }*/

    public void disconnect() {
        model.disconnect();
        System.out.println("Disconnected");
    }
}