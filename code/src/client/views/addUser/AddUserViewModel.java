package client.views.addUser;

import client.model.Chat;
import client.model.Model;

public class AddUserViewModel {
    private Model model;

    public AddUserViewModel(Model model) {
        this.model = model;
    }

    public void addUser(String text, Chat currentChat) {
        model.addUser(text, currentChat);
    }
}
