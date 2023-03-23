package service;

import utils_serv.Message;
import java.util.Random;

import exceptions.UserNotLoggedException;
import model_serv.GameValidator;

public class GameService extends AbstractService {

    public static Message saveScore(ApiService apiService, Message data) {
        GameValidator validator = new GameValidator(data);
        Message message = new Message();
        try {
            validator.isAuthenticated();
            apiService.post("/game/score", data.toJson());
            message.setStatusCode(200);
        } catch (UserNotLoggedException e) {
            message.setStatusCode(400);
            message.setMessage(e.getMessage());
        }
        return message;
    }

    public static Message turn(Message data) {
        GameValidator validator = new GameValidator(data);
        Message message = new Message();
        try {
            validator.isAuthenticated();
            validator.checkMove();
            message = validator.getResult();
        } catch (UserNotLoggedException e) {
            message.setStatusCode(400);
            message.setMessage(e.getMessage());
        }
        return message;
    }

    public static Message updateTurn(Message data) {

        GameValidator validator = new GameValidator(data);
        Message message = new Message();

        try {
            validator.isAuthenticated();
            validator.checkSnakeEaten();
            validator.checkWalls();
            boolean isAppleEaten = validator.checkItemFound();

            if (isAppleEaten) {
                validator.addRandomApple();
                Random rand = new Random();
                double r = rand.nextDouble();

                if (r < validator.probSpecialItem) {
                    System.out.println("add random item");
                    validator.addRandomItem();
                }
            }
            validator.removeSnake();
            validator.updateSnakeTimers();
            message = validator.getResult();
        } catch (UserNotLoggedException e) {
            message.setStatusCode(400);
            message.setMessage(e.getMessage());
        }
        return message;
    }
}
