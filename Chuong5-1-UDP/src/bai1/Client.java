package bai1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		try {
			Registry rgsty = LocateRegistry.getRegistry(8000);
			IProcess process = (IProcess) rgsty.lookup("rmi://localhost/process");
			menu();
			while (true) {
				String lc = sc.nextLine();
				if (lc.equals("6"))
					break;
				switch (lc) {
				case "1":
					Calculator(process);
					break;
				case "2":
					Rectangle(process);
					break;
				case "3":
					upper(process);
					break;
				case "4":
					lower(process);
					break;
				case "5":
					title(process);
					break;
				default:
					System.out.println("Lựa chọn không hợp lệ!");
					break;
				}
			}
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	private static void menu() {
		System.out.println("\n---------Menu---------");
		System.out.println("1. Tính toán hai số");
		System.out.println("2. Tính diện tích hình chữ nhật");
		System.out.println("3. Chuyển chuỗi thành chuỗi viết hoa");
		System.out.println("4. Chuyển chuỗi thành chuỗi viết thường");
		System.out.println("5. Chuyển chuỗi thành chuỗi viết hoa chữ đầu mỗi từ");
		System.out.println("6. Exit");
		System.out.print("Your choice: ");
	}

	private static void Calculator(IProcess p) throws RemoteException {
		System.out.print("Nhập phép tính (+ - * /): ");
		String s=sc.nextLine();
		System.out.print("Nhập a: ");
		int a = sc.nextInt();
		System.out.print("Nhập b: ");
		int b = sc.nextInt();
		sc.nextLine();
		char operator = s.charAt(0);
		System.out.println(p.calculate(operator, a, b));
	}
	private static void Rectangle(IProcess p) throws RemoteException {
		System.out.print("Nhập chiều dài: ");
		int a = sc.nextInt();
		System.out.print("Nhập chiều rộng: ");
		int b = sc.nextInt();
		sc.nextLine();
		System.out.println(p.sRectangle(a, b));
	}
	private static void upper(IProcess p) throws RemoteException {
		System.out.print("Nhập chuỗi: ");
		String a = sc.nextLine();

		System.out.println(p.to_upper(a));
	}
	private static void lower(IProcess p) throws RemoteException {
		System.out.print("Nhập chuỗi: ");
		String a = sc.nextLine();

		System.out.println(p.to_lower(a));
	}
	private static void title(IProcess p) throws RemoteException {
		System.out.print("Nhập chuỗi: ");
		String a = sc.nextLine();

		System.out.println(p.to_title(a));
	}
}
