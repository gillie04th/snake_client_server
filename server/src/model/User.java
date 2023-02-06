package model;

public class User {
    protected boolean isLogged;
    protected String name;
    protected String email;
    protected String password;

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.isLogged = true;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public boolean isLogged(){
        return isLogged;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setIsLogged(boolean logged){
        this.isLogged = logged;
    }
}