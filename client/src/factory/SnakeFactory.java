package factory;

import java.util.ArrayList;

import agent.Snake;
import strategy.StrategyAdvanced;
import strategy.StrategyHuman;
import strategy.StrategyRandom;
import utils.FeaturesSnake;
import utils.Position;


public class SnakeFactory  {

	static int id = 0;
	

	
	public Snake createSnake(FeaturesSnake featuresSnake, String levelAI) {
		
		int x = featuresSnake.getPositions().get(0).getX();
		int y = featuresSnake.getPositions().get(0).getY();
		
		Snake snake = new Snake(new Position(x, y), featuresSnake.getLastAction(), id, featuresSnake.getColorSnake());	
		id++;
		
        switch(levelAI) {
        case "Human":
        	snake.setStrategy(new StrategyHuman());

		break;
        case "Advanced":
        	snake.setStrategy(new StrategyAdvanced());

		break;
        default:
        	snake.setStrategy(new StrategyRandom());
        	
            break;
        }
        
        return snake;
            
	}
        
        
}