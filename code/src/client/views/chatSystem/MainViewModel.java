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
import java.rmi.RemoteException;
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
    private String username;
    // The username of the sender(local username).


    public MainViewModel(Model model) {
        this.model = model;
        messageSend = new SimpleStringProperty();
        searchRequest = new SimpleStringProperty();
        currentConversation = FXCollections.observableArrayList();
        currentlyOpenedChat = new Chat(-1, "<<initial>>");
        chatCatalog = FXCollections.observableArrayList();


        chatMap = new HashMap<>();
        username = null;
        model.addListener("MessageReceived", this::onMessageReceived);

        loadData();
    }

    private void loadData() {
        chatMap.put(1, new ArrayList<>());
        chatMap.put(2, new ArrayList<>());
        Chat chat1 = new Chat(1, "new");
        Chat chat2 = new Chat(2, "new2");
        chatCatalog.addAll(chat1, chat2);
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
             * it will be added to the opened chat  */

        });

    }


    public void sendMessage(String text) {
        if (text != null && !text.equals("")) {
            model.send(new Message(username, currentlyOpenedChat.getId(), text));
        }
    }

    public void switchChat(Chat chat) {
        currentConversation.clear();
        currentConversation.addAll(chatMap.get(chat.getId()));
        // if not working try openedChat.clear and then addAll from the list in the map
        currentlyOpenedChat = chat;
    }

    public User search(String username) {
        try {
            return model.search(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
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

    public void setUsername(String username) {
        this.username = username;
    }


    public boolean sameChat(Chat selectedItem) {
        return currentlyOpenedChat.equals(selectedItem);
    }


}
