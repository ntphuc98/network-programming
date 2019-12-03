import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;

/**
 * Viết chương trình kết nối đến đối tượng URL để lấy các thông tin về liên quan
 * như ngày tạo, ngày chỉnh sửa sau cùng, ngày hết hạn … hiển thị lên màn hình.
 * 
 * @author phuc
 *
 */
public class bai4 {
	URLConnection uc;
	Date createDate;
	Date expirationDate;
	Date lastDate;

	public void getInfo(URL url) {
		try {
			uc = url.openConnection();

			createDate = new Date(uc.getDate());
			lastDate = new Date(uc.getLastModified());
			expirationDate = new Date(uc.getExpiration());

			System.out.println("Ngày tạo: " + createDate);
			System.out.println("Ngày hết hạn: " + expirationDate);
			System.out.println("Ngày chỉnh sửa sau cùng: " + lastDate);
		} catch (IOException e) {
			System.out.println("Không thể mở kết nối đến URL " + e.toString());
		}
	}
}
