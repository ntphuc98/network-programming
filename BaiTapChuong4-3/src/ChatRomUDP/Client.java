package ChatRomUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.xml.crypto.Data;


public class Client {
	private InetAddress host;
	private int port;
	
	public Client(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}
	
private void execute() throws IOException{
//	tao socket
		DatagramSocket client = new DatagramSocket();
	// phan nhap ten
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhap ten : ");
		String name = sc.nextLine();
		client.send(createPacket(name + " da tham gia vao phong"));

		ReadClient read = new ReadClient(client);
		read.start();
		WriteClient write = new WriteClient(client, host, port, name);
		write.start();
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client(InetAddress.getLocalHost(), 8000);
		client.execute();
	}
	
	private DatagramPacket createPacket(String value) {
		byte[] arrData = value.getBytes();
		return new DatagramPacket(arrData, arrData.length,host,port);
	}
}
class ReadClient extends Thread{
	private DatagramSocket client;
	
	public ReadClient(DatagramSocket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				String sms = recieveData(client);
				System.out.println(sms);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private String recieveData(DatagramSocket client) throws IOException {
		byte[] temp = new byte[1024];
		DatagramPacket recieve_Packet = new DatagramPacket(temp, temp.length);
		client.receive(recieve_Packet);
		return new String(recieve_Packet.getData()).trim();
	}
}

class WriteClient extends Thread{
	private DatagramSocket client;
	private InetAddress host;
	private int port;
	private String name;
	
	public WriteClient(DatagramSocket client, InetAddress host, int port,String name) {
		this.client = client;
		this.host = host;
		this.port = port;
		this.name = name;
	}
	
	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			try {
				String sms = sc.nextLine();
				DatagramPacket DP = createPacket(name + ": "+sms); // dong goi chuan bi gui di
				client.send(DP);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private DatagramPacket createPacket(String value) {
		byte[] arrData = value.getBytes();
		return new DatagramPacket(arrData, arrData.length,host,port);
	}
}
