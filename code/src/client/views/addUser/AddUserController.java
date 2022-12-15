package client.views.addUser;

import client.core.ViewHandler;
import client.model.Chat;
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
    private Chat currentChat;
    private ViewHandler viewHandler;


    public void innit(ViewHandler viewHandler, AddUserViewModel viewModel, Chat selectedItem) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
//        usernameField = new TextField();
        addButton = new Button();
        currentChat = selectedItem;
    }

    public void addUser(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onButtonPressed();
        }
    }

    public void onButtonPressed() {
        viewModel.addUser(usernameField.getText(), currentChat);
        viewHandler.openChatView();
    }
}
