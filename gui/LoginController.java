package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener{
    private LoginModel model;

    public LoginController(LoginModel m){
        this.model = m;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        model.notifyObservers(command);
    }

    public void addObserver(LoginObserver observer){
        this.model.registerObserver(observer);
    }
}
