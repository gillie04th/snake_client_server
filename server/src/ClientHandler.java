
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread {
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;

    // Constructor
    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        while (true) {
            try {
                // Réception de la commande du client
                dos.writeUTF("Attente de commande ...");
                received = dis.readUTF();

                if (received.equals("exit")) {
                    System.out.println("Le client " + this.socket + " demande la fermeture de la connexion");
                    System.out.println("Fermeture de la connexion sur le server");
                    this.socket.close();
                    System.out.println("Connexion terminée");
                    break;
                }

                // Envoie de la réponse selon la commande du client
                switch (received) {
                    case "logout":
                        toreturn = "logout";
                        dos.writeUTF(toreturn);
                        break;
                    case "login":
                        toreturn = "login";
                        dos.writeUTF(toreturn);
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
