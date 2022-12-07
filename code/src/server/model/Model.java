package server.model;

import shared.Subject;

import java.sql.SQLException;
import java.util.List;

public interface Model extends Subject {
    void signUp(String firstName,String lastName,String username,String password);

    List<String> getAllUsername();

    String getPassword(String username);
}
