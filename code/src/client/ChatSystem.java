package client;

import client.core.ClientFactory;
import client.core.ModelFactory;
import client.core.ViewHandler;
import client.core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ChatSystem extends Application {
    @Override
    public void start(Stage stage) {
        ClientFactory cf = new ClientFactory();
        ModelFactory mf = new ModelFactory(cf);
        ViewModelFactory vmf = new ViewModelFactory(mf);
        ViewHandler viewHandler = new ViewHandler(vmf);
        viewHandler.start();
    }

}
