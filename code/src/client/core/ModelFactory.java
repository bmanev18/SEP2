package client.core;

import client.model.Model;
import client.model.ModelManager;

public class ModelFactory {
    private ClientFactory clientFactory;
    private Model model;


    public ModelFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        model = getModel();
    }


    public Model getModel() {
        if (model == null) {
            model = new ModelManager(clientFactory.getClient());
        }
        return model;
    }
}
