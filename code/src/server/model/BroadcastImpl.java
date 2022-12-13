package server.model;

import client.model.Chat;
import shared.networking.ClientCallback;
import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class BroadcastImpl implements Broadcast {
    private PropertyChangeSupport support;

    public BroadcastImpl() {
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void broadcastMessage(Message message, List<PropertyChangeListener> listeners) {
        for (PropertyChangeListener listener : listeners) {
            support.addPropertyChangeListener("NewMessage", listener);
        }
        support.firePropertyChange("NewMessage", null, message);
        System.out.println("Message sent");
        for (PropertyChangeListener listener : listeners) {
            support.removePropertyChangeListener("NewMessage", listener);
        }
    }

    @Override
    public void sendNewChat(ClientCallback creator, ClientCallback invited, Chat chat) {
        List<ClientCallback> list = new ArrayList<>();
        list.add(creator);
        if (invited != null) {
            list.add(invited);
        }
        PropertyChangeListener listener;
        List<PropertyChangeListener> listeners = new ArrayList<>();
        for (ClientCallback client : list) {
            listener = evt -> {
                try {
                    client.onNewChat((Chat) evt.getNewValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            };
            support.addPropertyChangeListener("NewChat", listener);
            listeners.add(listener);
        }
        support.firePropertyChange("NewChat", null, chat);
        System.out.println("Broadcast");

        for (PropertyChangeListener propertyChangeListener : listeners) {
            support.removePropertyChangeListener(propertyChangeListener);
        }
    }

    @Override
    public void changeColour(Chat oldChat, Chat newChat, List<ClientCallback> clients) {
        //create, add, fire, remove listeners
        List<PropertyChangeListener> list = new ArrayList<>();
        PropertyChangeListener pcl;
        for (ClientCallback client : clients) {
            pcl = evt -> {
                try {
                    client.changeColour((Chat) evt.getNewValue(), (Chat) evt.getNewValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            };
        }

        for (PropertyChangeListener listener : list) {
            support.firePropertyChange("ColourChanged", oldChat, newChat);
        }

        for (PropertyChangeListener listener : list) {
            support.removePropertyChangeListener(listener);
        }
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
