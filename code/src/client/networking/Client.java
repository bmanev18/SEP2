package client.networking;

import shared.util.Message;
import shared.Subject;

import java.rmi.RemoteException;
import java.util.List;

public interface Client extends Subject {
    void toCallback(Message message);

    void toModel(Message message);

    void startClient();

    void changeUsername(String username);

    String requestStats() throws RemoteException;

    void signUp(String firstName,String lastName,String username,String password) throws RemoteException;

    List<String> getUsernames();

}
