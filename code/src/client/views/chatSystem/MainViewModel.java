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

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel {

    private Model model;
    private StringProperty messageSend;
    private StringProperty searchRequest;

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

    private StringProperty chatName;

    public MainViewModel(Model model) {
        this.model = model;
        messageSend = new SimpleStringProperty();
        searchRequest = new SimpleStringProperty();
        username = new SimpleStringProperty();
        chatName = new SimpleStringProperty();
        currentConversation = FXCollections.observableArrayList();
        currentlyOpenedChat = new Chat(-1, "Select Chat");
        chatCatalog = FXCollections.observableArrayList();


        chatMap = new HashMap<>();
        user = null;
        username.setValue("");
        chatName.setValue(currentlyOpenedChat.getName());
        model.addListener("MessageReceived", this::onMessageReceived);

        load();
        System.out.println(1);
    }

    private void loadData() {
        chatCatalog.addAll(model.loadChats());
        chatMap = model.loadMessages();
        setUser(model.startChatWith());
        // set User
    }

    private void load() {
        chatMap.put(1, new ArrayList<>());
        chatMap.put(2, new ArrayList<>());
        Chat chat1 = new Chat(1, "new");
        Chat chat2 = new Chat(2, "new2");
        chatCatalog.addAll(chat1, chat2);
        setUser(new User("user"));
    }

    private void onMessageReceived(PropertyChangeEvent evt) {
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
        chatName.setValue(currentlyOpenedChat.getName());
    }

    public void search(String username) {
        Chat chat = model.startChatWith(username);
        if (chatCatalog.contains(chat)) {
            switchChat(chat);
        } else {
            chatCatalog.add(chat);
            chatMap.put(chat.getId(), new ArrayList<>());
        }
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
        currentlyOpenedChat = new Chat(-1, "Select Chat");
        chatName().setValue(currentlyOpenedChat.getName());
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

    public StringProperty searchRequest() {
        return searchRequest;
    }

    public StringProperty username() {
        return username;
    }

    public StringProperty chatName() {
        return chatName;
    }

    public void disconnect(){
        model.disconnect();
    }
}