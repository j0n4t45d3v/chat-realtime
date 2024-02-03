import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

public class ServerChat {
    private static Logger logger = Logger.getLogger(ServerChat.class.getName());

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(Constants.PORT);
            logger.info(String.format("Server started on port %d", Constants.PORT));

            Socket client = serverSocket.accept();
            Scanner inClient = new Scanner(client.getInputStream());
            PrintWriter outClient = new PrintWriter(client.getOutputStream(), true);
            String identifyClient = inClient.nextLine();
            logger.info(String.format("Client %s connected", identifyClient));
            outClient.printf("Bem vindo ao Chat, %s%n", identifyClient);
            receiveMessage(inClient, identifyClient, outClient);
            inClient.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void receiveMessage(Scanner inClient, String identifyClient, PrintWriter outClient) {
        while (inClient.hasNextLine()) {
            String message = inClient.nextLine();
            logger.info(message);
            if(!identifyClient.equals(message)){
                if(message.equals("exit")) {
                    outClient.printf("%s disconnected%n", identifyClient.toString());
                    logger.info(String.format("Client %s disconnected%n", identifyClient.toString()));
                }
                outClient.printf("%s: %s%n", identifyClient, message);
            }
        }
    }

    private static void sendMessage(Scanner keyboard, PrintStream outClient, String identifyClient) {
        while (true){
            String message = keyboard.nextLine();

            if(message.equals("exit")) {
                outClient.printf("Client %s disconnected%n", identifyClient.toString());
                break;
            }
            outClient.println(message);
        }
    }
}