package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAO;

/*
 * Nguyen Thanh Phuc
 * github.com/ntphuc98
 */
public class Bank extends DAO implements Serializable {
	private int id;
	private double balance = 0;

	public Bank(int id, double balance) {
		super();
		this.id = id;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public double getBalance() {
		Statement stm;
		String sql = "select balance from bank where id=" + id;
		try {
			stm = getConnection().createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				balance = rs.getFloat("balance");
			}
			stm.close();
		} catch (SQLException e) {
			System.out.println("Lỗi getBalance");
		}
		return balance;
	}

	public synchronized boolean withdraw(int amount) {
		synchronized (this) {
			balance = getBalance();
			if ((balance - amount) >= 50000) {
				if (updateBalance(balance - amount)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	private boolean updateBalance(double balance) {
		String sql = "update bank set balance=" + balance + " where id=" + id;
		Statement stmt;
		try {
			stmt = getConnection().createStatement();
			try {
				stmt.executeUpdate(sql);
				stmt.close();
				return true;
			} catch (SQLException e) {
				stmt.close();
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public synchronized boolean deposit(int amount) {
		synchronized (this) {
			balance = balance + amount;
			if (updateBalance(balance)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public List<History> getHistories() {
		List<History> historyList = new ArrayList<>();
		Statement stm;
		try {
			stm = getConnection().createStatement();
			String sql = "SELECT * FROM" + " (" + "	SELECT * FROM histories where id_bank=" + id
					+ " order by cr_date desc limit 5" + " ) SUB" + " order by cr_date";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				History history = new History(rs.getInt("id"), rs.getInt("id_bank"), rs.getInt("type"),
						rs.getDouble("amount"), rs.getDouble("last_balance"), rs.getString("note"),
						rs.getTimestamp("cr_date"), rs.getString("by_account"));
				historyList.add(history);
			}
			stm.close();
		} catch (SQLException e) {
			System.out.println("Lỗi getHistories");
		}
		return historyList;
	}

	// static method
	public static Bank getByCardId(String cardId) {
		Statement stm;
		String sql = "SELECT bank.id AS id, bank.balance AS balance " + "FROM bank inner join account "
				+ "ON bank.id = account.bank_id " + "WHERE account.card_id like '" + cardId + "'";
		try {
			stm = getConnection().createStatement();
			try {
				ResultSet rs = stm.executeQuery(sql);
				if (rs.next()) {
					return new Bank(rs.getInt("id"), rs.getDouble("balance"));
				}
				stm.close();
			} catch (SQLException e) {
				stm.close();
				System.out.println("Lỗi getByCardId");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static List<Bank> getAll() {
		List<Bank> bankList = new ArrayList<>();
		Statement stm;
		try {
			stm = getConnection().createStatement();
			ResultSet rs = stm.executeQuery("select * from bank");
			while (rs.next()) {
				Bank bank = new Bank(rs.getInt("id"), rs.getDouble("balance"));
				bankList.add(bank);
			}
		} catch (SQLException e) {
			System.out.println("Lỗi getAll");
		}
		return bankList;
	}
}
