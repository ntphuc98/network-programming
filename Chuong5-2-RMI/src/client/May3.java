package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import imethod.IProcess1;
import imethod.IProcess2;

public class May3 {
	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		try {
			Registry rgsty = LocateRegistry.getRegistry(8000);
			IProcess1 process = (IProcess1) rgsty.lookup("rmi://localhost/process");
			Registry rgsty2 = LocateRegistry.getRegistry(8080);
			IProcess2 process2 = (IProcess2) rgsty2.lookup("rmi://localhost/process");
			while (true) {
				menu();
				String lc = sc.nextLine();
				if (lc.equals("7")) {
					System.out.println("Kết thúc chương trình !!!");
					break;
				}
				switch (lc) {
				case "1":
					Calculator(process);
					break;
				case "2":
					sRectangle(process);
					break;
				case "3":
					pRectangle(process);
					break;
				case "4":
					upper(process2);
					break;
				case "5":
					lower(process2);
					break;
				case "6":
					title(process2);
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
		System.out.println("3. Tính chu vi hình chữ nhật");
		System.out.println("4. Chuyển chuỗi thành chuỗi viết hoa");
		System.out.println("5. Chuyển chuỗi thành chuỗi viết thường");
		System.out.println("6. Chuyển chuỗi thành chuỗi viết hoa chữ đầu mỗi từ");
		System.out.println("7. Exit");
		System.out.print("Your choice: ");
	}

	private static void Calculator(IProcess1 p) throws RemoteException {
		System.out.print("Nhập phép tính (+ - * /): ");
		String s = sc.nextLine();
		System.out.print("Nhập a: ");
		int a = sc.nextInt();
		System.out.print("Nhập b: ");
		int b = sc.nextInt();
		sc.nextLine();
		char operator = s.charAt(0);
		System.out.println(p.calculate(operator, a, b));
	}

	private static void sRectangle(IProcess1 p) throws RemoteException {
		System.out.print("Nhập chiều dài: ");
		int a = sc.nextInt();
		System.out.print("Nhập chiều rộng: ");
		int b = sc.nextInt();
		sc.nextLine();
		System.out.println("Diện tích hcn = " + p.sRectangle(a, b));
	}

	private static void pRectangle(IProcess1 p) throws RemoteException {
		System.out.print("Nhập chiều dài: ");
		int a = sc.nextInt();
		System.out.print("Nhập chiều rộng: ");
		int b = sc.nextInt();
		sc.nextLine();
		System.out.println("Chu vi hình chữ nhật = " + p.pRectangle(a, b));
	}

	private static void upper(IProcess2 p) throws RemoteException {
		System.out.print("Nhập chuỗi: ");
		String a = sc.nextLine();

		System.out.println(p.to_upper(a));
	}

	private static void lower(IProcess2 p) throws RemoteException {
		System.out.print("Nhập chuỗi: ");
		String a = sc.nextLine();

		System.out.println(p.to_lower(a));
	}

	private static void title(IProcess2 p) throws RemoteException {
		System.out.print("Nhập chuỗi: ");
		String a = sc.nextLine();

		System.out.println(p.to_title(a));
	}
}
