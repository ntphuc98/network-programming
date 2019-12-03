package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ParallelServer {

	public static void main(String[] args) {
		try {
			// Tạo socket cho server
			ServerSocket ss = new ServerSocket(8000);
			while (true) {
				try {
					Socket s = ss.accept(); // Lắng nghe các yêu cầu kết nối
					// Tạo phần xử lý
					RequestProcessing rp = new RequestProcessing(s);
					rp.start(); // Khởi động phần xử lý cho Client hiện tại
				} catch (IOException e) {
					System.out.println("Connection Error: " + e);
				}
			}
		} catch (IOException e) {
			System.err.println("Create Socket Error: " + e);
		}
	}

}

class RequestProcessing extends Thread {
	Socket channel; // Socket của kênh ảo nối với Client hiện tại
	DataInputStream dis;
	DataOutputStream dos;
	Map<String, String> users;

	public RequestProcessing(Socket s) {
		channel = s; // Nhận socket của kênh ảo nối với Client
		users = new HashMap<>();
		users.put("ntphuc", "123456");
		users.put("ntphuc98", "123456");
	}
	public void login() {
		try {
			dos.writeUTF("Nhập username:");
			String username = dis.readUTF();

			dos.writeUTF("Nhập password:");
			String password = dis.readUTF();

			// check user
			if (users.containsKey(username) && users.get(username).equals(password)) {
				dos.writeUTF("Đăng nhập thành công!");
			} else {
				dos.writeUTF("Username hoặc password không chính xác!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void register() {
		try {
			dos.writeUTF("Nhập username:");
			String username = dis.readUTF();
			dos.writeUTF("Nhập password:");
			String password = dis.readUTF();
			if (users.containsKey(username)) {
				dos.writeUTF("Username đã tồn tại!");
			} else {
				users.put(username, password);
				dos.writeUTF("Đăng ký thành công!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			InputStream ip = channel.getInputStream();
			dis = new DataInputStream(ip);
			OutputStream op = channel.getOutputStream();
			dos = new DataOutputStream(op);
			// menu
			String menu = "Nhập lựa chọn:\n 1. Đăng nhập: \n 2. Đăng ký:";
			dos.writeUTF(menu);
			// get request : luachon
			String luachon = dis.readUTF();
			// check luachon
			if (luachon.equals("1")) {
				login();
			} else if (luachon.equals("2")) {
				register();
			} else {
				dos.writeUTF("Lựa chọn không hợp lệ!");
			}
		} catch (IOException e) {
			System.err.println("Request Processing Error: " + e);
		}
	}
}
