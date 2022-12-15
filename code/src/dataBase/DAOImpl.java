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
        return DriverManager.getConnection("jdbc:postgresql://mouse.db.elephantsql.com:5432/pqvgnavm?currentSchema=sep2_database", "pqvgnavm", "NaKEa42n6TZgbMg9K1HzvEMpfSo9bkAK");
    }

    @Override
    public User create(String firstName, String lastName, String username, String password) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user_table(firstName,lastName,username,password) VALUES (?,?,?,?);");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username.toLowerCase());
            statement.setString(4, password);
            statement.executeUpdate();
            statement.close();
        }
        return new User(firstName, lastName, username, password);
    }

    @Override
    public List<String> getUsernames() throws SQLException {
        ArrayList<String> userNames = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_table");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userNames.add(resultSet.getString("username"));
                System.out.println(userNames);
            }

        }
        return userNames;
    }

    @Override
    public User loadUser(String username) throws SQLException {
        User temp = new User(" ");

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_table where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                temp = new User(resultSet.getString("username"));
            }
            statement.close();
        }
        return temp;
    }

    @Override
    public String getPassword(String username) throws SQLException {
        String password = " ";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM  user_table where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            connection.close();
            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
            statement.close();
        }
        return password;
    }

    @Override
    public String updatePassword(String username, String password) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user_table set password = ? where username = ?");
            statement.setString(1, password);
            statement.setString(2, username);
            statement.executeUpdate();
            statement.close();
        }
        return password;
    }

    @Override
    public String updateLastName(String username, String lastName) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user_table set lastname = ? where username = ?");
            statement.setString(1, lastName);
            statement.setString(2, username);
            statement.executeUpdate();
            statement.close();
        }
        return lastName;
    }

    @Override
    public String updateFirstName(String username, String firstName) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user_table set firstname = ? where username = ?");
            statement.setString(1, firstName);
            statement.setString(2, username);
            statement.executeUpdate();
            statement.close();
        }
        return firstName;
    }

    public List<Chat> loadChats(String username) throws SQLException {
        List<Chat> list = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from chat_table where id in (select chat from receiver_table where username = ?)");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Chat(Integer.parseInt(resultSet.getString("id")), resultSet.getString("name")));
            }
            statement.close();
        }
        return list;
    }

    public Chat createChat(String name) throws SQLException {
        Chat chat = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO chat_table(name) VALUES (?) returning *;");
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                int id = set.getInt(1);
                addMessage(new Message("@server@", id, "Chat started"));
                chat = new Chat(id, set.getString(2));
            }
            statement.close();
        }
        return chat;
    }

    @Override
    public Message addMessage(Message message) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO message_table(sender,toChat,text,datetime) VALUES (?,?,?,?);");
            statement.setString(1, message.getSender());
            statement.setInt(2, message.getToChat());
            statement.setString(3, message.getMessageBody());
            statement.setString(4, message.getDateTime());
            statement.executeUpdate();
            statement.close();
        }
        return message;

    }

    @Override
    public List<Message> loadMessages(String username) {
        List<Message> list = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from message_table where toChat in (select chat from receiver_table where username = ?)");
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO receiver_table(username,chat) VALUES (?,?)");
            statement.setString(1, username);
            statement.setInt(2, chat);
            statement.execute();
            statement.close();
        }

    }

    @Override
    public void removeReceiverFromChat(String username, int chatID) throws SQLException {
        System.out.println(1);
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM receiver_table where username = ? and chat = ?");
            statement.setString(1, username);
            statement.setInt(2, chatID);
            statement.executeUpdate();
            statement.close();
        }
    }

    @Override
    public User findUser(String name) throws SQLException {
        User user = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_table WHERE username=?;");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                user = new User(firstName, lastName, username, password);
                statement.close();
            }
        }
        return user;
    }

    @Override
    public List<String> getReceivers(int toChat) throws SQLException {
        List<String> receivers = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT username FROM receiver_table WHERE chat=?;");
            statement.setInt(1, toChat);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                receivers.add(resultSet.getString("username"));
            }
            statement.close();
        }
        return receivers;
    }

    @Override
    public Chat changeColour(int id, String colour) {
        // update chat info in |Chat| and return as a new Chat
        return null;
    }

    @Override
    public ArrayList<String> getInfoForUser(String username) throws SQLException {
        ArrayList<String> info = new ArrayList<>(1);
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT firstname,lastname FROM user_table WHERE username=?;");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                info.add(resultSet.getString("firstname"));
                info.add(resultSet.getString("lastname"));

            }
            statement.close();
        }
        return info;
    }

    @Override
    public void updateUser(String firstName, String lastName, String password, String username) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE user_table SET firstname = ?, lastname = ? WHERE username = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.executeUpdate();
            statement.close();
        }
    }

}
