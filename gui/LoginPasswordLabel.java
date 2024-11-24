package gui;

import java.awt.Color;

import javax.swing.JLabel;

import src.Password;

public class LoginPasswordLabel extends JLabel implements LoginObserver{
    public LoginPasswordLabel() {
        super("[Required: 12 characters | 1 upper case | 1 lower case | 1 number]"); 
        this.setSize(250, 100);
    }

    /*
     *  updates the observer with the validity of the password that was entered
     *  
     *  @param valString (String) - the password | username entered by the user
     */
    public void newLogin(String val){
        // get the password 
        String [] vals = val.split(":");
        String password = vals[1];

        StringBuilder text = new StringBuilder();
        int N = password.length();
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasNum = false;
        boolean hasSpace = false;

        // invalid text, update as necessary
        if (!(Password.isValid(password))){
            // set color to red
            this.setForeground(Color.RED);
            text.append("INVALID PASSWORD: |");

            // check for invalid length
            if (password.length() < 12){
                text.append("too few characters (12 character limit) |");
            }

            // scan string
            for (int i = 0; i < N; i++){
                // lower case
                if (Character.isLowerCase(password.charAt(i))){
                    hasLower = true;
                }

                // upper case
                else if (Character.isUpperCase(password.charAt(i))){
                    hasUpper = true;
                }

                // number
                else if (Character.isDigit(password.charAt(i))){
                    hasNum = true;
                }

                // space
                else if (password.charAt(i) == ' '){
                    hasSpace = true;
                }
            }

            // update text if no lower case
            if (!hasLower){
                text.append("need at least one lower case |");
            }

            // update text if no upper case
            if (!hasUpper){
                text.append("need at least one upper case |");
            }

            // update text if no number
            if (!hasNum){
                text.append("need at least one number |");
            }

            // update text if space
            if (hasSpace){
                text.append("space not allowed |");
            }

            // update the text
            String finalText = text.toString();
            this.setText(finalText);
        }

        // valid password, update as necessary
        else {
            this.setForeground(Color.GREEN);
            this.setText("Strong password");
        }
    }
}