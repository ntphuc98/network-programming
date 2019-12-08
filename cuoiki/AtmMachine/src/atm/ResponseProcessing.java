package atm;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ResponseProcessing extends Thread{
	Socket socket;
	DataInputStream dis;
	String rp;

	public ResponseProcessing(Socket socket) {
		this.socket = socket;
		try {
			this.dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// get response
		while (true) {
			try {
				rp = dis.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(rp);
		}
	}
}
