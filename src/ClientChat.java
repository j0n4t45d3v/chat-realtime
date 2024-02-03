import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class ClientChat {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", Constants.PORT);
            PrintStream outClient = new PrintStream(client.getOutputStream());
            Scanner keyboard = new Scanner(System.in);
            Scanner inClient = new Scanner(client.getInputStream());
            System.out.printf("Insira um nome para se identificar no chat: ");
            String identifyClient = keyboard.nextLine();
            outClient.println(identifyClient.toUpperCase());
            String receivedMessage = inClient.nextLine();
            System.out.println(receivedMessage);
            while (true){
                String message = keyboard.nextLine();
                // Envia mensagens para o servidor
                outClient.println(message);

                // Receba mensagens do servidor
                if (inClient.hasNextLine()) {
                    receivedMessage = inClient.nextLine();
                    System.out.println(receivedMessage);
                }
                if(message.equals("exit")) {
                    break;
                }
            }
            keyboard.close();
            inClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
