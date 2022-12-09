package client.core;

import client.views.chatSystem.MainController;
import client.views.login.LogInController;
import client.views.signUpView.SignUpViewController;
import client.views.updateView.UpdateViewController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
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
import javafx.stage.StageStyle;

import java.awt.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.rmi.RemoteException;

public class ViewHandler {

    private Stage stage;

    private Stage alertBoxStage;

    private Stage updateStage;

    private Scene updateScene;
    private Scene chatScene;
    private Scene logInScene;
    private Scene signUpScene;
    private Scene alertBoxScene;
    private ViewModelFactory vmf;

    private double deltaY;
    private double deltaX;

    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
        stage = new Stage();
        alertBoxStage = new Stage();
        updateStage = new Stage();
        alertBoxStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void start() {
        openLogInView();
        stage.show();
    }

    public void openChatView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/chatSystem/main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();
            mainController.innit(vmf.getMainViewModel());
            stage.setOnCloseRequest(e -> {
                vmf.getMainViewModel().disconnect();
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
            logInScene.getStylesheets().add(getClass().getResource("../style/styleLogInView.css").toExternalForm());

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

            signUpScene = new Scene(root);
            signUpScene.getStylesheets().add(getClass().getResource("../style/styleSignUpView.css").toExternalForm());


        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(signUpScene);
        stage.setTitle("SignUp");
    }

    public void openAnAlertBox(String messageToUser, String title) {
        alertBoxStage.setTitle(title);
        alertBoxStage.setMinWidth(250);
        alertBoxStage.setMinHeight(120);

        Label label1 = new Label();
        label1.setText(messageToUser);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(actionEvent -> alertBoxStage.close());

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label1, closeButton);
        vBox.setAlignment(Pos.CENTER);

        alertBoxScene = new Scene(vBox);
        alertBoxStage.setScene(alertBoxScene);
        alertBoxStage.showAndWait();
    }

    public void openUpdateStage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/updateView/updateView.fxml"));
            Parent root = loader.load();

            UpdateViewController updateViewController = loader.getController();
            updateViewController.innit(this, vmf.getUpdateViewModel());

            updateScene = new Scene(root);
            updateScene.getStylesheets().add(getClass().getResource("../style/styleUpdateView.css").toExternalForm());

        } catch (IOException e) {
            e.printStackTrace();
        }
        updateStage.setScene(updateScene);
        updateStage.setTitle("Update Credentials");
        updateStage.show();

    }

}
