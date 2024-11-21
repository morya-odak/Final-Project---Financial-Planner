import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class PasswordTest {
    
    @Test
    public void testIsValid(){
        String p1 = "hello";
        String p2 = "helloyuiolm";
        String p3 = "helloyuiolmh";
        String p4 = "hellouioimlkH";
        String p5 = "helloiuosHsas4";

        assertEquals(false, Password.isValid(p1));
        assertEquals(false, Password.isValid(p2));
        assertEquals(false, Password.isValid(p3));
        assertEquals(false, Password.isValid(p4));
        assertEquals(true, Password.isValid(p5));
    }

    @Test
    public void testGeneratePassword(){
        // assume salt is stored
        byte [] salt = Password.generateSalt();

        // ensure two passwords are identical
        try {
            String psswd1 = Password.generatePassword("helloworldY2", salt);
            String psswd2 = Password.generatePassword("helloworldY2", salt);
            assertEquals(psswd1, psswd2);
        } catch (Exception e){
            e.printStackTrace();
        }

        // ensure two passwords are different, same password, different salt
        try {
            byte [] salt1 = Password.generateSalt();
            String psswd2 = Password.generatePassword("helloworldY2", salt);
            String psswd3 = Password.generatePassword("helloworldY2", salt1);
            assertNotEquals(psswd2, psswd3);
        } catch (Exception e){
            e.printStackTrace();
        }

        // ensure two passwords are different, different password, same salt
        try {
            String psswd4 = Password.generatePassword("helloworldY2different", salt);
            String psswd5 = Password.generatePassword("helloworldY2", salt);
            assertNotEquals(psswd4, psswd5);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}