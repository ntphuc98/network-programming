package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestProcessing extends Thread {

    Socket channel;
    public int clientNo = 1;

    public RequestProcessing(Socket s) {
    	channel = s;
    }

    public void run() {
        try {
			InputStream ip = channel.getInputStream();
			DataInputStream dis = new DataInputStream(ip);
			OutputStream op = channel.getOutputStream();
			DataOutputStream dos = new DataOutputStream(op);

			while (true) {
				String rp = "Nhập chuỗi dạng 'OP Operant1 Operant2' - nhập q để thoát:";
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
			channel.close();
			System.out.println("Client " + clientNo + " đã ngắt kết nối ! ");
			System.out.println("-------------------------------");

        } catch (IOException e) {
        	
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
