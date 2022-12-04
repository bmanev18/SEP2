package server.model;

import dataBase.DAOImpl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.List;

public class ModelImpl implements Model {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private List<String> usernames;


    @Override
    public void signUp(String firstName, String lastName, String username, String password) {
        try {
            DAOImpl.getInstance().create(firstName,lastName,username,password);
        } catch (SQLException e) {
            support.firePropertyChange("SignUpDenied",null,null);
            System.out.println("Database Could Not Be Reached");
        }
    }

    @Override
    public List<String> getAllUsername(){
        try {
            usernames = DAOImpl.getInstance().getUsernames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usernames;
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);

    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);

    }
}
