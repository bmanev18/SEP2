package client.views.updateView;

import client.model.Model;

public class UpdateViewModel {
    private Model model;


    public UpdateViewModel(Model model){
        this.model = model;
    }
    public void updatePassword(String username,String password){
        model.updatePassword(username,password);
    }
    public void updateFirstName(String username,String firstName){
        model.updateFirstName(username,firstName);
    }
    public void updateLastName(String username,String lastName){
        model.updateLastName(username,lastName);
    }


    public String getPassword(String username) {
        return model.getPassword(username);
    }
}
