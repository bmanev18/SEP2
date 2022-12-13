package client.views.addUser;

import client.views.signUpView.SignUpViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AddUserController {
    @FXML
    private TextField usernameField;
    @FXML
    private Button addButton;
    private AddUserViewModel viewModel;

    public void innit(SignUpViewModel signUpViewModel) {

    }

    public void addUser(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onButtonPressed();
        }
    }

    public void onButtonPressed() {
        //viewModel.addUser(usernameField, id);
    }
}
