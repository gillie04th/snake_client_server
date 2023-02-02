
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        // Le server écoute le port 55555
        ServerSocket serverSocket = new ServerSocket(55555);

        // Boucle pour récupérer les requêtes des clients
        while (true) {
            Socket socket = null;

            try {
                // Socket qui reçoit les requêtes des clients
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // Déclaration des Input/Output Streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // Création d'un thread pour chaque client
                Thread t = new ClientHandler(socket, dis, dos);

                // Démarre le thread
                t.start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
                break;
            }
        }
    }
}
