package client.core;

import client.views.chatSystem.MainController;
import client.views.login.LogInController;
import client.views.signUpView.SignUpViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewHandler {

    private Stage stage;
    private Scene chatScene;
    private Scene logInScene;

    private Scene signUpScene;
    private ViewModelFactory vmf;

    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
        stage = new Stage();
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

            signUpScene = new Scene(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(signUpScene);
        stage.setTitle("SignUp");
    }
}
