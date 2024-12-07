package Frontend.login_signup_page;

import java.util.ArrayList;

/**
 * The `LoginModel` class acts as the data model in the login/signup system. 
 * It manages a list of observers and notifies them of changes, such as login or signup attempts.
 */
public class LoginModel {
    private ArrayList<LoginObserver> observers;

    /**
     * Constructs a `LoginModel` and initializes an empty list of observers.
     */
    public LoginModel() {
        this.observers = new ArrayList<>();
    }

    /**
     * Registers a new observer to receive updates about login or signup events.
     * 
     * @param observer - the `LoginObserver` to be added
     */
    public void registerObserver(LoginObserver observer) {
        observers.add(observer);
    }

    /**
     * Deregisters an observer, removing it from the list of observers.
     * 
     * @param observer - the `LoginObserver` to be removed
     */
    public void deregisterObserver(LoginObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers with the provided value. Typically used
     * to update observers with login or signup information.
     * 
     * @param val - the username and password entered by the user, formatted as a string
     */
    public void notifyObservers(String val) {
        for (LoginObserver o : observers) {
            o.newLogin(val);
        }
    }
}
