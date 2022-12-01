package client.networking;

import shared.util.Message;
import shared.Subject;

public interface Client extends Subject {
    void toCallback(Message message);

    void toModel(Message message);

    void startClient();

    void changeUsername(String username);

    String requestStats();
}
