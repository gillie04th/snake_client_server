package utils;

import model.InputMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

public class Message {
    
    private FeaturesItem fi;
    private FeaturesSnake fs;
    private InputMap map;
    private User user;
    private int statusCode;
    private String message;
    private String action;
    private String layout;
    private int turn;
    private int maxTurn;
    private long time;

    public Message(){}

    public FeaturesItem getFi() {
        return this.fi;
    }

    public void setFi(FeaturesItem fi) {
        this.fi = fi;
    }

    public FeaturesSnake getFs() {
        return this.fs;
    }

    public void setFs(FeaturesSnake fs) {
        this.fs = fs;
    }

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
    
    public String toJson(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}