package dataBase;

import server.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOImpl implements DAO {
    private static DAOImpl instance;

    private DAOImpl() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized DAOImpl getInstance() throws SQLException{
        if (instance==null){
            instance = new DAOImpl();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/sep2_database?currentSchema=sep2_database","postgres","admin");
    }

    @Override
    public User create() throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users(firstName,lastName,password,username) VALUES (?,?,?,?);");
            statement.setString(1,"Cristiano");
            statement.setString(2,"Ronaldo");
            statement.setString(3,"SIU");
            statement.setString(4,"CR7");
            statement.execute();
        }
        return new User("CR7","Cristiano","Ronaldo","SIU");
    }
}
