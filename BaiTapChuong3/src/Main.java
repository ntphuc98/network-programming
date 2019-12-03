import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {
	URL url;
	public void nhap() {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Nhập URL/IP: ");
			String u = sc.nextLine();
			url = new URL(u);
		} catch (MalformedURLException e) {
			System.out.println("URL/IP không hợp lệ " + e);
		}
	}

	public static void main(String[] args) {
//		Main main = new Main();
//		main.nhap();
		
//		bai1 b1 = new bai1();
//		b1.getInfoURL(main.url);
		
//		bai2 b2 = new bai2();
//		b2.viewSource(main.url);
		
//		bai3 b3 = new bai3();
//		b3.nhap();
//		b3.getInfoIA();
		//https://www.javatpoint.com/URLConnection-class

//		bai4 b4 = new bai4();
//		b4.getInfo(main.url);

//		bai5 b5 = new bai5();
//		b5.nhapBlackList();
//		b5.getContentURL(main.url);
		
		bai6 b6 = new bai6();
		b6.nhap();
		b6.getHistory();
	}

}
