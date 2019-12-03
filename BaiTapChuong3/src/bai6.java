import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Viết chương trình cho phép người dùng nhập vào các địa chỉ URL và lưu lại các
 * thông tin (Hostname, địa chỉ IP, thời gian truy cập) của các trang web mà
 * người dùng đã truy cập (không trùng lặp thông tin)
 * 
 * @author phuc
 *
 */
public class bai6 {
	Map<String, Url> list;

	public bai6() {
		list = new HashMap<String, Url>();
	}

	public void nhap() {
		String u = null;
		System.out.println("Nhập vào URL:(nhập 'q' thoát) ");
		Scanner sc = new Scanner(System.in);
		while (true) {
			u = sc.nextLine();
			if (u.equals("q")) {
				break;
			} else {
				try {
					URL url = new URL(u);
					try {
						InetAddress host = InetAddress.getByName(url.getHost());
						Date time = new Date();
						String hostName = url.getHost();
						String ip =  host.getHostAddress();
						Url ob = new Url(hostName, ip, time);
						
						if (list.containsKey(hostName)) {
							list.replace(hostName, ob);
						}else {
							list.put(hostName, ob);
						}
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					System.out.println("URL/IP không hợp lệ " + e);
				}
			}
		}
	}

	public void getHistory() {
		System.out.println("Lịch sử truy cập:");
		for (Map.Entry m : list.entrySet()) {
			System.out.println(m.getValue());
		}
	}
}
