package client.views.login;

import client.model.Model;

import java.util.List;

public class LogInViewModel {
    private Model model;

    public LogInViewModel(Model model) {
        this.model = model;
    }

    public void changeUsername(String username) {
        model.changeUsername(username);
    }

    public boolean getPassword(String username, String password) {
        return model.getPassword(username, password);
    }

    public List<String> getUsernames() {
        return model.getUsernames();
    }

}
