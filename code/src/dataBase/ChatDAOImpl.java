package dataBase;

import client.model.Chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ChatDAOImpl {

    private static ChatDAOImpl instance;

    private ChatDAOImpl() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized ChatDAOImpl getInstance() throws SQLException{
        if (instance==null){
            instance = new ChatDAOImpl();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2_database","postgres","2121");
    }

    public List<Chat> requestChats(String username) {
        //TODO
        return null;
    }

    public Chat createChat() {
        //TODO
        return null;
    }
}
