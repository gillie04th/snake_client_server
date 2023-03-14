package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.Message;

public abstract class Game extends Observable implements Runnable {

	int turn;
	int maxTurn;
	boolean isRunning;
	Thread thread;
	long time = 100;
	Socket socket;
	int serverPort;
	DataInputStream in;
	PrintWriter out;
	ObjectMapper mapper;
	String status;

	public Game(int maxTurn, int serverPort) {
		this.maxTurn = maxTurn;
		this.mapper = new ObjectMapper();
		this.serverPort = serverPort;
	}

	public void serverConnection() {
		while (!openConnection()) {
			try {
				System.out.println("Connexion impossible, nouvel tentative dans 10 secondes ...");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean openConnection() {
		// Déclaration des Input/Output Streams
		try {
			// Obtention ip local
			InetAddress ip = InetAddress.getByName("localhost");
			// Créationde la connexion au server sur le port fourni
			this.socket = new Socket(ip, serverPort);
			this.in = new DataInputStream(this.socket.getInputStream());
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void init() {
		this.turn = 0;
		isRunning = true;
		initializeGame();
		setChanged();
		notifyObservers();
	}

	public void step() {
		if (this.gameContinue() & turn < maxTurn) {
			turn++;
			takeTurn();
		} else {
			isRunning = false;
			status = "Fin du temps imparti";
			gameOver();
		}
		setChanged();
		notifyObservers();
	}

	public void run() {
		while (isRunning == true) {
			step();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void pause() {
		isRunning = false;
	}

	public void launch() {
		isRunning = true;
		this.thread = new Thread(this);
		this.thread.start();
	}

	public abstract void initializeGame();

	public abstract void takeTurn();

	public abstract boolean gameContinue();

	public abstract void gameOver();

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getTurn() {
		return turn;
	}

	public Message sendCommand(Message command) {

		try {
			// Envoie d'un message au server
			this.out.println(mapper.writeValueAsString(command));
			// Retour du message server
			return mapper.readValue(in.readUTF(), Message.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Le serveur est indisponible");
			serverConnection();
		}

		return null;
	}

	public void closeConnection() {
		// Fermeture de la connexion et des ressources
		try {
			System.out.println("Fermeture de la connexion : " + socket);
			this.socket.close();
			System.out.println("Connexion terminée");
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
