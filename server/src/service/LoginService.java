package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;

import utils_serv.Message;

public class LoginService extends AbstractService{
    
    public static Message login(ApiService apiService, Message data){
        String result = apiService.post("/login", "{ \"email\": \"" + data.getUser().getEmail() + "\", \"password\":\"" + data.getUser().getPassword() + "\"}");
        Message message = new Message();
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            message = mapper.readValue(result, Message.class);
            message.setStatusCode(200);
        } catch (JsonProcessingException e) {
            message.setStatusCode(400);
            message.setMessage("Erreur des données fournies");
        }
        return message;
    }

    public static String logout(ApiService apiService, String data){
        return "";
    }
}
