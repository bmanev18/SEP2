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
    public PasswordField currentPasswordField;
    public PasswordField newPasswordField;
    private ViewHandler viewHandler;
    private UpdateViewModel updateViewModel;

    public void innit(ViewHandler viewHandler, UpdateViewModel updateViewModel) {
        this.viewHandler = viewHandler;
        this.updateViewModel = updateViewModel;
    }

    public void onSaveButton() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || currentPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty()) {
            viewHandler.openAnAlertBox("All fields are mandatory", "Empty Fields");
        } else if (!currentPasswordField.getText().equals(updateViewModel.getPassword("test"))) {
            viewHandler.openAnAlertBox("Passwords do not match", "Not updated");
        } else if (newPasswordField.getText().length() > 8) {
            viewHandler.openAnAlertBox("Password must be no longer than 8 symbols", "Invalid Password");
        } else {
            updateViewModel.updateFirstName("test",firstNameField.getText());
            updateViewModel.updateLastName("test",lastNameField.getText());
            updateViewModel.updatePassword("test",newPasswordField.getText());
        }
    }
}
