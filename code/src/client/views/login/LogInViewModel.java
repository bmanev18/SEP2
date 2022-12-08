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

    public String getPassword(String username){
        return model.getPassword(username);
    }
public List<String> getUsernames(){
        return model.getUsernames();
}

}
