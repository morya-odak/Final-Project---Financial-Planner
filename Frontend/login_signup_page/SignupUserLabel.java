package Frontend.login_signup_page;

import java.awt.Color;

import javax.swing.JLabel;

import Backend.UserDB;

public class SignupUserLabel extends JLabel implements LoginObserver{
    public SignupUserLabel(){
        super("");
        this.setSize(350, 100);
    }

    /*
     *  alerts the user if the username already exists and ensures that entry
     *  will not be gained, this is to gaurentee no duplicate users
     * 
     *  @param val (String) - the username | password entered by the user
     */
    public void newLogin(String val){
        // gets the username from the info entered
        String [] vals = val.split(":");
        String username = vals[0];

        if (UserDB.checkUser(username)){
            setText("Username already exists");
            setForeground(Color.BLACK);
        }
        else {
            setText("Valid username");
            setForeground(Color.BLACK);
        }
    }
}