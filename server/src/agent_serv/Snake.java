package agent_serv;

import java.util.ArrayList;

import strategy.Strategy;
import utils_serv.AgentAction;
import utils_serv.ColorSnake;
import utils_serv.Position;

public class Snake {

	ArrayList<Position> positions;

	private AgentAction lastAction;

	private int invincibleTimer;
	private int sickTimer;

	int oldTailX = -1;
	int oldTailY = -1;

	private int id;
	
	boolean toRemove;
	
	ColorSnake colorSnake;

	public Snake(){}

	public int getSize() {
		
		return this.positions.size();
	}

	public ArrayList<Position> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}

	public int getX() {
		return this.positions.get(0).getX();
	}

	public int getY() {
		return this.positions.get(0).getY();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isToRemove() {
		return toRemove;
	}


	public void setToRemove(boolean toRemove) {
		this.toRemove = toRemove;
	}

	public ColorSnake getColorSnake() {
		return colorSnake;
	}

	public void setColorSnake(ColorSnake colorSnake) {
		this.colorSnake = colorSnake;
	}

	public int getInvincibleTimer() {
		return invincibleTimer;
	}

	public void setInvincibleTimer(int invincibleTimer) {
		this.invincibleTimer = invincibleTimer;
	}

	public int getSickTimer() {
		return sickTimer;
	}

	public void setSickTimer(int sickTimer) {
		this.sickTimer = sickTimer;
	}

	public AgentAction getLastAction() {
		return lastAction;
	}

	public void setLastAction(AgentAction lastAction) {
		this.lastAction = lastAction;
	}

	public void sizeIncrease() {
		this.positions.add(new Position(this.oldTailX, this.oldTailY));
	}
}
