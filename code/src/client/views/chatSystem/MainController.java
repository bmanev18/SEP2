package client.views.chatSystem;

import client.model.Chat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import shared.util.Message;

public class MainController {
    public Label chatName;
    @FXML
    private ListView<Message> messagesList;
    @FXML
    private ListView<Chat> chats;
    @FXML
    private TextField sendTextField;
    @FXML
    private TextField searchTextField;

    private MainViewModel mainViewModel;

    public void innit(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        sendTextField.textProperty().bindBidirectional(mainViewModel.messageSend());
        searchTextField.textProperty().bindBidirectional(mainViewModel.searchRequest());
        chats.setItems(mainViewModel.chats());
        messagesList.setItems(mainViewModel.messageReceived());
        messagesList.setVisible(false);
        sendTextField.setVisible(false);


    }

    public void onSendButton() {
        mainViewModel.sendMessage(sendTextField.getText());
        sendTextField.clear();
    }

    private void onSearchButton() {
        mainViewModel.search(searchTextField.getText());
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (keyEvent.getSource() == sendTextField) {
                onSendButton();
            } else if (keyEvent.getSource() == searchTextField) {
                onSearchButton();
            }
        }
    }

    public void switchChat(MouseEvent mouseEvent) {
        if (!messagesList.isVisible()) {
            messagesList.setVisible(true);
            sendTextField.setVisible(true);
        }

        Chat selectedItem = chats.getSelectionModel().getSelectedItem();
        if (!mainViewModel.sameChat(selectedItem)) {
            mainViewModel.switchChat(selectedItem);
            chatName.setText(selectedItem.getName());
            System.out.println("id " + selectedItem.getId());
        }
    }
}
