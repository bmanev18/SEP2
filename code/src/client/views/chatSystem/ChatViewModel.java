package client.views.chatSystem;

import client.model.Model;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class ChatViewModel {
    private Model model;
    private StringProperty messageSend;
    private ObservableList<Message> messageReceived;


    public ChatViewModel(Model model) {
        this.model = model;
        messageSend = new SimpleStringProperty();
        messageReceived = FXCollections.observableArrayList();
        model.addListener("MessageReceived", this::onMessageReceived);
        sendMessage("joined");
    }

    private void onMessageReceived(PropertyChangeEvent evt) {
        Platform.runLater(() -> messageReceived.add((Message) evt.getNewValue()));
    }


    public void sendMessage(String temp) {
        if (temp != null) {
            model.send(temp);
        }
    }

    public ObservableList<Message> messageReceived() {
        return messageReceived;
    }

    public StringProperty messageSend() {
        return messageSend;
    }

    public void requestStats() {
        model.requestStats();
    }

    public void disconnect(){
        model.disconnect();
    }
}
