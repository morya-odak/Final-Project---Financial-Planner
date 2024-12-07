package test;
import org.junit.Test;

import Backend.UserDB;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class UserDBTest {
    @Test
    public void testUserExists(){
        // generate passwords
        UserDB.addUser("mrafko", "helloworld12");
        UserDB.addUser("jrafko", "iLoveLizards237");
        UserDB.addUser("danTheMan", "helloasdfksagd128421");
        UserDB.addUser("tomAspinal1921", "weLuvLioins1829");

        // check if user login exists [username + password match] (multiple tests to test for random salts)
        assertTrue(UserDB.checkLogin("mrafko", "helloworld12"));
        assertTrue(UserDB.checkLogin("jrafko", "iLoveLizards237"));
        assertTrue(UserDB.checkLogin("danTheMan", "helloasdfksagd128421"));
        assertTrue(UserDB.checkLogin("tomAspinal1921", "weLuvLioins1829"));

        // ensure the incorrect password entry does not exist
        assertFalse(UserDB.checkLogin("mrafko", "helloworld11"));
        assertFalse(UserDB.checkLogin("jrafko", "iLoveLizards239"));
        assertFalse(UserDB.checkLogin("danTheMan", "helloasdfksagd128411"));
        assertFalse(UserDB.checkLogin("tomAspinal1921", "weLuvLioins1839"));

        // check for existence of user, useful for creating account with already existing user
        assertTrue(UserDB.checkUser("mrafko"));
        assertTrue(UserDB.checkUser("jrafko"));
        assertTrue(UserDB.checkUser("danTheMan"));
        assertTrue(UserDB.checkUser("tomAspinal1921"));
        assertFalse(UserDB.checkUser("randomUser"));
    }
}
