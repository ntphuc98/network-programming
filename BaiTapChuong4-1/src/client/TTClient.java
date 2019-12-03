package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TTClient {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String input;
		String rp;
		try {
			Socket s = new Socket("localhost", 8000);
			System.out.println("Client da ket noi den server");

			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			
			while(true) {
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
				// send luachon
				input = sc.nextLine();
				dos.writeUTF(input);
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
				System.out.println("----------------------------");
				if(input.equals("q")) {
					break;
				}
			}
			s.close();
		} catch (IOException ie) {
			System.out.print("Error: Khong tao duoc socket");
		}
	}
}
