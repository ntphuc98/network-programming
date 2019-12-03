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

public class SequentialServer {

	public static void main(String[] args) {
		Map<String, String> users = new HashMap<>();
		users.put("ntphuc", "123456");
		users.put("ntphuc98", "123456");

		// Tuần tự
		try {
			// Tao SocketServer
			ServerSocket ss = new ServerSocket(8000);
			System.out.println("Tạo server thành công");
			while (true) {
				// listening
				try {
					Socket s = ss.accept();
					DataInputStream dis = new DataInputStream(s.getInputStream());
					DataOutputStream dos = new DataOutputStream(s.getOutputStream());
					// menu
					String menu = "Nhập lựa chọn:\n 1. Đăng nhập: \n 2. Đăng ký:";
					dos.writeUTF(menu);
					// get request : luachon
					String luachon = dis.readUTF();

					// check option
					if (luachon.equals("1")) {
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
					} else if (luachon.equals("2")) {
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
					} else {
						dos.writeUTF("Lựa chọn không hợp lệ!");
					}

				} catch (IOException e) {
					System.out.println("Lỗi kết nối");
				}
			}
		} catch (IOException e) {
			System.out.println("Không thể tạo ServerSocket");
			e.printStackTrace();
		}
	}

}
