package client.model;

import server.model.User;
import shared.Subject;
import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public interface Model extends Subject {

    void send(Message message);

    void receive(PropertyChangeEvent event);

    void changeUsername(String username);

    void changeCurrentChat(Chat chat);

    User search(String username) throws RemoteException;
}
