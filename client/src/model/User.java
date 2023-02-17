package model;

public class User {
    protected String name;
    protected String email;

    public User(){};

    public User(String name, String email, String Password){
        this.name = name;
        this.email = email;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void setLogged(){}
    public boolean isLogged(){ return true; }
    public void setPassword(){}

}
