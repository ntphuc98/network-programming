package model;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import dao.DAO;

public class History extends DAO implements Serializable {
	private int id;
	private int bankId;
	private int type;
	private double amount;
	private double lastBalance;
	private String note;
	private Timestamp cr_date;
	private String byAccount;

	public History() {

	}

	public History(int id, int bankId, int type, double amount, double lastBalance, String note, Timestamp cr_date,
			String byAccount) {
		super();
		this.id = id;
		this.bankId = bankId;
		this.type = type;
		this.amount = amount;
		this.lastBalance = lastBalance;
		this.note = note;
		this.cr_date = cr_date;
		this.byAccount = byAccount;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	public String getNote() {
		return note;
	}

	public Timestamp getCr_date() {
		return cr_date;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setCr_date(Timestamp cr_date) {
		this.cr_date = cr_date;
	}

	public String getByAccount() {
		return byAccount;
	}

	public void setByAccount(String byAccount) {
		this.byAccount = byAccount;
	}

	public double getLastBalance() {
		return lastBalance;
	}

	public void setLastBalance(double lastBalance) {
		this.lastBalance = lastBalance;
	}

	public boolean save() {
		String sql = "INSERT INTO histories(id_bank, type, amount, last_balance, note, by_account) VALUES ( " + bankId
				+ ", " + type + ", " + amount + ", " + lastBalance + ", '" + note + "', '" + byAccount + "' )";
		Statement stmt;
		try {
			stmt = getConnection().createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
