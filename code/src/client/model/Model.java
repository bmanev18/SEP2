package client.model;

import shared.Subject;

import java.beans.PropertyChangeEvent;

public interface Model extends Subject {

    void send(String Text);

    void receive(PropertyChangeEvent event);

    void changeUsername(String username);

    void requestStats();
}
