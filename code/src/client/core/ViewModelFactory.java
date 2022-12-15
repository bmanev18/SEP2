package client.core;

import client.views.addUser.AddUserViewModel;
import client.views.chatSystem.MainViewModel;
import client.views.login.LogInViewModel;
import client.views.signUpView.SignUpViewModel;
import client.views.updateView.UpdateViewModel;

public class ViewModelFactory {
    private ModelFactory mf;
    private MainViewModel mainViewModel;
    private LogInViewModel logInViewModel;

    private UpdateViewModel updateViewModel;

    private SignUpViewModel signUpViewModel;
    private AddUserViewModel addUserViewModel;


    public ViewModelFactory(ModelFactory mf) {
        this.mf = mf;
    }

    public MainViewModel getMainViewModel() {
        if (mainViewModel == null) {
            mainViewModel = new MainViewModel(mf.getModel());
        }
        return mainViewModel;
    }

    public LogInViewModel getLogInViewModel() {
        if (logInViewModel == null) {
            logInViewModel = new LogInViewModel(mf.getModel());
        }
        return logInViewModel;
    }

    public SignUpViewModel getSignUpViewModel() {
        if (signUpViewModel == null) {
            signUpViewModel = new SignUpViewModel(mf.getModel());
        }
        return signUpViewModel;
    }

    public UpdateViewModel getUpdateViewModel() {
        if (updateViewModel == null) {
            updateViewModel = new UpdateViewModel(mf.getModel());
        }
        return updateViewModel;
    }

    public AddUserViewModel getAddUserViewModel() {
        if (addUserViewModel == null) {
            addUserViewModel = new AddUserViewModel(mf.getModel());
        }
        return addUserViewModel;
    }
}
