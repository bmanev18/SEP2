package dataBase;

import client.model.Chat;
import server.model.User;
import shared.util.Message;

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
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2_database","postgres","2121");
    }

    @Override
    public User create(String firstName,String lastName,String username,String password) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sep2_database.user(firstName,lastName,username,password) VALUES (?,?,?,?);");
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user");
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM  \"user\" where username = ?");
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
            PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" set password = ? where username = ?");
            statement.setString(1,password);
            statement.setString(2,username);
            statement.executeUpdate();
            return password;
        }
    }

    @Override
    public String updateLastName(String username, String lastName) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" set lastname = ? where username = ?");
            statement.setString(1,lastName);
            statement.setString(2,username);
            statement.executeUpdate();
            return lastName;
    }
    }
    @Override
    public String updateFirstName(String username, String firstName) throws SQLException {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" set firstname = ? where username = ?");
            statement.setString(1,firstName);
            statement.setString(2,username);
            statement.executeUpdate();
            return firstName;
        }
    }

    public List<Chat> requestChats(String username) {
        //TODO
        return null;
    }

    public Chat createChat(String name) {
        //TODO
        return null;
    }

    @Override public Message createMessage(Message message)
            throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO messages(sender,toChat,text,datetime) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, message.getSender());
            statement.setInt(2, message.getToChat());
            statement.setString(3, message.getMessageBody());
            statement.setString(4, message.dateTime());
            statement.execute();
        }
        return new Message(message.getSender(), message.getToChat(), message.getMessageBody());
    }

    @Override public List<Message> requestData(String user)
    {
        return null;
    }

    @Override
    public List<Message> loadMessages(String username) {
//TODO
        return null;
    }


    @Override public void addMessagesToChats(Message message) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO chats(id) VALUES (?)");
            statement.setInt(1, message.getToChat());
            statement.execute();
            //needs to be worked on
        }

    }

    @Override public void addReceiverToChat(String username, int chat)
            throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO receivers(username,chat) VALUES (?,?)");
            statement.setString(1, username);
            statement.setInt(2, chat);
            statement.execute();
        }

    }

    @Override public void removeReceiverFromChat(String username, int chatID)
            throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM receivers(username,chat) VALUES (?,?)");
            statement.setString(1, username);
            statement.setInt(2, chatID);
            statement.executeUpdate();
        }
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
