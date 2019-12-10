package dao;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Account;

public class RMIDAOServer extends UnicastRemoteObject implements ICheckDB {
	private Connection conn;

	protected RMIDAOServer() throws RemoteException {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BANKING", "javauser", "123456");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Registry rgsty;
		try {
			rgsty = LocateRegistry.createRegistry(8080);
			rgsty.rebind("rmi://localhost/dao", new RMIDAOServer());
			System.out.println("RMIDAOServer đang hoạt động...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkCardId(String cardId) throws IOException {
		Statement stm;
		try {
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("select * from account where card_id like '" + cardId + "'");
			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			System.out.println("Lỗi checkCardId");
		}
		return false;
	}

	@Override
	public Account getAccount(String cardId, String pin) throws IOException {
		Statement stm;
		try {
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(
					"select * from account where card_id like '" + cardId + "' and pin like '" + pin + "'");
			if (rs.next()) {
				Account account = new Account(rs.getInt("id"), rs.getString("name"), rs.getString("card_id"), pin,
						rs.getInt("role"), rs.getInt("bank_id"));
				return account;
			}
		} catch (SQLException e) {
			System.out.println("Lỗi getUser");
		}
		return null;
	}

	@Override
	public boolean checkPin(Account account) throws IOException {
		String sql = "select * from account where cardId like '" + account.getCardId() + "' and pin like '"
				+ account.getPin() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Lỗi checkPin");
		}
		return false;
	}
}
