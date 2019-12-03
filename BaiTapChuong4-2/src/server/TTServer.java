package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TTServer {

	public final static int defaultPort = 8000;
	public static int clientNo = 0;

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			ServerSocket ss = new ServerSocket(defaultPort);
			System.out.println("Server đã được tạo !");
			while (true) {
				try {
					Socket s = ss.accept();
					clientNo++;
					System.out.println("Client " + clientNo + " đã kết nối ... ");

					DataInputStream dis = new DataInputStream(s.getInputStream());
					DataOutputStream dos = new DataOutputStream(s.getOutputStream());

					while (true) {
						String rp = "Nhập chuỗi dạng 'OP Operant1 Operant2' - nhập q để thoát: ";
						dos.writeUTF(rp);

						String rq = dis.readUTF();

						if (rq.equals("q")) {
							dos.writeUTF("Yêu cầu ngắt kết nối!");
							break;
						} else {
							String r = calculateTwoNumbers(rq);
							dos.writeUTF(r);
						}
					}
					s.close();
					System.out.println("Client " + clientNo + " đã ngắt kết nối ! ");
					System.out.println("-------------------------------");
				} catch (IOException e) {
					System.err.println(" Connection Error: " + e);
				}
			}
		} catch (IOException e) {
			System.err.println(" Server Creation Error:" + e);
		}
	}

	public static String calculateTwoNumbers(String str) {
		str = str.trim();
		if (str.length() == 0) {
			return "Vui lòng nhập chuỗi!";
		}
		
		String[] input = str.split(" ");
		if (input.length != 3) {
			return "Chuỗi không hợp lệ";
		}
		char p = input[0].charAt(0);
		int a, b;
		try {
			a = Integer.parseInt(input[1]);
			b = Integer.parseInt(input[2]);
		}catch(NumberFormatException e) {
			return "Chuỗi không hợp lệ";
		}
		switch (p) {
		case '+':
			return Integer.toString((a + b));
		case '-':
			return Integer.toString((a - b));
		case '*':
			return Integer.toString((a * b));
		case '/':
			float rs = (float) a / (float) b;
			return Float.toString(rs);
		default:
			return "Các toán tử hợp lệ: '+', '-', '*', '/'.";
		}

	}
}
