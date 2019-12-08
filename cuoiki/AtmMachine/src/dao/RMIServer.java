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

import bean.Account;
import irmi.IProcessDB;

public class RMIServer extends UnicastRemoteObject implements IProcessDB {
	private Connection conn;

	public RMIServer() throws RemoteException {
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
			rgsty.rebind("rmi://localhost/database", new RMIServer());
			System.out.println("Máy RMI đang hoạt động...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public boolean checkCardId(String cardId) throws IOException {
		Statement stm;
		try {
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("select * from users where cardID like '" + cardId + "'");
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
	public boolean checkActiveCardId(String cardId) throws IOException {
		Statement stm;
		try {
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("select * from users where cardID like '" + cardId + "' and status=1");
			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			System.out.println("Lỗi checkActiveCardId");
		}
		return false;
	}

	@Override
	public Account getUser(String cardId, String pin) throws IOException {
		Statement stm;
		try {
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(
					"select * from users where cardID like '" + cardId + "' and pin like '" + pin + "' and status=1");
			if (rs.next()) {
				return new Account(rs.getString("name"), cardId, pin);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi getUser");
		}
		return null;
	}

	@Override
	public float checkBalance(Account account) throws IOException {
		Statement stm;
		String sql = "select balance from users where cardID like '" + account.getCardID()
		+ "' and pin like '" + account.getPin() + "'";
		try {
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				return rs.getFloat("balance");
			}
		} catch (SQLException e) {
			System.out.println("Lỗi checkBalance");
		}
		return 0;
	}

	@Override
	public boolean updatePin(Account account, String pin) throws IOException {
		String sql = "update users set pin='" + pin + "' where cardId like '" + account.getCardID() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void withdraw(Account user, int amount) throws IOException {
	}

	@Override
	public void blockUser(String cardId) throws IOException {
		String sql = "update users set status=0 where cardID like '" + cardId + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkPin(Account account, String pin) throws IOException {
		String sql = "select * from users where cardId like '" + account.getCardID() + "' and pin like '" + pin + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next() && account.getPin().equals(pin)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
