package client.networking;

import server.model.User;
import shared.util.Message;
import shared.Subject;

import java.rmi.RemoteException;

public interface Client extends Subject {
    void toCallback(Message message);

    void toModel(Message message);

    void startClient();

    void changeUsername(String username);

    String requestStats();

    User requestSearchFromCallback(String username) throws RemoteException;
}
