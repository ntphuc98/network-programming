import java.net.URL;

/**
 * Viết chương trình truy cập đến đối tượng URL và trả về thông tin liên quan
 * (tên file, tên host, số hiệu cổng, kiểu giao thức) của nó. *
 * 
 * @author phuc
 *
 */
public class bai1 {
	public void getInfoURL(URL url) {
		System.out.println("Tên file " + url.getFile());
		System.out.println("Tên host " + url.getHost());
		System.out.println("Số hiệu cổng " + url.getPort());
		System.out.println("Kiểu giao thức: " + url.getProtocol());
	}
}
