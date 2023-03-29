package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import factory.SnakeFactory;
import item.Item;
import strategy.StrategyHuman;
import strategy.StrategyRandom;
import utils.AgentAction;
import utils.ColorSnake;
import utils.FeaturesItem;
import utils.FeaturesSnake;
import utils.ItemType;
import utils.Message;
import utils.Position;
import agent.Snake;

public class SnakeGame extends Game {

	/// A revoir
	public static int timeInvincible = 20;
	public static int timeSick = 20;

	double probSpecialItem = 1;

	private ArrayList<FeaturesSnake> start_snakes;
	private ArrayList<FeaturesItem> start_items;

	private ArrayList<Snake> snakes;
	private ArrayList<Item> items;

	InputMap inputMap;

	private AgentAction inputMoveHuman1;

	private int sizeX;
	private int sizeY;

	private User user;

	private String gameTimestamp;

	public SnakeGame(int maxTurn, int serverPort, String layout) {
		super(maxTurn, serverPort);
		try {
			this.inputMap = new InputMap(layout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.inputMoveHuman1 = AgentAction.MOVE_DOWN;
	}

	@Override
	public void initializeGame() {
		snakes = new ArrayList<Snake>();
		items = new ArrayList<Item>();

		this.walls = inputMap.get_walls().clone();

		start_snakes = inputMap.getStart_snakes();
		start_items = inputMap.getStart_items();

		this.sizeX = inputMap.getSizeX();
		this.sizeY = inputMap.getSizeY();

		String levelAISnake = "Advanced";
		SnakeFactory snakeFactory = new SnakeFactory();

		for (int i = 0; i < start_snakes.size(); i++) {
			if (i == 0) {
				snakes.add(snakeFactory.createSnake(start_snakes.get(0), "Human"));
			} else {
				snakes.add(snakeFactory.createSnake(start_snakes.get(i), levelAISnake));
			}
		}
		for (FeaturesItem featuresItem : start_items) {
			items.add(new Item(featuresItem.getX(), featuresItem.getY(), featuresItem.getItemType()));
		}

		this.gameTimestamp = LocalDateTime.now().toString();
	}

	@Override
	public void takeTurn() {

		Message command = new Message();
		command.setAction("turn");
		command.setUser(user);

		ListIterator<Snake> iterSnakes = snakes.listIterator();

		while (iterSnakes.hasNext()) {
			Snake snake = iterSnakes.next();
			AgentAction agentAction = snake.play(this);
			command.setSnakeMove(agentAction);
			command.setSnake(snake);

			Message res = sendCommand(command);
			snake.move(res.getSnakeMove(), this);
		}

		command = new Message();
		command.setAction("updateTurn");
		command.setMap(inputMap);
		command.setSnakes(snakes);
		command.setItems(items);
		command.setUser(user);

		Message res = sendCommand(command);

		snakes = res.getSnakes();
		items = res.getItems();

		for (Snake snake : snakes) {
			if (snake.getId() == 0) {
				snake.setStrategy(new StrategyHuman());
			} else {
				snake.setStrategy(new StrategyRandom());
			}
		}

	}

	@Override
	public boolean gameContinue() {

		if (snakes.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void gameOver() {
		System.out.println("Game over");
		saveScore(status);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	private boolean walls[][];

	public boolean[][] getWalls() {
		return walls;
	}

	public AgentAction getInputMoveHuman1() {
		return inputMoveHuman1;
	}

	public void setInputMoveHuman1(AgentAction inputMoveHuman1) {
		this.inputMoveHuman1 = inputMoveHuman1;
	}

	public ArrayList<Snake> getSnakes() {
		return snakes;
	}

	public void setSnakes(ArrayList<Snake> snakes) {
		this.snakes = snakes;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public boolean login(String login, String password) {
		Message message = new Message();
		message.setAction("login");
		message.setUser(new User("", login, password));

		Message res = sendCommand(message);

		if (res.getStatusCode() == 200) {
			this.user = res.getUser();
			this.user.setLogged(true);
			snakes.get(0).setSkin(this.user.getSkin());
			notifyObservers();
			return true;
		} else
			return false;
	}

	public boolean isUserLogged() {
		if (user != null) {
			return user.isLogged();
		} else {
			return false;
		}
	}

	public InputMap getInputMap() {
		return this.inputMap;
	}

	public String getStatus() {
		return this.status;
	}

	public void saveScore(String endStatus) {
		Message message = new Message();
		message.setAction("saveScore");
		message.setUser(user);
		message.setLayout(this.inputMap.getFilename());
		message.setTurn(turn);
		message.setMaxTurn(maxTurn);
		message.setTime(time);
		message.setMessage(endStatus);
		message.setTimestamp(gameTimestamp);
		message.setSnake(snakes.get(0));

		Message res = sendCommand(message);
		if (res.getStatusCode() == 200) {
			System.out.println("partie enregistrée");
		}
	}

}
