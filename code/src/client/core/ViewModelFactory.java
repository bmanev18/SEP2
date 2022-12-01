package client.core;

import client.views.chatSystem.ChatViewModel;
import client.views.login.LogInViewModel;
import client.views.signUpView.SignUpViewModel;

public class ViewModelFactory {
    private ModelFactory mf;
    private ChatViewModel chatViewModel;
    private LogInViewModel logInViewModel;

    private SignUpViewModel signUpViewModel;


    public ViewModelFactory(ModelFactory mf) {
        this.mf = mf;
    }

    public ChatViewModel getChatViewModel() {
        if (chatViewModel == null) {
            chatViewModel = new ChatViewModel(mf.getModel());
        }
        return chatViewModel;
    }

    public LogInViewModel getLogInViewModel() {
        if (logInViewModel == null) {
            logInViewModel = new LogInViewModel(mf.getModel());
        }
        return logInViewModel;
    }
    public SignUpViewModel getSignUpViewModel(){
        if (signUpViewModel == null) {
            signUpViewModel = new SignUpViewModel(mf.getModel());
        }
        return signUpViewModel;
    }
}
