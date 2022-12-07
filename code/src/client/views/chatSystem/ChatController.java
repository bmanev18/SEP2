package client.views.chatSystem;

import client.core.ViewHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import shared.util.Message;

public class ChatController {
    /*@FXML
    private ListView<Message> receivedField;
    @FXML
    private TextField sendTextField;

    private ChatViewModel chatViewModel;

    public void innit(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
        sendTextField.textProperty().bindBidirectional(chatViewModel.messageSend());
        receivedField.setItems(chatViewModel.messageReceived());
    }

    public void onSendButton() {
        chatViewModel.sendMessage(sendTextField.getText());
        sendTextField.clear();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onSendButton();
        }
    }*/
}
