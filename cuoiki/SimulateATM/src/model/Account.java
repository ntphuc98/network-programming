package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.DAO;

public class Account extends DAO implements Serializable {
	private int id;
	private String name;
	private String cardId;
	private String pin;
	private int role;
	private int bankId;

	public Account(int id, String name, String cardId, String pin, int role, int bankId) {
		super();
		this.id = id;
		this.name = name;
		this.cardId = cardId;
		this.pin = pin;
		this.role = role;
		this.bankId = bankId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCardId() {
		return cardId;
	}

	public String getPin() {
//		String sql = "select pin from account where cardId like '" + cardId;
//		Statement stmt;
//		try {
//			stmt = getConnection().createStatement();
//			ResultSet rs = stmt.executeQuery(sql);
//			if (rs.next()) {
//				pin = rs.getString("pin");
//			}
//		} catch (SQLException e) {
//			System.out.println("Lỗi checkPin");
//		}
		return pin;
	}

	public String getFormDB() {
		return null;
	}

	public boolean updatePin(String newPin) {
		String sql = "update account set pin='" + newPin + "' where card_id like '" + cardId + "' and pin like '"
				+ this.pin + "'";
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

	public int getRole() {
		return role;
	}

	public int getBankId() {
		return bankId;
	}

	@Override
	public String toString() {
		return "Card Number: " + cardId + " , Name: " + name;
	}

}
