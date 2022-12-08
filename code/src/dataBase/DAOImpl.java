package dataBase;

import com.sun.source.tree.BreakTree;
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

    @Override
    public String getPassword(String username) throws SQLException {
        String password = " ";
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users where username = ?");
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                password = resultSet.getString("password");
            }
        }
        return password;
    }

    @Override
    public String updatePassword(String username, String password) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE users set password = ? where username = ?");
            statement.setString(1,password);
            statement.setString(2,username);
            statement.executeUpdate();
            return password;
        }
    }

    @Override
    public String updateLastName(String username, String lastName) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE users set lastname = ? where username = ?");
            statement.setString(1,lastName);
            statement.setString(2,username);
            statement.executeUpdate();
            return lastName;
    }
    }
    @Override
    public String updateFirstName(String username, String firstName) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE users set firstname = ? where username = ?");
            statement.setString(1,firstName);
            statement.setString(2,username);
            statement.executeUpdate();
            return firstName;
        }
    }
}
