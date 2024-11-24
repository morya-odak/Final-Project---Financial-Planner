package gui;

import java.awt.Color;

import javax.swing.JLabel;

import src.UserDB;

public class LoginUserLabel extends JLabel implements LoginObserver{
    public LoginUserLabel(){
        // empty text, only should appear when there is an invalid username
        super("");
        this.setSize(250, 100);
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
            this.setForeground(Color.RED);
            this.setText("INVALID USERNAME: User already exists, try a new username");
        }
        else {
            this.setForeground(Color.GREEN);
            this.setText("Valid username");
        }
    }  
}
