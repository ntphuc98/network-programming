package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import string.IStringProcess;

public class StringClient {

	public static void main(String[] args) {
		Registry rgsty;
		Scanner sc = new Scanner(System.in);
		try {
			rgsty = LocateRegistry.getRegistry(8000);
			IStringProcess str = (IStringProcess) rgsty.lookup("rmi://localhost/string");
			
			String input;
			System.out.println("Nhập vào chuỗi: ");
			input = sc.nextLine();
			
			System.out.println("Chuỗi viết hoa: " + str.to_upper(input));
			System.out.println("Chuỗi viết thường: " + str.to_lower(input));
			System.out.println("Chuỗi viết hoa mỗi từ: " + str.to_title(input));
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
