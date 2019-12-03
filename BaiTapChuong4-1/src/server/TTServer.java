package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class TTServer {
    public final static int defaultPort = 8000;
    public static int clientNumber = 0;
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(defaultPort);
            System.out.println("Server đã được tạo!");
            while (true) {
                try {
                    Socket s = ss.accept();
                    clientNumber++;
                    System.out.println("Client " + clientNumber + " đã kết nối!");
					DataInputStream dis = new DataInputStream(s.getInputStream());
					DataOutputStream dos = new DataOutputStream(s.getOutputStream());
					
                    while (true) {
                    	dos.writeUTF("Nhập số - nhập q để thoát");
                        String str = dis.readUTF();
                        if (str.equals("q")) {
                        	dos.writeUTF("Đã ngắt kết nối!");
                            break;
                        }
                        String r = parseStringToNumber(str);
                        dos.writeUTF(r);
                    }
                	s.close();
                	System.out.println("Client " + clientNumber + " đã ngắt kết nối!");
                } catch (IOException e) {
                    System.err.println(" Connection Error: " + e);
                }
            }
        } catch (IOException e) {
            System.err.println(" Server Creation Error:" + e);
        }
    }

    public static String parseStringToNumber(String n) {

        char a[];
        int b = 0;
        int c = 0;
        String tmp = "";
        String strNumber = "";
        String result = "";
        String n1 = "";
        String nStr[] = {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bẩy", "tám", "chín"};
        String mStr[] = {"", "mươi", "trăm"};
        String vStr[] = {"", "nghìn", "triệu", "tỉ", "nghìn", "triệu"};

        for (int j = n.length(), x = 0; j >= 0; j -= 3, x += 3) {

            n1 = n.substring((j - 3 < 0) ? 0 : j - 3, j);

            a = n1.toCharArray();
            strNumber = "";

            for (int i = 0; i < a.length; i++) {

                try {
                    b = Integer.parseInt("" + a[a.length - i - 1]);
                    c = Integer.parseInt("" + a[Math.max(0, a.length - i - 2)]);
                } catch (NumberFormatException e) {
                    return "Khong phai so nguyen";
                }

                tmp = (b == 1) ? ((i == 1) ? "mười" : ((i == 0 && c > 1) ? "mốt " : nStr[1] + " " + mStr[i]))
                        : ((b == 5) ? ((i == 0 && c != 0 && i + 1 < a.length) ? "năm " : nStr[5] + " " + mStr[i])
                                : ((b == 0) ? ((i == 0) ? "" : ((i == 1 && c != 0) ? "linh" : ((i == 2) ? "không" + " " + mStr[i] : " "))) : nStr[b] + " " + mStr[i]));

                strNumber = !strNumber.equals("") ? tmp + " " + strNumber : tmp;
            }
            if (strNumber.equals("")) {
                break;
            }
            result = !result.equals("") ? strNumber + vStr[x / 3] + " " + result : strNumber;
        }

        return result;
    }
}
