package server.model;

import client.model.Chat;
import shared.Subject;
import shared.networking.ClientCallback;
import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface Broadcast extends Subject {
    void broadcastMessage(Message message, List<PropertyChangeListener> listeners);

    void sendNewChat(ClientCallback invited, Chat chat);

    void changeColour(Chat oldChat, Chat newChat, List<ClientCallback> clients);
}
