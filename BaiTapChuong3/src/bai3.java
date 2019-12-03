import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Viết chương trình sử dụng các phương thức getLocalHost(), getByName() của đối
 * tượng InetAddress để in ra địa chỉ của localhost, địa chỉ URL của một
 * website.
 * 
 * @author phuc
 *
 */
public class bai3 {
	String addr;

	public void nhap() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập địa chỉ: ");
		addr = sc.nextLine();
	}

	public void getInfoIA() {
		try {
			InetAddress hostLocal = InetAddress.getLocalHost();
			InetAddress iNull = InetAddress.getByName(null);
			InetAddress hostWebsite = InetAddress.getByName(addr);
			
			System.out.println("The localhost is: " + hostLocal);
			System.out.println("The Null host is: " + iNull);
			System.out.println("The website is: " + hostWebsite);
			
//			System.out.println("Host name localhost: " + hostLocal.getHostName());
//			System.out.println("Dia chi IP localhost: " + hostLocal.getHostAddress());
//			System.out.println("Host Null: " + hostWebsite.getHostName());
//			System.out.println("Dia chi Null host: " + hostWebsite.getHostAddress());
//			System.out.println("Host name website: " + hostWebsite.getHostName());
//			System.out.println("Dia chi IP website: " + hostWebsite.getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("Địa chỉ không hợp lệ " + e);
		}
	}
}
