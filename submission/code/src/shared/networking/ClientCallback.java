package shared.networking;

import client.model.Chat;
import shared.util.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {

    void receive(Message message) throws RemoteException;

    void onNewChat(Chat chat) throws RemoteException;

    void changeColour(Chat oldChat, Chat newChat) throws RemoteException;
}