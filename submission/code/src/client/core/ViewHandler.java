package client.core;

import client.model.Chat;
import client.views.addUser.AddUserController;
import client.views.chatSystem.MainViewController;
import client.views.login.LogInController;
import client.views.signUpView.SignUpViewController;
import client.views.updateView.UpdateViewController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

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

    private Double yOffset;
    private Double xOffset;

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
        alertBoxStage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void start() {
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

            chatScene = new Scene(root);
            makeDraggable(root);
            chatScene.getStylesheets().add(getClass().getResource("../style/chatStyle.css").toExternalForm());

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
            makeDraggable(root);

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
            makeDraggable(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(signUpScene);
        stage.setTitle("SignUp");
    }

    public void openAddUserView(Chat selectedItem) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/addUser/addUserView.fxml"));
        Parent root = loader.load();

        AddUserController addUserController = loader.getController();
        addUserController.innit(this, vmf.getAddUserViewModel(), selectedItem);

        addUser = new Scene(root);

        stage.setScene(addUser);
        stage.setTitle("Add User");
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
        updateStage.showAndWait();

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
        alertBoxScene.getStylesheets().add(getClass().getResource("../style/universalStyle.css").toExternalForm());
        alertBoxStage.setScene(alertBoxScene);
        alertBoxStage.showAndWait();
    }

    private void makeDraggable(Parent root) {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public void onXforLoginAndSignUp() {
        Platform.exit();
    }

    public void leaveAndDisconnect() {
        vmf.getMainViewModel().disconnect();
        Platform.exit();
    }

    public void minimize() {
        stage.setIconified(true);
    }


    public void closeUpdate() {
        updateStage.close();
    }
}
