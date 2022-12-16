package server;


import server.networking.RMIServerImpl;
import shared.networking.RMIServer;

import java.rmi.RemoteException;

public class StartServer {
    public static void main(String[] args) throws RemoteException {

        RMIServer server = new RMIServerImpl();
        server.startServer();

        /*Server es = new Server();
        es.startServer();*/
    }
}