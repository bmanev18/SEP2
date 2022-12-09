package server.model;

import client.model.Chat;
import dataBase.*;
import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.List;

public class ModelImpl implements Model {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private List<String> usernames;


    @Override
    public synchronized void signUp(String firstName, String lastName, String username, String password) {
        try {
            DAOImpl.getInstance().create(firstName, lastName, username, password);
        } catch (SQLException e) {
            support.firePropertyChange("SignUpDenied", null, null);
            System.out.println("Database Could Not Be Reached");
        }
    }

    @Override
    public synchronized List<String> getAllUsername() {
        try {
            usernames = DAOImpl.getInstance().getUsernames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usernames;
    }

    @Override
    public String getPassword(String username) {
        String password = " ";
        try {
            password = DAOImpl.getInstance().getPassword(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return password;
    }

    @Override
    public void updatePassword(String username, String password) {
        try {
            DAOImpl.getInstance().updatePassword(username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateFirstName(String username, String firstName) {
        try {
            DAOImpl.getInstance().updateFirstName(username, firstName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLastName(String username, String lastName) {
        try {
            DAOImpl.getInstance().updateLastName(username, lastName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //B

    @Override
    public List<Message> loadMessages(String username) {
        List<Message> list;
        try {
            list = MessageDAOImpl.getInstance().loadMessages(username);
        } catch (SQLException e) {
            throw new RuntimeException("Error loading messages");
        }

        return list;
    }

    @Override
    public List<Chat> loadChats(String username) {
        List<Chat> list;
        try {
            // return all chats to which the username is subscribed
            list = ChatDAOImpl.getInstance().requestChats(username);
        } catch (SQLException e) {
            throw new RuntimeException("Error loading chats");
        }
        return list;
    }

    @Override
    public User loadUser(String username) {
        User user;
        try {
            // search for a user with this username
            user = UserDAOImpl.getInstance().findUser(username);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user");
        }
        // null user will be returned if it does not exist
        return user;
    }

    @Override
    public Chat createChatWith(String creator, String username) {
        Chat chat = null;
        try {
            User user = loadUser(username);
            // If user exists
            if (user != null) {
                //create chat
                chat = ChatDAOImpl.getInstance().createChat();
                int id = chat.getId();
                //subscribe both to it
                RecieverDAOImpl.getInstance().addReceiverToChat(creator, id);
                RecieverDAOImpl.getInstance().addReceiverToChat(username, id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // null chat will be returned if user does not exist
        return chat;
    }

    @Override
    public void addUser(String username, int id) {
        try {
            //subscribe a user to chat
            RecieverDAOImpl.getInstance().addReceiverToChat(username, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void leaveChat(String username, int id) {
        try {
            // Unsubscribe user from a chat
            RecieverDAOImpl.getInstance().removeReceiverFromChat(username, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);

    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName, listener);

    }
}
