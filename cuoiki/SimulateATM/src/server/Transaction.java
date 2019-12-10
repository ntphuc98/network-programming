package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dao.ICheckDB;
import model.Account;
import model.Bank;
import model.History;
import service.Service;

/*
 * Nguyen Thanh Phuc
 * github.com/ntphuc98
 */
public class Transaction extends Thread {
	public Socket socket;
	private ICheckDB dao;
	private DataInputStream dis;
	private DataOutputStream dos;
	private List<Bank> bankList;
	private Account account = null;
	private Bank bankAccount = null;
	private List<History> historyList;

	public Transaction(Socket socket, List<Bank> bankList, ICheckDB dao) throws IOException {
		this.socket = socket;
		this.bankList = bankList;
		this.historyList = new ArrayList<>();
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
		this.dao = dao;
		System.out.println(socket.getInetAddress().getHostAddress() + "/" + socket.getPort() + " đã kết nối!");
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (isLogin()) {
					getBank();
					switch (account.getRole()) {
					case 1:
						handleMainAccount();
						break;
					case 0:
						handleSubAccount();
						break;
					default:
						break;
					}
				} else {
					login();
				}
			} catch (IOException e) {
				System.out.println(
						socket.getInetAddress().getHostAddress() + "/" + socket.getPort() + " đã ngắt kết nối!");
				break;
			}
		}
	}

	private void getBank() {
		if (account != null && !bankList.isEmpty()) {
			for (Bank bank : bankList) {
				if (bank.getId() == account.getBankId()) {
					this.bankAccount = bank;
				}
			}
		}
	}

	private void printMainMenu() throws IOException {
		dos.writeUTF("-----------------------------------------------");
		dos.writeUTF("Xin chào " + account.getName());
		dos.writeUTF("Vui lòng lựa chọn loại giao dịch...");
		dos.writeUTF("1. Vấn tin tài khoản");
		dos.writeUTF("2. Rút tiền");
		dos.writeUTF("3. Nạp tiền");
		dos.writeUTF("4. Chuyển tiền");
		dos.writeUTF("5. Đổi mã PIN");
		dos.writeUTF("6. Xem lịch sử giao dịch");
		dos.writeUTF("7. Thoát");
	}

	private void printSubMenu() throws IOException {
		dos.writeUTF("-----------------------------------------------");
		dos.writeUTF("Xin chào " + account.getName());
		dos.writeUTF("Vui lòng lựa chọn loại giao dịch...");
		dos.writeUTF("1. Vấn tin tài khoản");
		dos.writeUTF("2. Rút tiền");
		dos.writeUTF("2. Nạp tiền");
		dos.writeUTF("4. Đổi mã PIN");
		dos.writeUTF("5. Xem lịch giao dịch");
		dos.writeUTF("6. Thoát");
	}

	private void handleMainAccount() throws IOException {
		printMainMenu();
		String lc = dis.readUTF();
		switch (lc) {
		case "1": // getBalance
			getBalance();
			break;
		case "2": // withdraw
			withdraw();
			break;
		case "3": // deposit
			deposit();
			break;
		case "4": // transfer
			transfer();
			break;
		case "5": // changePIN
			changePIN();
			break;
		case "6": // getHistories
			getHistories();
			break;
		case "7": // logout
			logout();
			break;
		case "cancel":
			logout();
			break;
		default:
			dos.writeUTF("Yêu cầu không hợp lệ!");
			break;
		}
	}

	private void handleSubAccount() throws IOException {
		printSubMenu();
		String lc = dis.readUTF();
		switch (lc) {
		case "1": // getBalance
			getBalance();
			break;
		case "2": // withdraw
			withdraw();
			break;
		case "3": // deposit
			deposit();
			break;
		case "4": // changePIN
			changePIN();
			break;
		case "5": // getHistories
			getHistories();
			break;
		case "6": // logout
			logout();
			break;
		case "cancel":
			logout();
			break;
		default:
			dos.writeUTF("Yêu cầu không hợp lệ!");
			break;
		}
	}

	// start login
	private boolean isLogin() {
		if (account != null) {
			return true;
		} else {
			return false;
		}
	}

	private void login() throws IOException {
		int count = 0;
		while (!isLogin()) {
			dos.writeUTF("-----------------------------------------------");
			dos.writeUTF("Xin chào quý khách!");
			dos.writeUTF("Nhập số thẻ: ");
			String cardId = dis.readUTF();

			if (dao.checkCardId(cardId)) {
				getAccount(cardId);
			} else {
				count++;
				dos.writeUTF("Số thẻ không hợp lệ!");
			}
		}
	}

	private void getAccount(String cardId) throws IOException {
		int count = 0;
		while (true) {
			dos.writeUTF("Nhập mã PIN: ");
			String pin = dis.readUTF();
			if (cancel(pin)) {
				break;
			} else {
				this.account = dao.getAccount(cardId, pin);
				if (isLogin()) {
					break;
				} else {
					count++;
					dos.writeUTF("PIN không chính xác! ");
				}
				if (count >= 3) {
					dos.writeUTF("Nhập mã PIN sai nhiều lần. Vui lòng đăng nhập lại!");
					break;
				}
			}
		}
	}

	// end login
	// 1. Vấn tin tài khoản
	private void getBalance() throws IOException {
		if (this.account != null) {
			dos.writeUTF("Tên tài khoản: " + account.getName());
			dos.writeUTF("Số thẻ: " + account.getCardId());
			dos.writeUTF("Số dư: " + DecimalFormat.getInstance().format(bankAccount.getBalance()) + " VND");
			ask();
		} else {
			logout();
		}
	}

	// 2. Rút tiền
	private void withdraw() throws IOException {
		while (true) {
			dos.writeUTF("-----------------------------------------------");
			dos.writeUTF("Nhập số tiền:");
			dos.writeUTF("Tối thiểu 50,000VND");
			String input = dis.readUTF();
			if (cancel(input)) {
				break;
			} else if (Service.isNumber(input)) {
				if (input.length() > 9 || Integer.parseInt(input) > 300000000) {// 300 000 000
					dos.writeUTF("Tối đa 300,000,000VND!");
				} else if (Integer.parseInt(input) < 50000) {
					dos.writeUTF("Tối thiểu 50,000VND!");
				} else if (Integer.parseInt(input) % 50000 != 0) {
					dos.writeUTF("Số tiền phải là bội 50,000VND!");
				} else {
					int amount = Integer.parseInt(input);
					// sub
					if (this.bankAccount.withdraw(amount)) {
						// add history
						History history = new History();
						history.setBankId(bankAccount.getId());
						history.setType(1);
						history.setAmount(amount);
						history.setLastBalance(bankAccount.getBalance());
						history.setByAccount(account.getCardId());
						history.setNote(account.getCardId());
						history.save();
						// response
						dos.writeUTF("Giao dịch thành công.");
						ask();
						break;
					} else {
						dos.writeUTF("Số dư không đủ!");
						dos.writeUTF("Số dư hiện tại: " + DecimalFormat.getInstance().format(bankAccount.getBalance())
								+ "VND");
					}
				}
			} else {
				dos.writeUTF("Không hợp lệ!");
			}
		}
	}

	// 3. Nạp tiền
	private void deposit() throws IOException {
		while (true) {
			dos.writeUTF("-----------------------------------------------");
			dos.writeUTF("Nhập số tiền:");
			String input = dis.readUTF();
			if (cancel(input)) {
				logout();
				break;
			} else if (Service.isNumber(input)) {
				if (input.length() > 9 || Integer.parseInt(input) > 300000000) {// 300 000 000
					dos.writeUTF("Tối đa 300,000,000VND!");
				} else if (Integer.parseInt(input) < 50000) {
					dos.writeUTF("Tối thiểu 50,000VND!");
				} else if (Integer.parseInt(input) % 50000 != 0) {
					dos.writeUTF("Số tiền phải là bội 50,000VND!");
				} else {
					// nạp
					int amount = Integer.parseInt(input);
					// sub
					if (this.bankAccount.deposit(amount)) {
						// add history
						History history = new History();
						history.setBankId(bankAccount.getId());
						history.setType(2);
						history.setAmount(amount);
						history.setLastBalance(bankAccount.getBalance());
						history.setNote(account.getCardId());
						history.setByAccount(account.getCardId());
						history.save();
						// response
						dos.writeUTF("Giao dịch thành công.");
						ask();
						break;
					} else {
						dos.writeUTF("Giao dịch không thành công. Vui lòng thử lại!");
					}
				}
			} else {
				dos.writeUTF("Không hợp lệ!");
			}
		}
	}

	// 4. Chuyển tiền
	private void transfer() throws IOException {
		while (true) {
			dos.writeUTF("-----------------------------------------------");
			dos.writeUTF("Nhập số tài khoản nhận:");
			String inputCardId = dis.readUTF();
			if (cancel(inputCardId)) {
				logout();
				break;
			} else if (!Service.isNumber(inputCardId) || !dao.checkCardId(inputCardId)) {
				dos.writeUTF("Số tài khoản không hợp lệ!");
			} else {
				while (true) {
					dos.writeUTF("Nhập số tiền (Tối đa 20,000,000VND):");
					String inputAmount = dis.readUTF();
					if (cancel(inputAmount)) {
						logout();
						break; // out while
					} else if (!Service.isNumber(inputAmount) || inputAmount.length() > 8) {
						dos.writeUTF("Không hợp lệ!");
					} else if (Service.toNumber(inputAmount) > 20000000) {
						dos.writeUTF("Tối đa 20,000,000 VND!");
					} else if(Service.toNumber(inputAmount) <= 50000){
						dos.writeUTF("Tối thiểu 50,000 VND!");
					}else if (Service.toNumber(inputAmount) > (bankAccount.getBalance() - 50000)) {
						dos.writeUTF("Số dư không đủ!");
						dos.writeUTF("Số dư hiện tại: " + Service.toVNDCurrency(bankAccount.getBalance()) + " VND");
					} else {
						int amount = Service.toNumber(inputAmount);
						// chuyển
						Bank otherBank = Bank.getByCardId(inputCardId);
						// add
						if (bankAccount.withdraw(amount)) {
							// add history
							History historyAccount = new History();
							historyAccount.setBankId(bankAccount.getId());
							historyAccount.setType(3);
							historyAccount.setAmount(amount);
							historyAccount.setLastBalance(bankAccount.getBalance());
							historyAccount.setNote("transfer to " + inputCardId);
							historyAccount.setByAccount(account.getCardId());
							historyAccount.save();
						}
						// sub
						if (otherBank.deposit(amount)) {
							History historyOther = new History();
							historyOther.setBankId(otherBank.getId());
							historyOther.setType(4);
							historyOther.setAmount(amount);
							historyOther.setLastBalance(otherBank.getBalance());
							historyOther.setNote("receive from " + account.getCardId());
							historyOther.setByAccount(inputCardId);
							historyOther.save();
						}
						// thông báo
						dos.writeUTF("Giao dịch thành công.");
						ask();
						// out while
						break;
					}
				}
				// out while
				break;
			}
		}
	}

	// 5. Đổi mã PIN
	private void changePIN() throws IOException {
		int count = 0;
		while (true) {
			dos.writeUTF("Quý khách vui lòng nhập mã PIN cũ: ");
			String pin = dis.readUTF();
			if (cancel(pin) || !isLogin() || count > 3) {
				if (count > 3) {
					dos.writeUTF("Nhập PIN sai nhiều lần, vui lòng đăng nhập lại!");
				}
				logout();
				break;
			} else if (account.getPin().equals(pin)) {
				while (true) {
					dos.writeUTF("Quý khách vui lòng nhập mã PIN mới(6 chữ số): ");
					String newPIN = dis.readUTF();
					if (cancel(newPIN)) {
						logout();
						break;
					} else if (Service.isValidPin(newPIN)) {
						dos.writeUTF("Quý khách vui lòng nhập lại mã PIN mới(6 chữ số): ");
						String newConfirmPIN = dis.readUTF();
						if (cancel(newConfirmPIN)) {
							logout();
							break;
						} else {
							if (Service.isConfirmPIN(newPIN, newConfirmPIN)) {
								if (account.updatePin(newConfirmPIN)) {
									dos.writeUTF("Giao dịch thành công.");
								} else {
									dos.writeUTF("Giao dịch không thành công!");
								}
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
				dos.writeUTF("Nhập PIN cũ không chính xác!");
			}
		}
	}

	// end changePin
	// 6. Xem lịch sử giao dịch
	private void getHistories() throws IOException {
		if (account != null && bankAccount != null) {
			List<History> historyList = bankAccount.getHistories();
			if (historyList.isEmpty()) {
				dos.writeUTF("Chưa có giao dich nào!");
			} else {
				dos.writeUTF("-----------------------------------------------");
				dos.writeUTF("Các giao dịch gần đây:");
				printHistories(historyList);
			}
			ask();
		} else {
			logout();
		}
	}

	private void printHistories(List<History> historyList) throws IOException {
		for (History history : historyList) {
			dos.writeUTF("**********************");
			if (history.getType() == 1) { // rút
				dos.writeUTF("Thời gian: " + history.getCr_date());
				dos.writeUTF("Tài khoản: " + account.getCardId());
				dos.writeUTF("Số tiền GD: -" + DecimalFormat.getInstance().format(history.getAmount()) + "VND");
				dos.writeUTF("Số dư cuối: " + DecimalFormat.getInstance().format(history.getLastBalance()) + "VND");
				dos.writeUTF("Nội dung: " + history.getNote());
			} else if (history.getType() == 2) // nạp
			{
				dos.writeUTF("Thời gian: " + history.getCr_date());
				dos.writeUTF("Tài khoản: " + account.getCardId());
				dos.writeUTF("Số tiền GD: +" + DecimalFormat.getInstance().format(history.getAmount()) + "VND");
				dos.writeUTF("Số dư cuối: " + DecimalFormat.getInstance().format(history.getLastBalance()) + "VND");
				dos.writeUTF("Nội dung: " + history.getNote());
			} else if (history.getType() == 3)// chuyển di
			{
				dos.writeUTF("Thời gian: " + history.getCr_date());
				dos.writeUTF("Tài khoản: " + account.getCardId());
				dos.writeUTF("Số tiền GD: -" + DecimalFormat.getInstance().format(history.getAmount()) + "VND");
				dos.writeUTF("Số dư cuối: " + DecimalFormat.getInstance().format(history.getLastBalance()) + "VND");
				dos.writeUTF("Nội dung: " + history.getNote());
			} else if (history.getType() == 4)// nhận về
			{
				dos.writeUTF("Thời gian: " + history.getCr_date());
				dos.writeUTF("Tài khoản: " + account.getCardId());
				dos.writeUTF("Số tiền GD: +" + DecimalFormat.getInstance().format(history.getAmount()) + "VND");
				dos.writeUTF("Số dư cuối: " + DecimalFormat.getInstance().format(history.getLastBalance()) + "VND");
				dos.writeUTF("Nội dung: " + history.getNote());
				dos.writeUTF("Tài khoản nhận: " + history.getByAccount());
			}
		}
		dos.writeUTF("**********************");
	}

	// logout
	private void logout() {
		if (this.account != null)
			this.account = null;
	}

	// enter cancel
	private boolean cancel(String str) {
		if (str.equals("cancel")) {
			return true;
		} else {
			return false;
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
}
