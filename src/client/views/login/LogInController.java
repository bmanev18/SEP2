package client.views.login;

import client.core.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LogInController {
    public TextField passwordField;
    @FXML
    private TextField usernameField;

    private ViewHandler viewHandler;
    private LogInViewModel viewModel;

    public void innit(ViewHandler viewHandler, LogInViewModel logInViewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = logInViewModel;
    }

    public void onEnterButton() {
        viewModel.changeUsername(usernameField.getText());
        viewHandler.openChatView();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterButton();
        }
    }

    public void onSignUp(ActionEvent actionEvent) {
        viewHandler.openSignUpView();
    }
}
