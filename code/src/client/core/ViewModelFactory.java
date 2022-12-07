package client.core;

import client.views.chatSystem.ChatViewModel;
import client.views.chatSystem.MainViewModel;
import client.views.login.LogInViewModel;
import client.views.signUpView.SignUpViewModel;

public class ViewModelFactory {
    private ModelFactory mf;
    private MainViewModel mainViewModel;
    private LogInViewModel logInViewModel;

    private SignUpViewModel signUpViewModel;


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
}
