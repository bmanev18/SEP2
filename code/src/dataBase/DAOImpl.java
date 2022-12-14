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

    public static synchronized DAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new DAOImpl();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2_database", "postgres", "2121");
    }

    @Override
    public User create(String firstName, String lastName, String username, String password) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sep2_database.user(firstName,lastName,username,password) VALUES (?,?,?,?);");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username.toLowerCase());
            statement.setString(4, password);
            statement.executeUpdate();
        }
        return new User(firstName, lastName, username, password);
    }

    @Override
    public List<String> getUsernames() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sep2_database.user");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> userNames = new ArrayList<>();
            while (resultSet.next()) {
                userNames.add(resultSet.getString("username"));
                System.out.println(userNames);
            }
            return userNames;
        }
    }

    @Override
    public User loadUser(String username) throws SQLException {
        User temp = new User(" ");

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"user\" where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                temp = new User(resultSet.getString("username"));
            }
            return temp;
        }
    }

    @Override
    public String getPassword(String username) throws SQLException {
        String password = " ";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM  \"user\" where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
        }
        return password;
    }

    @Override
    public String updatePassword(String username, String password) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" set password = ? where username = ?");
            statement.setString(1, password);
            statement.setString(2, username);
            statement.executeUpdate();
            return password;
        }
    }

    @Override
    public String updateLastName(String username, String lastName) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" set lastname = ? where username = ?");
            statement.setString(1, lastName);
            statement.setString(2, username);
            statement.executeUpdate();
            return lastName;
        }
    }

    @Override
    public String updateFirstName(String username, String firstName) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" set firstname = ? where username = ?");
            statement.setString(1, firstName);
            statement.setString(2, username);
            statement.executeUpdate();
            return firstName;
        }
    }

    public List<Chat> loadChats(String username) throws SQLException {
        List<Chat> list = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from chat where id in (select chat from receiver where username = ?)");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Chat(Integer.parseInt(resultSet.getString("id")), resultSet.getString("name")));
            }
        }
        return list;
    }

    public Chat createChat(String name) throws SQLException {
        Chat chat = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO chat(name) VALUES (?) returning *;");
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                int id = set.getInt(1);
                addMessage(new Message("@server@", id, "Chat started"));
                chat = new Chat(id, set.getString(2));
            }
        }
        return chat;
    }

    @Override
    public Message addMessage(Message message) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO message(sender,toChat,text,datetime) VALUES (?,?,?,?);");
            statement.setString(1, message.getSender());
            statement.setInt(2, message.getToChat());
            statement.setString(3, message.getMessageBody());
            statement.setString(4, message.getDateTime());
            statement.executeUpdate();
        }
        return message;

    }

    @Override
    public List<Message> loadMessages(String username) {
        List<Message> list = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from message where toChat in (select chat from receiver where username = ?)");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String sender = resultSet.getString("sender");
                int toChat = resultSet.getInt("toChat");
                String text = resultSet.getString("text");
                String date = resultSet.getString("datetime");
                list.add(new Message(sender, toChat, text, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void addReceiverToChat(String username, int chat) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO receiver(username,chat) VALUES (?,?)");
            statement.setString(1, username);
            statement.setInt(2, chat);
            statement.execute();
        }

    }

    @Override
    public void removeReceiverFromChat(String username, int chatID) throws SQLException {
        System.out.println(1);
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM receiver where username = ? and chat = ?");
            statement.setString(1, username);
            statement.setInt(2, chatID);
            statement.executeUpdate();
        }
    }

    @Override
    public User findUser(String name) throws SQLException {
        User user = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sep2_database.user WHERE username=?;");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                user = new User(firstName, lastName, username, password);
            }
        }
        return user;
    }

    @Override
    public List<String> getReceivers(int toChat) throws SQLException {
        List<String> receivers = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT username FROM receiver WHERE chat=?;");
            statement.setInt(1, toChat);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                receivers.add(resultSet.getString("username"));
            }
        }
        return receivers;
    }

    @Override
    public Chat changeColour(int id, String colour) {
        // update chat info in |Chat| and return as a new Chat
        return null;
    }

}
