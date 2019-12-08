package bankserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import bean.Account;
import irmi.IProcessDB;

public class ServiceProcessing extends Thread {
	private Socket channel;
	private DataInputStream dis;
	private DataOutputStream dos;
	private IProcessDB dao;
	private Account account = null;

	public ServiceProcessing(Socket socket, IProcessDB dao) throws IOException, NotBoundException {
		channel = socket;
		dos = new DataOutputStream(channel.getOutputStream());
		dis = new DataInputStream(channel.getInputStream());
		this.dao = dao;
		System.out.println(channel.getInetAddress().getHostAddress() + "/" + channel.getPort() + " đã kết nối!");
	}

	public void run() {
		while (true) {
			try {
				if (!isLogin()) {
					login();
				} else {
					menu();
					String lc = dis.readUTF();
					switch (lc) {
					case "1": // balance
						getBalance();
						break;
					case "2": // withdraw
						break;
					case "3": //
						break;
					case "4": // change PIN
						changePIN();
						break;
					case "5": // history
						break;
					case "6": // out
						account = null;
						break;
					case "cancel":
						account = null;
						break;
					default:
						dos.writeUTF("Yêu cầu không hợp lệ!");
						break;
					}
				}
			} catch (IOException e) {
				System.out.println(
						channel.getInetAddress().getHostAddress() + "/" + channel.getPort() + " đã ngắt kết nối!");
				break;
			}
		}
	}

	private void menu() throws IOException {
		dos.writeUTF("-----------------------------------------------");
		dos.writeUTF("Xin chào " + account.getName());
		dos.writeUTF("Vui lòng lựa chọn loại giao dịch...");
		dos.writeUTF("1. Vấn tin tài khoản");
		dos.writeUTF("2. Rút tiền");
		dos.writeUTF("3. Chuyển tiền");
		dos.writeUTF("4. Đổi mã PIN");
		dos.writeUTF("5. Xem lịch sử");
		dos.writeUTF("6. Thoát");
	}

	private void login() throws IOException {
		while (!isLogin()) {
			dos.writeUTF("-----------------------------------------------");
			dos.writeUTF("Xin chào quý khách!");
			dos.writeUTF("Nhập số thẻ: ");
			String cardId = dis.readUTF();

			if (!dao.checkCardId(cardId)) {
				dos.writeUTF("Số thẻ không hợp lệ!");
			} else if (!dao.checkActiveCardId(cardId)) {
				dos.writeUTF("Thẻ đã bị khóa!");
			} else {
				inputPIN(cardId);
			}
		}
	}

	private void inputPIN(String cardId) throws IOException {
		int count = 0;
		while (true) {
			dos.writeUTF("Nhập mã PIN: ");
			String pin = dis.readUTF();
			if (end(pin)) {
				break;
			} else {
				account = dao.getUser(cardId, pin);
				if (account != null) {
					break;
				} else {
					count++;
					dos.writeUTF("PIN không chính xác! ");
				}
				if (count >= 3) {
					dos.writeUTF("Thẻ đã bị khóa do nhập sai PIN nhiều lần!");
					dao.blockUser(cardId);
					break;
				}
			}
		}
	}

	private void getBalance() throws IOException {
		if (account != null) {
			double balance = (double) dao.checkBalance(account);
			dos.writeUTF("Tên tài khoản: " + account.getName());
			dos.writeUTF("Số thẻ: " + account.getCardID());
			dos.writeUTF("Số dư: " + DecimalFormat.getInstance().format(balance) + " VND");
			ask();
		} else {
			run();
		}
	}

	// 4. Đổi mã PIN
	private void changePIN() throws IOException {
		int count = 0;
		while (true) {
			dos.writeUTF("Quý khách vui lòng nhập mã PIN cũ: ");
			String pin = dis.readUTF();
			if (end(pin) || !isLogin() || count > 3) {
				if (count > 3) {
					dos.writeUTF("Nhập PIN sai nhiều lân, vui lòng đăng nhập lại!");
				}
				logout();
				break;
			} else if (dao.checkPin(account, pin)) {
				while (true) {
					dos.writeUTF("Quý khách vui lòng nhập mã PIN mới(6 chữ số): ");
					String newPIN = dis.readUTF();
					if (end(newPIN)) {
						logout();
						break;
					} else if (checkValidPIN(newPIN)) {
						dos.writeUTF("Quý khách vui lòng nhập lại mã PIN mới(6 chữ số): ");
						String newConfirmPIN = dis.readUTF();
						if (end(newConfirmPIN)) {
							logout();
							break;
						} else {
							if (checkConfirmPIN(newPIN, newConfirmPIN)) {
								dao.updatePin(account, newConfirmPIN);
								dos.writeUTF("Giao dịch thành công!.");
								logout();
								break;
							} else {
								dos.writeUTF("Nhập lại mã PIN không chính xác!.");
							}
						}
					} else {
						dos.writeUTF("PIN không hợp lệ! (gồm 6 chữ số).");
					}
				}
				break;
			} else {
				count++;
				dos.writeUTF("Nhập PIN không chính xác!");
			}
		}
	}

	private void ask() throws IOException {
		dos.writeUTF("Bạn có muốn thực hiện giao dịch khác? (y/n)");
		String lc = dis.readUTF();
		switch (lc) {
		case "n":
		case "cancel":
			logout();
			break;
		case "y":
			break;
		default:
			dos.writeUTF("Không hợp lệ!");
			ask();
			break;
		}
	}

	private boolean end(String cancel) {
		if (cancel.equals("cancel")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isLogin() {
		if (account == null)
			return false;
		else
			return true;
	}

	private void logout() {
		if (account != null) {
			account = null;
		}
	}

	private boolean checkConfirmPIN(String pin, String comfirmPIN) {
		if (checkValidPIN(comfirmPIN) && pin.equals(comfirmPIN))
			return true;
		return false;
	}

	private boolean checkValidPIN(String pin) {
		if (pin.length() == 6 && isNumber(pin))
			return true;
		return false;
	}

	private boolean isNumber(String str) {
		String regex = "[0-9]+";
		return str.matches(regex);
	}
}
