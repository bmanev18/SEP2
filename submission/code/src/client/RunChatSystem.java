package client;

import javafx.application.Application;
import server.StartServer;

import java.rmi.RemoteException;

public class RunChatSystem {
    public static void main(String[] args) {
        Application.launch(ChatSystem.class);
    }
}
