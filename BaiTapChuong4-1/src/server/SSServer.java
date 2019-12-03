package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SSServer {

    public final static int defaultPort = 8000;
    public static int clientNo = 0;
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(defaultPort);
            System.out.println("Server đã được tạo ...");
            while (true) {
                try {
                    Socket s = ss.accept();
                    clientNo++;
                    System.out.println("Client " + clientNo + " đã kết nối!");
                    RequestProcessing rp = new RequestProcessing(s);
                    rp.start();
                    rp.clientNo = clientNo;                 
                } catch (IOException e) {
                    System.err.println(" Connection Error: " + e);
                }
            }
        } catch (IOException e) {
            System.err.println(" Server Creation Error:" + e);
        }
    }

}