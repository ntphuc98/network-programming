import java.util.Date;

public class Url {
	String hostName;
	String ip;
	Date time;
	
	public Url(String hN, String ip, Date t){
		this.hostName = hN;
		this.ip = ip;
		this.time = t;
	}
	public String toString() {
		return "URL: " + this.hostName + ", IP: " +
				this.ip + ", Thời gian truy cập: " + time.toString();
	}
}
