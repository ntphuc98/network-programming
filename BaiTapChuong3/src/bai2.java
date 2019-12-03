import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Viết chương trình liên kết đến URL và sử dụng phương thức getContent() của
 * đối tượng URL để nhận toàn bộ nội dung của trang web rồi hiển thị lên màn
 * hình.
 * 
 * @author phuc
 *
 */
public class bai2 {
	public void viewSource(URL url) {
		int i;
		try {
			InputStream dis = (InputStream) url.getContent();
			while ((i = dis.read()) > 0)
				System.out.print((char) i);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
