package client.views.signUpView;

import client.core.ViewHandler;
import client.views.login.LogInViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class SignUpViewController {
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField usernameField;
    public TextField passwordField;
    private ViewHandler viewHandler;
    private SignUpViewModel viewModel;

    public void innit(ViewHandler viewHandler, SignUpViewModel signUpViewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = signUpViewModel;
    }

    public void OnSignUp() {
        //add the account to the database if possible, send the user back to the loginView
        viewHandler.openLogInView();
    }
}
