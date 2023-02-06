package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Game extends Observable implements Runnable {

	int turn;
	int maxTurn;
	boolean isRunning;
	Thread thread;
	long time = 100;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;

	public Game(int maxTurn) {
		this.maxTurn = maxTurn;
		try {
			// Obtention ip local
			InetAddress ip = InetAddress.getByName("localhost");
			// Créationde la connexion au server sur le port 55555
			this.socket = new Socket(ip, 55555);
			// Déclaration des Input/Output Streams
			this.dis = new DataInputStream(this.socket.getInputStream());
			this.dos = new DataOutputStream(this.socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
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

	public HashMap<String,Object> sendCommand(Map<String, Object> command){
		try {
			// Envoie d'un message au server
			System.out.println(this.dis.readUTF());
			this.dos.writeUTF(new ObjectMapper().writeValueAsString(command));
			// Retour du message server
			return (HashMap<String,Object>) new ObjectMapper().readValue(dis.readUTF(), HashMap.class);
		} catch (EOFException e) {
			System.out.println("Le server est indisponible");
			this.gameOver();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void closeConnection() {
		// Fermeture de la connexion et des ressources
		try {
			System.out.println("Fermeture de la connexion : " + socket);
			this.socket.close();
			System.out.println("Connexion terminée");
			dis.close();
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
