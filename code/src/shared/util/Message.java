package shared.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {
    private String sender;
    private String messageBody;
    private int toChat;
    private String datetime;

    public Message(String sender, int toChat, String messageBody) {
        this.sender = sender;
        this.messageBody = messageBody;
        this.toChat = toChat;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        datetime = formatter.format(new Date());
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getSender() {
        return sender;
    }

    public int getToChat() {
        return toChat;
    }

    public String dateTime() {
        return datetime;
    }

    @Override
    public String toString() {
        return String.format("%s -> %d : %s", sender, toChat, messageBody);
    }
}
