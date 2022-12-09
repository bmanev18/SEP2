package client.views.chatSystem;

import client.model.Chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import server.model.User;
import shared.util.Message;

public class MainController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label chatName;
    @FXML
    private ListView<Message> messagesList;
    @FXML
    private ListView<Chat> chats;
    @FXML
    private TextField sendTextField;
    @FXML
    private TextField searchTextField;

    @FXML
    private Button settingsButton;
    @FXML
    private ImageView sendButton;
    private MainViewModel mainViewModel;

    public void innit(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        sendTextField.textProperty().bindBidirectional(mainViewModel.messageSend());
        searchTextField.textProperty().bindBidirectional(mainViewModel.searchRequest());
        chats.setItems(mainViewModel.chats());
        messagesList.setItems(mainViewModel.messageReceived());
        usernameLabel.textProperty().bind(mainViewModel.username());
        chatName.textProperty().bind(mainViewModel.chatName());
        setVisibility(false);


    }

    public void onSendButton() {
        mainViewModel.sendMessage(sendTextField.getText());
        sendTextField.clear();
    }

    public void onSearchButton() {
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
            setVisibility(true);
        }

        Chat selectedItem = chats.getSelectionModel().getSelectedItem();
        if (!mainViewModel.isInSameChat(selectedItem)) {
            mainViewModel.switchChat(selectedItem);
            //chatName.setText(selectedItem.getName());
            System.out.println("id " + selectedItem.getId());
        }
    }

    public void addUser(User user) {
        mainViewModel.addUser(user);
    }

    public void leaveChat() {
        mainViewModel.leaveChat();
        setVisibility(false);
    }

    public void downloadChat() {
        mainViewModel.downloadChat();
    }

    public void addUser(ActionEvent actionEvent) {
        //TODO
    }

    public void customize(ActionEvent actionEvent) {
        //TODO
    }

    private void setVisibility(boolean visible) {
        messagesList.setVisible(visible);
        sendTextField.setVisible(visible);
        sendButton.setVisible(visible);
        settingsButton.setVisible(visible);
    }
}