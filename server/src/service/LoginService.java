package service;

import model.User;
import utils.Message;

public class LoginService {
    
    public static Message login(ApiService apiService, Message data){
        apiService.get("/login", "");
        Message message = new Message();
        message.setUser(new User("test", "test@test.fr", "test"));
        return message;
    }

    public static String logout(ApiService apiService, String data){
        return "";
    }
}
