package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		String username;
		String password;
		String luachon;
		String rp;
		Scanner sc = new Scanner(System.in);
		try {
			Socket s = new Socket("localhost", 8000); // Kết nối đến Server
			
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());

			if (s.isConnected()) {
				System.out.println("Kết nối thành công");
			}
			// get response
			rp = dis.readUTF();
			System.out.println(rp);
			
			// send luachon
			luachon = sc.nextLine();
			dos.writeUTF(luachon);
			
			if (luachon.equals("1")) {
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
				// send username
				username = sc.nextLine();
				dos.writeUTF(username);
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
				// send password
				password = sc.nextLine();
				dos.writeUTF(password);
				
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
			}else if(luachon.equals("2")) {
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
				// send username
				username = sc.nextLine();
				dos.writeUTF(username);
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
				// send password
				password = sc.nextLine();
				dos.writeUTF(password);
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
			}else {
				// get response
				rp = dis.readUTF();
				System.out.println(rp);
				System.out.println("Thoát!");
			}
			
			s.close();
		} catch (IOException ie) {
			System.out.println("Loi ket noi!");
		}
	}

}
