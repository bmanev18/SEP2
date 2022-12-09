package client.views.signUpView;

import client.core.ViewHandler;
import client.views.login.LogInViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;

public class SignUpViewController {
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    private ViewHandler viewHandler;
    private SignUpViewModel viewModel;

    public void innit(ViewHandler viewHandler, SignUpViewModel signUpViewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = signUpViewModel;
    }

    public void OnSignUp() {
        if(firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            viewHandler.openAnAlertBox("All fields are mandatory", "Empty Fields");

        } else if(passwordField.getText().length()>8){
            viewHandler.openAnAlertBox("Password must be no longer than 8 symbols", "Invalid Password");
            passwordField.clear();

        } else if (!passwordField.getText().equals(confirmPasswordField.getText())){
            viewHandler.openAnAlertBox("Passwords do not match", "sign up failed");
            passwordField.clear();
            confirmPasswordField.clear();

        } else if(usernameField.getText().length()>15){
            viewHandler.openAnAlertBox("Username must be no longer than 15 symbols", "Invalid Username");
            passwordField.clear();

        }/* else if(viewModel.getUsernames().contains(usernameField.getText())){
            viewHandler.openAnAlertBox("Username "+ usernameField.getText() + " is already taken.", "Invalid Username");
            usernameField.clear();

        } */else {
            viewModel.signUp(firstNameField.getText(),lastNameField.getText(),usernameField.getText(),passwordField.getText());
            viewHandler.openAnAlertBox("Signed up successfully","Signed Up");
            viewHandler.openLogInView();
        }

    }

    public void onBackButton() {
        viewHandler.openLogInView();
    }
}
