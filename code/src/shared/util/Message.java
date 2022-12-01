package shared.util;

import java.io.Serializable;

public class Message implements Serializable {
    private String username;
    private String messageBody;

    public Message(String messageBody, String username) {
        this.username = username;
        this.messageBody = messageBody;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", username, messageBody);
    }
}
