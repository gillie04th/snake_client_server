package service;

import utils.Message;

public class GameService {
    
    public static Message saveScore(ApiService apiService, Message data){
        apiService.post("/game/score", data.toJson());
        Message message = new Message();
        message.setStatusCode(200);
        return message;
    }

}
