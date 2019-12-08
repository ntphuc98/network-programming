package atm;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ATMMain {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String input;
		ResponseProcessing rp = null;
		try {
			System.out.println("ATM đang hoạt động!");
			Socket socket = new Socket("localhost", 8000);
			rp = new ResponseProcessing(socket);
			rp.start();
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			while (true) {
				input = sc.nextLine();
				if (input.equals("q")) {
					System.out.println("Kết thúc chương trình!");
					break;
				}
				dos.writeUTF(input);
			}
		} catch (IOException ie) {
			System.out.print("Không thể kết nối!");
		}
		sc.close();
		rp.stop();
	}
}