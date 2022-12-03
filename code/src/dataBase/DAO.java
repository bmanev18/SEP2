package dataBase;

import server.model.User;

import java.sql.SQLException;

public interface DAO {
    User create() throws SQLException;
}
