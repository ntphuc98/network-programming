package server;

import java.io.IOException;
import java.net.SocketException;

public class Main {

	public static void main(String[] args) {
		Server server;
		try {
			server = new Server();
			System.out.println("Server đang hoạt động!");
			while(true) {
				server.start();
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
