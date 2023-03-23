package service;

import utils_serv.Message;
import java.util.Random;

import model_serv.GameValidator;

public class GameService extends AbstractService {

    public static Message saveScore(ApiService apiService, Message data) {
        apiService.post("/game/score", data.toJson());
        Message message = new Message();
        message.setStatusCode(200);
        return message;
    }

    public static Message turn(Message data) {
        GameValidator validator = new GameValidator(data); 
        validator.checkMove();
        return validator.getResult();
    }

    public static Message updateTurn(Message data){

        GameValidator validator = new GameValidator(data);

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
        return validator.getResult();
    }
}
