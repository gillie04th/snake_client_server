package utils_serv;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import agent_serv.Snake;
import item_serv.Item;
import model_serv.InputMap;
import model_serv.User;

public class Message {
    
    private InputMap map;
    private User user;
    private int statusCode;
    private String message;
    private String action;
    private String layout;
    private int turn;
    private int maxTurn;
    private long time;
    private String timestamp;
    private Snake snake;
    private ArrayList<Snake> snakes;
    private ArrayList<Item> items;
    private AgentAction snakeMove;

    public Message(){}

    public InputMap getMap() {
        return this.map;
    }

    public void setMap(InputMap map) {
        this.map = map;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLayout() {
        return this.layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }
    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getMaxTurn() {
        return this.maxTurn;
    }

    public void setMaxTurn(int maxTurn) {
        this.maxTurn = maxTurn;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Snake getSnake() {
        return this.snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public AgentAction getSnakeMove() {
        return this.snakeMove;
    }

    public void setSnakeMove(AgentAction snakeMove) {
        this.snakeMove = snakeMove;
    }
    
    public ArrayList<Snake> getSnakes() {
        return this.snakes;
    }

    public void setSnakes(ArrayList<Snake> snakes) {
        this.snakes = snakes;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getScore(){
        if(snake!=null){
            return snake.getSize();
        }
        return 0;
    }

    public String toJson(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}