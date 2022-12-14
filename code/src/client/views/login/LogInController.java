package client.views.login;

import client.core.ViewHandler;
import com.sun.javafx.embed.swing.newimpl.FXDnDInteropN;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LogInController {
    public PasswordField passwordField;
    @FXML
    private TextField usernameField;

    private ViewHandler viewHandler;
    private LogInViewModel viewModel;

    public void innit(ViewHandler viewHandler, LogInViewModel logInViewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = logInViewModel;
    }

    public void onEnterButton() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            viewHandler.openAnAlertBox("Empty Fields", "Log In Failed");
        } else if (viewModel.getPassword(usernameField.getText()).equals(passwordField.getText())) {
            viewModel.changeUsername(usernameField.getText());
            viewHandler.openChatView();
        } else {
            viewHandler.openAnAlertBox("Incorrect Credentials", "Log In Failed");
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterButton();
        }
    }

    public void onSignUp() throws IOException {
        viewHandler.openSignUpView();
    }

    public void openUpdate(ActionEvent actionEvent) throws IOException {
        viewHandler.openUpdateStage();
    }

}
