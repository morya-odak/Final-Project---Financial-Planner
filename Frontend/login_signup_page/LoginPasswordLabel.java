package Frontend.login_signup_page;

import java.awt.Color;

import javax.swing.JLabel;

import Backend.Password;

/**
 * The `LoginPasswordLabel` class is a graphical component that serves as 
 * a status indicator for password validity during login or signup. 
 * It implements the `LoginObserver` interface to dynamically update 
 * the displayed message based on the entered password.
 */
public class LoginPasswordLabel extends JLabel implements LoginObserver {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a `LoginPasswordLabel` with a default message 
     * indicating password requirements.
     */
    public LoginPasswordLabel() {
        super("[12 characters | 1 upper case | 1 lower case | 1 number]");
    }

    /**
     * Updates the label to reflect the validity of the entered password. 
     * If the password is invalid, the label displays specific reasons for invalidity.
     * If valid, the label displays a success message.
     * 
     * @param val - a `String` containing the username and password separated by a colon
     */
    @Override
    public void newLogin(String val) {
        String[] vals = val.split(":");
        String password = vals[1];

        StringBuilder text = new StringBuilder();
        int N = password.length();
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasNum = false;
        boolean hasSpace = false;


        if (!Password.isValid(password)) {

            this.setForeground(Color.BLACK);
            text.append("|");

            if (password.length() < 12) {
                text.append("too few characters |");
            }

            for (int i = 0; i < N; i++) {
                if (Character.isLowerCase(password.charAt(i))) {
                    hasLower = true;
                }
                else if (Character.isUpperCase(password.charAt(i))) {
                    hasUpper = true;
                }
                else if (Character.isDigit(password.charAt(i))) {
                    hasNum = true;
                }
                else if (password.charAt(i) == ' ') {
                    hasSpace = true;
                }
            }

            if (!hasLower) {
                text.append("at least one lower case |");
            }
            if (!hasUpper) {
                text.append("at least one upper case |");
            }
            if (!hasNum) {
                text.append("at least one number |");
            }
            if (hasSpace) {
                text.append("space not allowed |");
            }

            this.setText(text.toString());
        } 
        else {
            this.setForeground(Color.BLACK);
            this.setText("Strong password");
        }
    }
}
