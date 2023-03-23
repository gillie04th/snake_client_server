package controller;

import model.SnakeGame;
import model.InputMap;
import utils.AgentAction;
import view.PanelSnakeGame;
import view.ViewSnakeGame;
import view.ViewCommand;
import view.ViewLogin;

public class ControllerSnakeGame extends AbstractController {

	SnakeGame snakeGame;
	InputMap map; 
	
	public ControllerSnakeGame() {
		
		String layoutName = "layouts/smallNoWall.lay";
		
		this.snakeGame = new SnakeGame(100, 55555,layoutName);
		this.snakeGame.serverConnection();
		this.snakeGame.init();
		
		this.game = snakeGame;

		ViewLogin ViewLogin = new ViewLogin(this, snakeGame);
	}

	public void goUp(){
        this.snakeGame.setInputMoveHuman1(AgentAction.MOVE_UP);
	}
	
	public void goDown(){
		this.snakeGame.setInputMoveHuman1(AgentAction.MOVE_DOWN);
	}	
	
	public void goLeft(){
		this.snakeGame.setInputMoveHuman1(AgentAction.MOVE_LEFT);
	}	
	
	public void goRight(){
		this.snakeGame.setInputMoveHuman1(AgentAction.MOVE_RIGHT);
	}

	public void closeGame(){
		this.snakeGame.closeConnection();
	}

	@Override
	public void restart() {
		this.game.pause();
		if(this.snakeGame.getTurn() < this.snakeGame.getMaxTurn()){
			this.snakeGame.saveScore("restart");
		}
		this.game.init();
	}

	public void login(String login, String password){
		if(this.snakeGame.login(login, password)){
			PanelSnakeGame panelSnakeGame = new PanelSnakeGame(snakeGame.getInputMap().getSizeX(), snakeGame.getInputMap().getSizeY(), snakeGame.getInputMap().get_walls(), snakeGame.getInputMap().getStart_snakes(), snakeGame.getInputMap().getStart_items());
			ViewSnakeGame viewSnakeGame = new ViewSnakeGame(this, snakeGame, panelSnakeGame);
			ViewCommand viewCommand = new ViewCommand(this, snakeGame);
		}
	}
}
