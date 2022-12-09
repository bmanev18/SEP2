package dataBase;

import server.model.User;

import java.sql.*;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl instance;

    private UserDAOImpl() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized UserDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/sep2_database?currentSchema=sep2_database",
                "postgres", "2121");
    }

    @Override
    public User create() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users(firstName,lastName,password,username) VALUES (?,?,?,?);");
            statement.setString(1, "Cristiano");
            statement.setString(2, "Ronaldo");
            statement.setString(3, "SIU");
            statement.setString(4, "CR7");
            statement.execute();
        }
        return new User("CR7", "Cristiano", "Ronaldo", "SIU");
    }

    @Override
    public User findUser(String name) throws SQLException {
        User user = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username=?;");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getNString("firstName");
                String lastName = resultSet.getNString("lastName");
                String username = resultSet.getNString("username");
                String password = resultSet.getNString("password");

                user = new User(firstName, lastName, username, password);
            }
        }
        return user;
    }
}


