package dataBase;

import shared.util.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMain {
    public static void main(String[] args) throws SQLException {
        //System.out.println(DAOImpl.getInstance().loadChats("test"));
        //System.out.println(DAOImpl.getInstance().getPassword("test"));
        //System.out.println(DAOImpl.getInstance().loadMessages("test"));
        //System.out.println(DAOImpl.getInstance().loadUser("test"));
//        DAOImpl.getInstance().createMessage(new Message("user", 2, "haha"));
        //System.out.println(DAOImpl.getInstance().getReceivers(1));
        //DAOImpl.getInstance().removeReceiverFromChat("test", 1);
        //DAOImpl.getInstance().removeReceiverFromChat("@server@", 1);
        //DAOImpl.getInstance().addReceiverToChat("test", 1);
        /*Message message = new Message("@server@", 1, "test" + " has left the chat");
        System.out.printf("%s -> %d: %s @%s", message.getSender(), message.getToChat(), message.getMessageBody(), message.getDateTime());
        DAOImpl.getInstance().createMessage(message);*/

        //System.out.println(DAOImpl.getInstance().createChat("newChat"));
        Map<String, List<String>> map = new HashMap<>();
        map.put("String", new ArrayList<>());
        System.out.println(map.get("string"));

    }
}
