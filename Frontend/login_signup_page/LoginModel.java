package Frontend.login_signup_page;
import java.util.ArrayList;

public class LoginModel {
    private ArrayList <LoginObserver> observers;

    public LoginModel () {
        this.observers = new ArrayList <LoginObserver> ();
    }

    public void registerObserver(LoginObserver observer){
        observers.add(observer);
    }

    public void deregisterObserver(LoginObserver observer){
        observers.remove(observer);
    }

    /*
     *  updates the observers with the new info
     *  
     *  @param val (String) - the username and password entered by the user
     */
    public void notifyObservers(String val){
        for (LoginObserver o : observers){
            o.newLogin(val);
        }
    }
}