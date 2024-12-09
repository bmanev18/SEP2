package client.views.updateView;

import client.core.ViewHandler;
import client.views.signUpView.SignUpViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UpdateViewController {

    public Button onSaveButton;
    public TextField firstNameField;
    public TextField lastNameField;
    private ViewHandler viewHandler;
    private UpdateViewModel updateViewModel;

    public void innit(ViewHandler viewHandler, UpdateViewModel updateViewModel) {
        this.viewHandler = viewHandler;
        this.updateViewModel = updateViewModel;
        firstNameField.textProperty().bindBidirectional(updateViewModel.firstName());
        lastNameField.textProperty().bindBidirectional(updateViewModel.lastName());
        System.out.print(updateViewModel.getUsername());
    }

    public void onSaveButton() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
            viewHandler.openAnAlertBox("All fields are mandatory", "Empty Fields");
        } else {
            updateViewModel.updateFirstName(updateViewModel.getUsername(), firstNameField.getText());
            updateViewModel.updateLastName(updateViewModel.getUsername(), lastNameField.getText());
            viewHandler.closeUpdate();
        }
    }

}
