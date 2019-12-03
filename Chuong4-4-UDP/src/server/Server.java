package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Server {
	private final int PORT = 8000; // Cổng mặc định của Server
	private static Map<String, String> users;
	private InetAddress inetAddress;
	private int portClient;
	private DatagramPacket incoming;
	private DatagramPacket dp;
	private DatagramSocket ds;
	private String msgRp;

	public Server() throws SocketException {
		ds = new DatagramSocket(PORT);
		users = new HashMap<>();
		users.put("ntphuc", "123456");
		users.put("ntphuc98", "123456");
	}

	public void start() throws IOException {
		byte[] buffer = new byte[6000];
		incoming = new DatagramPacket(buffer, buffer.length);
		ds.receive(incoming);
		
		inetAddress = incoming.getAddress();
		portClient = incoming.getPort();
		
		String msg = new String(incoming.getData(), 0, incoming.getLength());
		switch (msg) {
		case "1":
			login();
			break;
		case "2":
			register();
			break;
		case "q":
			break;
		default:
			responseDefault();
			break;
		}
	}

	public void login() {
		try {
			while (true) {
				msgRp = "Nhập username: ";
				byte[] data = msgRp.getBytes();
				dp = new DatagramPacket(data, data.length, inetAddress, portClient);
				ds.send(dp);
				
				byte[] buffer = new byte[6000];
				incoming = new DatagramPacket(buffer, buffer.length);
				ds.receive(incoming);
				String username = new String(incoming.getData(), 0, incoming.getLength());
				
				if (checkUsername(username)) {
					msgRp = "Nhập password: ";
					data = msgRp.getBytes();
					dp = new DatagramPacket(data, data.length, inetAddress, portClient);
					ds.send(dp);
					
					ds.receive(incoming);
					String password = new String(incoming.getData(), 0, incoming.getLength());
					
					if (checkPassword(username, password)) {
						msgRp = "Đăng nhập thành công!";
						data = msgRp.getBytes();
						dp = new DatagramPacket(data, data.length, inetAddress, portClient);
						ds.send(dp);
						
						break;
					} else {
						msgRp = "Password không chính xác!";
						data = msgRp.getBytes();
						dp = new DatagramPacket(data, data.length, inetAddress, portClient);
						ds.send(dp);
					}
				} else {
					msgRp = "Username không tồn tại!";
					data = msgRp.getBytes();
					dp = new DatagramPacket(data, data.length, inetAddress, portClient);
					ds.send(dp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void register() {
		try {
			while (true) {
				msgRp = "Nhập username: ";
				byte[] data = msgRp.getBytes();
				dp = new DatagramPacket(data, data.length, inetAddress, portClient);
				ds.send(dp);
				
				byte[] buffer = new byte[6000];
				incoming = new DatagramPacket(buffer, buffer.length);
				ds.receive(incoming);
				String username = new String(incoming.getData(), 0, incoming.getLength());
				if (!checkUsername(username)) {
					msgRp = "Nhập password: ";
					data = msgRp.getBytes();
					dp = new DatagramPacket(data, data.length, inetAddress, portClient);
					ds.send(dp);
					ds.receive(incoming);
					String password = new String(incoming.getData(), 0, incoming.getLength());

					// add user
					users.put(username, password);
					msgRp = "Đăng ký thành công!";
					data = msgRp.getBytes();
					dp = new DatagramPacket(data, data.length, inetAddress, portClient);
					ds.send(dp);
					break;
				} else {
					msgRp = "Username đã tồn tại!";
					data = msgRp.getBytes();
					dp = new DatagramPacket(data, data.length, inetAddress, portClient);
					ds.send(dp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean checkUsername(String username) {
		username.trim();
		if (users.containsKey(username))
			return true;
		return false;
	}

	boolean checkPassword(String username, String password) {
		if (users.get(username).equals(password))
			return true;
		return false;
	}

	public void responseDefault() {
		msgRp = "Lựa chọn không hợp lệ !";
		byte[] data = msgRp.getBytes();
		dp = new DatagramPacket(data, data.length, inetAddress, portClient);
		try {
			ds.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
