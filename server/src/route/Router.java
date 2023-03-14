package route;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import service.ApiService;
import service.GameService;
import service.LoginService;
import utils.Message;

public class Router {
    
    public static Message route(ApiService apiService, Message message){
        Message toreturn = new Message();
        switch (message.getAction()) {
            case "login":
                return toreturn = LoginService.login(apiService, message);
            case "saveScore":
                return toreturn = GameService.saveScore(apiService, message);
            case "noActionProvided":
                toreturn.setStatusCode(400);
                toreturn.setMessage("Aucune action renseignée");
                return toreturn;
            default:
                toreturn.setStatusCode(400);
                toreturn.setMessage("Commande Invalide");
                return toreturn;
        }
    }
}