package dataBase;

import server.model.User;

import java.sql.SQLException;

public interface UserDAO
{
    User create() throws SQLException;
    User findUser(String user) throws SQLException;


}
