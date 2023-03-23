package model_serv;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import agent_serv.Snake;
import exceptions.UserNotLoggedException;
import item_serv.Item;
import utils_serv.AgentAction;
import utils_serv.ItemType;
import utils_serv.Message;
import utils_serv.Position;

public class GameValidator {

	int timeInvincible = 20;
	int timeSick = 20;
	public double probSpecialItem = 1;

	Snake snake;
	AgentAction action;
	ArrayList<Snake> snakes;
	ArrayList<Item> items;
	InputMap map;
	User user;

	public GameValidator(Message data) {
		this.snake = data.getSnake();
		this.snakes = data.getSnakes();
		this.items = data.getItems();
		this.map = data.getMap();
		this.action = data.getSnakeMove();
		this.user = data.getUser();
	}

	public boolean isLegalMove() {
		if (snake.getSize() > 1) {
			if (snake.getLastAction() == AgentAction.MOVE_DOWN && action == AgentAction.MOVE_UP) {
				return false;
			} else if (snake.getLastAction() == AgentAction.MOVE_UP && action == AgentAction.MOVE_DOWN) {
				return false;
			} else if (snake.getLastAction() == AgentAction.MOVE_LEFT && action == AgentAction.MOVE_RIGHT) {
				return false;
			} else if (snake.getLastAction() == AgentAction.MOVE_RIGHT && action == AgentAction.MOVE_LEFT) {
				return false;
			}
		}
		return true;
	}

	public void checkMove() {
		if (!this.isLegalMove()) {
			action = snake.getLastAction();
		}
	}

	public void checkSnakeEaten() {
		for (Snake snake1 : snakes) {
			if (snake1.getInvincibleTimer() < 1) {
				for (Snake snake2 : snakes) {
					int x2 = snake2.getPositions().get(0).getX();
					int y2 = snake2.getPositions().get(0).getY();

					if (snake1.getId() != snake2.getId()) {
						if (x2 == snake1.getPositions().get(0).getX() && y2 == snake1.getPositions().get(0).getY()) {
							if (snake1.getSize() <= snake2.getSize()) {
								snake1.setToRemove(true);
							}
						}
					}

					for (int i = 1; i < snake1.getPositions().size(); i++) {
						if (x2 == snake1.getPositions().get(i).getX() && y2 == snake1.getPositions().get(i).getY()) {
							snake1.setToRemove(true);
						}
					}
				}
			}
		}
	}

	public void checkWalls() {
		for (Snake snake1 : snakes) {
			if (snake1.getInvincibleTimer() < 1) {
				int x = snake1.getPositions().get(0).getX() % map.get_walls().length;
				int y = snake1.getPositions().get(0).getY() % map.get_walls()[0].length;
				if (map.get_walls()[x][y]) {
					snake1.setToRemove(true);
				}
			}
		}
	}

	public void removeSnake() {

		ListIterator<Snake> iterSnake = snakes.listIterator();

		while (iterSnake.hasNext()) {
			Snake snake = iterSnake.next();
			if (snake.isToRemove()) {
				iterSnake.remove();
			}
		}
	}

	public void updateSnakeTimers() {
		ListIterator<Snake> iter = snakes.listIterator();
		while (iter.hasNext()) {
			Snake snake = iter.next();
			if (snake.getInvincibleTimer() > 0) {
				snake.setInvincibleTimer(snake.getInvincibleTimer() - 1);
			}
			if (snake.getSickTimer() > 0) {
				snake.setSickTimer(snake.getSickTimer() - 1);
			}
		}
	}

	public void addRandomApple() {
		boolean notPlaced = true;
		while (notPlaced) {
			Random rand = new Random();
			int x = rand.nextInt(map.getSizeX());
			int y = rand.nextInt(map.getSizeY());
			if (!map.get_walls()[x][y] & !this.isSnake(x, y) & !this.isItem(x, y)) {
				items.add(new Item(x, y, ItemType.APPLE));
				notPlaced = false;
			}
		}
	}

	public void addRandomItem() {

		Random rand = new Random();
		int r = rand.nextInt(3);
		ItemType itemType = null;

		if (r == 0) {
			itemType = ItemType.BOX;
		} else if (r == 1) {
			itemType = ItemType.SICK_BALL;
		} else if (r == 2) {
			itemType = ItemType.INVINCIBILITY_BALL;
		}

		boolean notPlaced = true;
		while (notPlaced) {
			int x = rand.nextInt(map.getSizeX());
			int y = rand.nextInt(map.getSizeY());
			if (!map.get_walls()[x][y] & !this.isSnake(x, y) & !this.isItem(x, y)) {
				items.add(new Item(x, y, itemType));
				notPlaced = false;
			}
		}
	}

	public boolean isSnake(int x, int y) {
		for (Snake snake : snakes) {
			for (Position pos : snake.getPositions()) {
				if (pos.getX() == x & pos.getY() == y) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isItem(int x, int y) {
		for (Item item : items) {
			if (item.getX() == x & item.getY() == y) {
				return true;
			}
		}
		return false;
	}

	public boolean checkItemFound() {
		ListIterator<Item> iterItem = items.listIterator();
		boolean isAppleEaten = false;
		while (iterItem.hasNext()) {
			Item item = iterItem.next();
			for (Snake snake : snakes) {
				if (snake.getSickTimer() < 1) {
					int x = snake.getPositions().get(0).getX();
					int y = snake.getPositions().get(0).getY();
					if (item.getX() == x & item.getY() == y) {
						iterItem.remove();
						if (item.getItemType() == ItemType.APPLE) {
							snake.sizeIncrease();
							isAppleEaten = true;
						}
						if (item.getItemType() == ItemType.BOX) {
							Random rand = new Random();
							double r = rand.nextDouble();
							if (r < 0.5) {
								snake.setInvincibleTimer(timeInvincible);
							} else {
								snake.setSickTimer(timeSick);
							}
						}
						if (item.getItemType() == ItemType.SICK_BALL) {
							snake.setSickTimer(timeSick);
						}
						if (item.getItemType() == ItemType.INVINCIBILITY_BALL) {
							snake.setInvincibleTimer(timeInvincible);
						}
					}
				}
			}
		}
		return isAppleEaten;
	}

	public Message getResult() {
		Message result = new Message();

		result.setSnakeMove(action);
		result.setSnake(snake);
		result.setSnakes(snakes);
		result.setItems(items);

		return result;
	}

	public void isAuthenticated() throws UserNotLoggedException {
		if(user != null){
			if(!user.getToken().equals("token_api")){
				throw new UserNotLoggedException("Le token fourni n'est pas autorisé");
			}
		} else {
			throw new UserNotLoggedException("Aucun utilisateur fourni");
		}
	}
}
