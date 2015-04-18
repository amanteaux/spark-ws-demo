package org.devteam.ws.user;

/**
 * Created by amanteaux on 18/04/15.
 */
public class User {

    private final String name;
    private final String message;

    public User(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

}
