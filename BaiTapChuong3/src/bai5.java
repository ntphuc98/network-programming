import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Viết chương trình cho phép người dùng nhập vào địa chỉ các trang web và kiểm
 * soát việc truy cập các trang web của người dùng. Trong đó, khi người dùng
 * truy cập đến 1 trang web có trong danh sách cấm thì thông báo cho người dùng
 * biết. Khi người dùng truy cập đến trang web không có trong danh sách cấm thì
 * lấy nội dung trang web về hiển thị lên màn hình. Người dùng có thể nhập địa
 * chỉ IP hoặc Hostname.
 * 
 * @author phuc
 *
 */
public class bai5 {
	Set<String> blackList;
	Scanner sc;

	public bai5() {
		blackList = new HashSet<String>();
		sc = new Scanner(System.in);
		blackList.add("javatpoint.com");
		blackList.add("www.javatpoint.com");
	}

	public void nhapBlackList() {
		String u = null;
		while (u != "q") {
			System.out.println("Nhập URL thêm vào danh sách chặn('q' thoát): ");
			u = sc.nextLine();
			if (u.equals("q")) {
				break;
			} else {
				URL url;
				try {
					url = new URL(u);
					blackList.add(url.getHost());
				} catch (MalformedURLException e) {
					System.out.println("URL không hợp lệ " + e);
				}
			}
		}
	}

	public void getContentURL(URL url) {
		if (blackList.contains(url.getHost())) {
			System.out.println("URL đã bị chặn!");
		} else {
			bai2 oBai2 = new bai2();
			oBai2.viewSource(url);
		}
	}
}
