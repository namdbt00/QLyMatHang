package dao;

import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    @Test
    void checkLogin(){
        UserDAO userDAO = new UserDAO();
        String username = "Huy";
        String password = "1";
        User user = userDAO.checkLogin(username,password);
        assertNotNull(user);
        assertEquals(user, user);
    }
    @Test
    void checkLoginFail() {
        UserDAO userDAO = new UserDAO();
        String username = "Huyen";
        String password = "1";
        User user = userDAO.checkLogin(username, password);
        assertNull(user);
    }
    @Test
    void checkLoginFail1() {
        UserDAO userDAO = new UserDAO();
        String username = "Huy";
        String password = "huy";
        User user = userDAO.checkLogin(username, password);
        assertNull(user);
    }
    @Test
    void checkLoginFail2() {
        UserDAO userDAO = new UserDAO();
        String username = "";
        String password = "";
        User user = userDAO.checkLogin(username, password);
        assertNull(user);
    }
    @Test
    void checkLoginFail3() {
        UserDAO userDAO = new UserDAO();
        String username = "";
        String password = "1";
        User user = userDAO.checkLogin(username, password);
        assertNull(user);
    }
    @Test
    void checkLoginFail4() {
        UserDAO userDAO = new UserDAO();
        String username = "Huy";
        String password = "";
        User user = userDAO.checkLogin(username, password);
        assertNull(user);
    }
}
