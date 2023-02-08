
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import com.fasterxml.jackson.databind.ObjectMapper;

import route.Router;
import service.ApiService;
import utils.Message;

public class ClientHandler extends Thread {
    final BufferedReader br;
    final DataOutputStream dos;
    final Socket socket;
    final ApiService apiService;
    final ObjectMapper mapper;

    // Constructor
    public ClientHandler(Socket socket, BufferedReader br, DataOutputStream dos) {
        this.socket = socket;
        this.br = br;
        this.dos = dos;
        this.apiService = new ApiService("http://localhost:8080/api");
        this.mapper = new ObjectMapper();
    }

    @Override
    public void run() {
        Message received;
        while (true) {
            try {
                // Réception de la commande du client
                received = mapper.readValue(br.readLine(),Message.class);

                try{
                    received.getAction();
                }catch (NullPointerException e){
                    received.setAction("noActionProvided");
                }

                if (received.getAction().equals("logout")) {
                    // TODO : Utilisation d'un token fourni pour chaque client pour l'identifier auprès de l'api une fois connecté
                    System.out.println("Le client " + this.socket + " demande la fermeture de la connexion");
                    System.out.println("Fermeture de la connexion sur le server");
                    this.socket.close();
                    System.out.println("Connexion terminée");
                    break;
                }
                
                dos.writeUTF(mapper.writeValueAsString(Router.route(apiService, received)));

            } catch (SocketException e) {
                System.out.println("Connexion avec le client " + socket + " interrompue");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // Fermeture des ressources
            this.br.close();
            this.dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
