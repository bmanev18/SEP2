package server.model;

import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BroadcastImpl implements Broadcast {
    private PropertyChangeSupport support;

    public BroadcastImpl() {
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void broadcast(Message message) {
        support.firePropertyChange("NewMessage", null, message);

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
