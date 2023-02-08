
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

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
        HashMap<String, Object> received;
        HashMap<String, Object> toreturn = new HashMap<String, Object>();
        while (true) {
            try {
                // Réception de la commande du client
                received = (HashMap<String, Object>) mapper.readValue(br.readLine(),Object.class);

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
                        dos.writeUTF(mapper.writeValueAsString(toreturn));
                        break;
                    case "login":
                        User user = new User("test", "test@test.fr", "test");
                        toreturn.put("user", user);
                        toreturn.put("status_code", "200");


                        LoginService.login(apiService, received, mapper);

                        dos.writeUTF(mapper.writeValueAsString(toreturn));
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
            this.br.close();
            this.dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
