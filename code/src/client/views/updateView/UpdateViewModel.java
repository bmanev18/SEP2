package client.views.updateView;

import client.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UpdateViewModel {
    private Model model;
    private SimpleStringProperty firstName = new SimpleStringProperty();
    private SimpleStringProperty lastName = new SimpleStringProperty();


    public UpdateViewModel(Model model) {
        this.model = model;
    }

    public void updatePassword(String username, String password) {
        model.updatePassword(username, password);
    }

    public void updateFirstName(String username, String firstName) {
        model.updateFirstName(username, firstName);
    }

    public void updateLastName(String username, String lastName) {
        model.updateLastName(username, lastName);
    }


    public String getPassword(String username) {
//        return model.getPassword(username);
        return null;
    }

    public StringProperty lastName() {
        return lastName;
    }

    public StringProperty firstName() {
        return firstName;
    }

    public void updateInfo(String firstName, String lastName, String password) {
        model.updateUser(firstName, lastName, password, model.getUser().getUsername());
    }

    public String getUsername() {
        return model.getUsername();
    }
}
