package dataBase;

import org.junit.Test;
import server.model.User;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DAOImplTest
{

  @Test public void findUser() throws SQLException
  {

    User user = new User("test1", "test1", "test1", "test1");
    User user1 = new User("kro", "test", "test", "test");

    assertEquals(user.toString(), DAOImpl.getInstance().findUser("test1")
        .toString()); //Checking that data access object class finds required user

    assertEquals(user1.toString(),
        DAOImpl.getInstance().findUser("kro").toString());

    assertThrows(java.lang.NullPointerException.class,
        () -> DAOImpl.getInstance()
            .create(null, null, null, null)); //Cannot add null user

  }

}