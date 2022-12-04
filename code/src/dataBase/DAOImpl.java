package dataBase;

import server.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2_database","postgres","yuzu");
    }

    @Override
    public User create(String firstName,String lastName,String username,String password) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sep2_database.users(firstName,lastName,username,password) VALUES (?,?,?,?);");
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setString(3,username.toLowerCase());
            statement.setString(4,password);
            statement.executeUpdate();
        }
        return new User(firstName,lastName,username,password);
    }

    @Override
    public List<String> getUsernames() throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> userNames = new ArrayList<>();
            while(resultSet.next()){
                userNames.add(resultSet.getString("username"));
                System.out.println(userNames);
            }
            return userNames;
        }
    }
}
