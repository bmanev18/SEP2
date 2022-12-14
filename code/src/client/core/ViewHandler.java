package client.core;

import client.model.Chat;
import client.views.addUser.AddUserController;
import client.views.chatSystem.MainViewController;
import client.views.login.LogInController;
import client.views.signUpView.SignUpViewController;
import client.views.updateView.UpdateViewController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ViewHandler {

    private Stage stage;

    private Stage alertBoxStage;

    private Stage chatOptionsStage;

    private Stage updateStage;

    private Scene updateScene;
    private Scene chatScene;
    private Scene logInScene;
    private Scene signUpScene;
    private Scene alertBoxScene;
    private Scene addUser;
    private ViewModelFactory vmf;

    private double deltaY;
    private double deltaX;

    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
        stage = new Stage();
        alertBoxStage = new Stage();
        updateStage = new Stage();
        chatOptionsStage = new Stage();
        alertBoxStage.initModality(Modality.APPLICATION_MODAL);
        chatOptionsStage.initModality(Modality.APPLICATION_MODAL);
    }

    public void start() throws IOException {
        openLogInView();
        stage.show();
    }

    public void openChatView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/chatSystem/mainView.fxml"));
            Parent root = loader.load();

            MainViewController mainViewController = loader.getController();
            mainViewController.innit(this, vmf.getMainViewModel());
            stage.setOnCloseRequest(e -> {
                vmf.getMainViewModel().disconnect();
                Platform.exit();
            });

            chatScene = new Scene(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(chatScene);
        stage.setTitle("Chat");

    }

    public void openLogInView() throws IOException {

        FXMLLoader loader = loadFXML("login/LogIn.fxml");

        LogInController loginController = loader.getController();
        loginController.innit(this, vmf.getLogInViewModel());

        logInScene = new Scene(loader.load());
        logInScene.getStylesheets().add(getClass().getResource("../style/styleLogInView.css").toExternalForm());

        stage.setScene(logInScene);
        stage.setTitle("Log in");
    }

    public void openSignUpView() throws IOException {
        FXMLLoader fxmlLoader = loadFXML("signUpView/signUpView.fxml");
        SignUpViewController signUpViewController = fxmlLoader.getController();
        signUpViewController.innit(this, vmf.getSignUpViewModel());

        signUpScene = new Scene(fxmlLoader.load());
        signUpScene.getStylesheets().add(getClass().getResource("../style/styleSignUpView.css").toExternalForm());
        stage.setScene(signUpScene);
        stage.setTitle("SignUp");

    }

    public void openAddUserView(Chat selectedItem) throws IOException {
        FXMLLoader loader = loadFXML("addUser/addUserView.fxml");
        AddUserController addUserController = loader.getController();
        addUserController.innit(this, vmf.getAddUserViewModel(), selectedItem);

        addUser = new Scene(loader.load());

        stage.setScene(addUser);
        stage.setTitle("Add User");
    }

    public void openUpdateStage() throws IOException {
        FXMLLoader loader = loadFXML("updateView/updateView.fxml");

        UpdateViewController updateViewController = loader.getController();
        updateViewController.innit(this, vmf.getUpdateViewModel());

        updateScene = new Scene(loader.load());
        updateScene.getStylesheets().add(getClass().getResource("../style/styleUpdateView.css").toExternalForm());

        updateStage.setScene(updateScene);
        updateStage.setTitle("Update Credentials");
        updateStage.show();

    }

    private FXMLLoader loadFXML(String path) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/" + path));
        Parent root = loader.load();
        return loader;
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

}
