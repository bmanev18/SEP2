package dataBase;

import client.model.Chat;

import java.sql.SQLException;
import java.util.List;

public interface ChatDAO {

    List<Chat> requestChats(String username) throws SQLException;
}
