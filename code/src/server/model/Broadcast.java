package server.model;

import shared.Subject;
import shared.util.Message;

import java.rmi.RemoteException;

public interface Broadcast extends Subject {
    void broadcast(Message message);
}
