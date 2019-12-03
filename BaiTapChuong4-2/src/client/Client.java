package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String args[]) {
    	Scanner sc = new Scanner(System.in);
    	String rp;
    	
        try {
            Socket s = new Socket("localhost", 8000);
            System.out.println("Client da ket noi den Server");

			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			
            while (true) {
            	rp = dis.readUTF();
                System.out.println(rp);
                
                String str = sc.nextLine();
                dos.writeUTF(str);
                
                rp = dis.readUTF();
                System.out.println(rp);
                if (str.equals("q")) {
                    break;
                }
            }
            s.close();
            System.out.println("Client đã ngắt kết nối!");
        } catch (IOException e) {
            System.out.print("Loi: Khong tao duoc socket " + e);
        }
    }
}
