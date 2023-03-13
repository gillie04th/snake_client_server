package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import utils.Message;

public class LoginService {
    
    public static Message login(ApiService apiService, Message data){
        String result = apiService.post("/login", "{ \"email\": \"" + data.getUser().getEmail() + "\", \"password\":\"" + data.getUser().getPassword() + "\"}");
        Message message = new Message();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            message = mapper.readValue(result, Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        message.setStatusCode(200);
        return message;
    }

    public static String logout(ApiService apiService, String data){
        return "";
    }
}
