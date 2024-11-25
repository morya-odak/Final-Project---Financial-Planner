package gui.login_signup_page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.Password;
import src.UserDB;

public class LoginController implements ActionListener{
    private LoginModel model;

    public LoginController(LoginModel m){
        this.model = m;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        
        // user tries signing up
        String [] commands = command.split(":");
        if (commands.length == 3){
            String username = commands[1];
            String password = commands[2];
            if (!UserDB.checkUser(username) && Password.isValid(password)){
                UserDB.addUser(username, password);
            }

            // invalid sign up
            else {
                model.notifyObservers(username + ":" + password);
            }
        }

        // user tries logging in 
        else {
            model.notifyObservers(command);
        }

    }

    public void addObserver(LoginObserver observer){
        this.model.registerObserver(observer);
    }
}
