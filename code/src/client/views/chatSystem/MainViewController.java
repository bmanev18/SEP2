package client.views.chatSystem;

import client.core.ViewHandler;
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

import java.io.IOException;

public class MainViewController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label chatNameLabel;
    @FXML
    private ListView<Message> messagesList;
    @FXML
    private ListView<Chat> chats;
    @FXML
    private TextField sendTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField groupNameField;

    @FXML
    private Button settingsButton;
    @FXML
    private ImageView sendButton;
    private MainViewModel mainViewModel;
    private ViewHandler handler;

    public void innit(ViewHandler viewHandler, MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        this.handler = viewHandler;
        sendTextField.textProperty().bindBidirectional(mainViewModel.messageSend());
        searchTextField = new TextField();
        chats.setItems(mainViewModel.chats());
        messagesList.setItems(mainViewModel.messageReceived());
        usernameLabel.textProperty().bind(mainViewModel.username());
        chatNameLabel.textProperty().bindBidirectional(mainViewModel.chatTitle());
        groupNameField = new TextField();
        groupNameField.setVisible(false);

        //mainViewModel.loadData();

        setVisibility(false);


    }

    public void onSendButton() {
        mainViewModel.sendMessage(sendTextField.getText());
        sendTextField.clear();
    }

    public void onSearchButton() {
        String username = searchTextField.getText();
        String chatName = groupNameField.getText();
//        Add checks
        System.out.println("MainController");
        mainViewModel.startChatWith(username, chatName);

    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (keyEvent.getSource() == sendTextField) {
                onSendButton();
            } else if (keyEvent.getSource() == groupNameField) {
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
            chatNameLabel.setText(selectedItem.getName());
            System.out.println("id " + selectedItem.getId());
        }
    }

    public void addUserToChat() throws IOException {
        handler.openAddUserView(chats.getSelectionModel().getSelectedItem());
    }

    public void leaveChat() {
        setVisibility(false);
        mainViewModel.leaveChat();
    }

    public void downloadChat() {
        mainViewModel.downloadChat();
    }

    public void startChatWith(ActionEvent actionEvent) {
        mainViewModel.startChatWith(searchTextField.getText(), groupNameField.getText());
    }

    public void customize(ActionEvent actionEvent) {
        //TODO
    }

    public void changeGroupName(ActionEvent actionEvent) {
        //TODO
    }

    public void showGroupName() {
        groupNameField.setVisible(true);
    }

    private void setVisibility(boolean visible) {
        messagesList.setVisible(visible);
        sendTextField.setVisible(visible);
        sendButton.setVisible(visible);
        settingsButton.setVisible(visible);
    }

    public void updateProfile(ActionEvent actionEvent) {
    }
}