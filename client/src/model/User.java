package model;

public class User {
    protected boolean isLogged;

    public User(){
        isLogged = false;
    }

    public boolean isLogged(){
        return isLogged;
    }
}
