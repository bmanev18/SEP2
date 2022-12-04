package dataBase;

import server.model.User;
import shared.Subject;

import java.sql.SQLException;
import java.util.List;

public interface DAO{
    User create(String firstName,String lastName,String username,String password) throws SQLException;

    List<String> getUsernames() throws SQLException;
}
