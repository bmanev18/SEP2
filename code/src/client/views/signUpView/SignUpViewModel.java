package client.views.signUpView;

import client.model.Model;
import server.model.User;

import java.util.List;

public class SignUpViewModel {
    private Model model;
    public SignUpViewModel(Model model) {
        this.model = model;
    }

   public List<String> getUsernames(){
        return model.getUsernames();
   }

    public void signUp(String firstName, String lastName, String username, String password) {
        model.signUp(firstName,lastName,username,password);
    }
    public User getUser(String username){
        return model.getUser();
    }
}
