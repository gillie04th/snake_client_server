package model;

public class User {
    protected boolean logged;
    protected String name;
    protected String email;

    public User(){};

    public User(String name, String email, String Password){
        this.name = name;
        this.email = email;
        logged = true;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public boolean isLogged(){
        return logged;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setIsLogged(boolean logged){
        this.logged = logged;
    }
}
