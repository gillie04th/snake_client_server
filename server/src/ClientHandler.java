
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;
    final ApiService apiService;

    // Constructor
    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
        this.apiService = new ApiService("http://localhost:8080/api");
    }

    @Override
    public void run() {
        HashMap<String, Object> received;
        HashMap<String, Object> toreturn = new HashMap<String, Object>();
        while (true) {
            try {
                // Réception de la commande du client
                dos.writeUTF("Attente de commande ...");
                received = (HashMap<String, Object>) new ObjectMapper().readValue(dis.readUTF(),Object.class);

                if (received.equals("exit")) {
                    // TODO : Utilisation d'un token fourni pour chaque client pour l'identifier auprès de l'api une fois connecté
                    System.out.println("Le client " + this.socket + " demande la fermeture de la connexion");
                    System.out.println("Fermeture de la connexion sur le server");
                    this.socket.close();
                    System.out.println("Connexion terminée");
                    break;
                }

                // Envoie de la réponse selon la commande du client
                System.out.println(received.get("action"));
                switch ((String) received.get("action")) {
                    case "logout":
                        toreturn.put("logout", "Vous êtes déconnecté");
                        dos.writeUTF(new ObjectMapper().writeValueAsString(toreturn));
                        break;
                    case "login":
                        toreturn.put("login", "Vous êtes connecté");
                        dos.writeUTF(new ObjectMapper().writeValueAsString(toreturn));
                        break;
                    default:
                        dos.writeUTF("Commande invalide");
                        break;
                }

            } catch (SocketException e) {
                System.out.println("Connexion avec le client " + socket + " interrompue");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // Fermeture des ressources
            this.dis.close();
            this.dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
