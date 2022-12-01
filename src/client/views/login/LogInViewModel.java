package client.views.login;

import client.model.Model;

public class LogInViewModel {
    private Model model;

    public LogInViewModel(Model model) {
        this.model = model;
    }

    public void changeUsername(String username) {
        model.changeUsername(username);
    }


}
