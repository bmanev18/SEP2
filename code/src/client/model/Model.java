package client.model;

import shared.Subject;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.List;

public interface Model extends Subject {

    void send(String Text);

    void receive(PropertyChangeEvent event);

    void changeUsername(String username);

    void requestStats();

    void signUp(String firstName, String lastName, String username, String password);

    List<String> getUsernames();

    void disconnect();

    String getPassword(String username);

    void updatePassword(String username,String password);
    void updateFirstName(String username,String firstName);
    void updateLastName(String username,String lastName);
}
