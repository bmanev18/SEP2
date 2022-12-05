package client.core;

import client.views.chatSystem.ChatController;
import client.views.login.LogInController;
import client.views.signUpView.SignUpViewController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;

public class ViewHandler {

    private Stage stage;

    private Stage alertBoxStage;
    private Scene chatScene;
    private Scene logInScene;
    private Scene signUpScene;
    private Scene alertBoxScene;
    private ViewModelFactory vmf;
    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
        stage = new Stage();
        alertBoxStage = new Stage();
        alertBoxStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void start() {
        openLogInView();
        stage.show();
    }

    public void openChatView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/chatSystem/Chat.fxml"));
            Parent root = loader.load();

            ChatController chatController = loader.getController();
            chatController.innit(vmf.getChatViewModel());
            stage.setOnCloseRequest(e -> {
                    vmf.getChatViewModel().disconnect();
                Platform.exit();
            });

            chatScene = new Scene(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(chatScene);
        stage.setTitle("Chat");

    }

    public void openLogInView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/login/LogIn.fxml"));
            Parent root = loader.load();

            LogInController loginController = loader.getController();
            loginController.innit(this, vmf.getLogInViewModel());

            logInScene = new Scene(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(logInScene);
        stage.setTitle("Log in");
    }
    public void openSignUpView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/signUpView/signUpView.fxml"));
            Parent root = loader.load();

            SignUpViewController signUpViewController = loader.getController();
            signUpViewController.innit(this, vmf.getSignUpViewModel());

            signUpScene= new Scene(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(signUpScene);
        stage.setTitle("SignUp");
}
  public void openAnAlertBox(String messageToUser,String title){
        alertBoxStage.setTitle(title);
        alertBoxStage.setMinWidth(250);
        alertBoxStage.setMinHeight(120);

        Label label1 = new Label();
        label1.setText(messageToUser);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(actionEvent -> alertBoxStage.close());

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label1,closeButton);
        vBox.setAlignment(Pos.CENTER);

        alertBoxScene = new Scene(vBox);
        alertBoxStage.setScene(alertBoxScene);
        alertBoxStage.showAndWait();

  }
}
