package client.views.signUpView;

import client.model.Model;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class SignUpViewModel {
    private Model model;
    public SignUpViewModel(Model model) {
        this.model =model;
    }

   public List<String> getUsernames(){
        return model.getUsernames();
   }

    public void signUp(String firstName, String lastName, String username, String password) {
        model.signUp(firstName,lastName,username,password);
    }
}
