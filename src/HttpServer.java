import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public final static int serverPort=8080;
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(serverPort);
            System.out.println("Server started...listening on Port : "+serverPort);
            while(true){
                Socket clientSocket = socket.accept();
                RequestHandler rH = new RequestHandler(clientSocket);
                rH.start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
