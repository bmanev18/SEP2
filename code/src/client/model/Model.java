package client.model;

import shared.Subject;

import java.beans.PropertyChangeEvent;
import java.util.List;

public interface Model extends Subject {

    void send(String Text);

    void receive(PropertyChangeEvent event);

    void changeUsername(String username);

    void requestStats();

    void signUp(String firstName, String lastName, String username, String password);

    List<String> getUsernames();
}
