
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import controller.ControllerSnakeGame;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scn = new Scanner(System.in);

            // Obtention ip local
            InetAddress ip = InetAddress.getByName("localhost");

            // Créationde la connexion au server sur le port 55555
            Socket socket = new Socket(ip, 55555);

            // Déclaration des Input/Output Streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            ControllerSnakeGame controllerSnakeGame = new ControllerSnakeGame();

            // Lancement de la boucle du client
            while (true) {
                System.out.println(dis.readUTF());
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);

                // Sortie de la boucle quand le client tape exit
                if (tosend.equals("exit")) {
                    System.out.println("Fermeture de la connexion : " + socket);
                    socket.close();
                    System.out.println("Connexion terminée");
                    break;
                }
                // Exemple d'affichage de la date ou de l'heure lorsque le client le demande
                String received = dis.readUTF();
                System.out.println(received);
            }
            // Fermeture des ressources
            scn.close();
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}