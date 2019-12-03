package ChatRomUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {
	private int port;
	private InetAddress clientIP;
	private int clientPort;
	public static Map<DatagramPacket, Integer> listSK;

	public Server(int port) {
		this.port = port;
	}

	private void execute() throws IOException {

//		tao socket
		DatagramSocket server = new DatagramSocket(port);
		WriteServer write = new WriteServer(server);
		write.start();
		
		System.out.println("Server is listening...");
		while (true) {
			String sms = recieveData(server);
			for (DatagramPacket item : listSK.keySet()) {
				if (!(item.getAddress().equals(clientIP) && item.getPort() == clientPort)) {
					sendData(sms, server, item.getAddress(), item.getPort());
				}
			}
			System.out.println(sms);
		}
	}

	public static void main(String[] args) throws IOException {
		Server.listSK = new HashMap<DatagramPacket, Integer>();
		Server server = new Server(8000);
		server.execute();
	}

	private String recieveData(DatagramSocket server) throws IOException {
		byte[] temp = new byte[1024];
		DatagramPacket recieve_Packet = new DatagramPacket(temp, temp.length);
		server.receive(recieve_Packet);
		clientIP = recieve_Packet.getAddress();
		clientPort = recieve_Packet.getPort();

		checkDuplicate(recieve_Packet); // kiem tra trung Packet trong mang Map
		return new String(recieve_Packet.getData()).trim();
	}

	private void checkDuplicate(DatagramPacket packet) {
		for (DatagramPacket item : listSK.keySet()) {
			if (item.getAddress().equals(packet.getAddress()) && item.getPort() == packet.getPort()) {
				listSK.replace(item, 0);
				return; // tim thay trung thi out
			}
		}
		listSK.put(packet, 0);
	}

	private void sendData(String value, DatagramSocket server, InetAddress clientIP, int clientPort)
			throws IOException {
		byte[] temp = new byte[1024];
		temp = value.getBytes();
		DatagramPacket send_result_Packet = new DatagramPacket(temp, temp.length, clientIP, clientPort);
		server.send(send_result_Packet);
	}

}

class WriteServer extends Thread {
	private DatagramSocket server;

	public WriteServer(DatagramSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String sms = sc.nextLine();
			try {
				for (DatagramPacket item : Server.listSK.keySet()) {
					sendData("Sever: " + sms, server, item.getAddress(), item.getPort());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void sendData(String value, DatagramSocket server, InetAddress clientIP, int clientPort)
			throws IOException {
		byte[] temp = new byte[1024];
		temp = value.getBytes();
		DatagramPacket send_result_Packet = new DatagramPacket(temp, temp.length, clientIP, clientPort);
		server.send(send_result_Packet);
	}
}