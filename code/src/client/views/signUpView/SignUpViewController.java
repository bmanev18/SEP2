package client.views.signUpView;

import client.core.ViewHandler;
import client.views.login.LogInViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;

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

    public void OnSignUp() throws IOException {
        boolean notMatchingPassword = !passwordField.getText().equals(confirmPasswordField.getText());
        boolean passwordTooLong = passwordField.getText().length() > 8;
        boolean usernameAlreadyTaken = viewModel.getUser(usernameField.getText()).getUsername().equals(usernameField.getText().toLowerCase());
        boolean usernameTooLong = usernameField.getText().length() > 15;
        boolean hasEmptyField = firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty();

        if (hasEmptyField) {
            viewHandler.openAnAlertBox("All fields are mandatory", "Empty Fields");

        } else if (notMatchingPassword) {
            viewHandler.openAnAlertBox("Passwords do not match", "sign up failed");
            passwordField.clear();
            confirmPasswordField.clear();

        } else if (usernameTooLong) {
            viewHandler.openAnAlertBox("Username must be no longer than 15 symbols", "Invalid Username");
            passwordField.clear();

        } else if (usernameAlreadyTaken) {
            viewHandler.openAnAlertBox("Username " + usernameField.getText() + " is already taken.", "Invalid Username");
            usernameField.clear();

        } else if (passwordTooLong) {
            viewHandler.openAnAlertBox("Password must be no longer than 8 symbols", "Invalid Password");
            passwordField.clear();

        } else {
            viewModel.signUp(firstNameField.getText(), lastNameField.getText(), usernameField.getText(), passwordField.getText());
            viewHandler.openAnAlertBox("Signed up successfully", "Signed Up");
            viewHandler.openLogInView();
        }

    }
}

