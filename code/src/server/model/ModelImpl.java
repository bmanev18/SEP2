package server.model;

import client.model.Chat;
import dataBase.*;
import shared.networking.ClientCallback;
import shared.util.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelImpl implements Model {
    private PropertyChangeSupport support;
    private Map<String, ClientCallback> map;
    private List<String> usernames;

    public ModelImpl() {
        support = new PropertyChangeSupport(this);
        map = new HashMap<>();
    }

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
    public User getUser(String username, ClientCallback client) {
        map.put(username, client);
        try {
            return DAOImpl.getInstance().findUser(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPassword(String username) {
        try {
            return DAOImpl.getInstance().getPassword(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public void addMessage(Message message) {
        try {
            DAOImpl.getInstance().addMessage(message);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding message");
        }
    }

    @Override
    public List<ClientCallback> loadReceivers(int toChat) {
        List<String> usernames;
        try {
            usernames = DAOImpl.getInstance().getReceivers(toChat);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching receivers");
        }
        List<ClientCallback> clients = new ArrayList<>();
        for (String username : usernames) {
            clients.add(map.get(username));
        }
        return clients;
    }


    @Override
    public List<Message> loadMessages(String username) {
        try {
            return DAOImpl.getInstance().loadMessages(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Chat> loadChats(String username) {
        List<Chat> list;
        try {
            // return all chats to which the username is subscribed
            list = DAOImpl.getInstance().loadChats(username);
        } catch (SQLException e) {
            throw new RuntimeException("Error loading chats");
        }
        return list;
    }

    @Override
    public User loadUsername(String username) {
        User user;
        try {
            // search for a user with this username
            user = DAOImpl.getInstance().loadUser(username);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding username");
        }
        // null user will be returned if it does not exist
        return user;
    }

    @Override
    public Chat createChatWith(String creator, String invited, String chatName) {
        Chat chat = null;
        try {
            User user = loadUsername(invited);
            // If user exists
            if (user != null) {
                //create chat
                chat = DAOImpl.getInstance().createChat(chatName);
                int id = chat.getId();
                //subscribe both to it
                DAOImpl.getInstance().addReceiverToChat(creator, id);
                DAOImpl.getInstance().addReceiverToChat(invited, id);
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
            DAOImpl.getInstance().addReceiverToChat(username, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void leaveChat(String username, int id) {
        try {
            // Unsubscribe user from a chat
            DAOImpl.getInstance().removeReceiverFromChat(username, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addClient(String username, ClientCallback client) {
        map.put(username, client);
    }

    @Override
    public ClientCallback removeClient(String username) {
        return map.remove(username);
    }

    @Override
    public ClientCallback getClient(String username) {
        return map.get(username);
    }

    @Override
    public Chat changeColour(Chat chat, String colour) {
        //TODO
        try {
            return DAOImpl.getInstance().changeColour(chat.getId(), colour);
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
