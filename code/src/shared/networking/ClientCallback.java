package shared.networking;

import shared.util.Message;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {

    void send(Message message) throws RemoteException;

    void receive(Message message) throws RemoteException;

    String getUsername() throws RemoteException;

}
