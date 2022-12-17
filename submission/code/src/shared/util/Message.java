package shared.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable
{
  private String sender;
  private String messageBody;
  private int toChat;
  private String datetime;

  public Message(String sender, int toChat, String messageBody)
  {
    this(sender, toChat, SwearDetection.changeSwearStatic(messageBody),
        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
  }

  public Message(String sender, int toChat, String messageBody, String datetime)
  {
    this.sender = sender;
    this.messageBody = SwearDetection.changeSwearStatic(messageBody);
    this.toChat = toChat;
    this.datetime = datetime;

  }

  public String getMessageBody()
  {
    return messageBody;
  }

  public String getSender()
  {
    return sender;
  }

  public int getToChat()
  {
    return toChat;
  }

  public String getDateTime()
  {
    return datetime;
  }

  @Override public String toString()
  {
    if (sender.equals("server"))
    {
      return String.format("%s", messageBody);
    }
    else
    {
      return String.format("%s -> %s", sender, messageBody);
    }
  }
}
