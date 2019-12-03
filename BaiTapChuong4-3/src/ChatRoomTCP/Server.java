package ChatRoomTCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;



public class Server {
	private int port;
	public static ArrayList<Socket> listSK;
	
	
	public Server(int port) {
		this.port = port;
	}
	
	private void execute() throws IOException {
		ServerSocket server = new ServerSocket(port);
		WriteServer write = new WriteServer();
		write.start();
		System.out.println("Server is listening...");
		while(true) {
			
			Socket socket = server.accept();
			System.out.println("Da Ket Noi Voi " + socket);
			Server.listSK.add(socket);
			ReadServer read = new ReadServer(socket);
			read.start();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server.listSK = new ArrayList<>();
		Server server = new Server(15789);
		server.execute();
	}
}

class ReadServer extends Thread{
	private Socket socket;
	
	public ReadServer(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(socket.getInputStream());
			while(true) {
				String sms = dis.readUTF();
				if(sms.contains("exit")) {
					Server.listSK.remove(socket);
					System.out.println("da ngat ket noi voi :" + socket);
					dis.close();
					socket.close();
					continue; //ngat ket noi
				}
				for (Socket item : Server.listSK) {
					if(item.getPort() != socket.getPort()) {
						DataOutputStream dos = new DataOutputStream(item.getOutputStream());
						dos.writeUTF(sms);
					}
					
				}
				System.out.println(sms);
			}
		}catch (Exception e) {
			// TODO: handle exception
			try {
//				dis.close();
				socket.close();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				System.out.println("Ngat ket noi Server");
			}
			
		}
	}
	
}
class WriteServer extends Thread{
	
	@Override
	public void run() {
		DataOutputStream dos = null;
		Scanner sc = new Scanner(System.in);
		while(true) {
			String sms = sc.nextLine();
			try {
			for (Socket item : Server.listSK) {
				dos = new DataOutputStream(item.getOutputStream());
					dos.writeUTF("Server: " + sms);
			}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
}
