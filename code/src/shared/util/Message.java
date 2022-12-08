package shared.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Message implements Serializable
{
  private String username;
  private String messageBody;
  private LocalDateTime dateTime;


  public Message(String messageBody, String username)
  {
    this.username = username;
    this.messageBody = messageBody;
    this.dateTime = LocalDateTime.now();
  }

  public String getMessageBody()
  {
    return messageBody;
  }

  public String getUsername()
  {
    return username;
  }

  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public String dateTime()
  {
    String frmtdDate = dateFormat.format(dateTime);

    return frmtdDate;
  }

  @Override public String toString()
  {
    return String.format("%s: %s", username, messageBody);
  }
}
