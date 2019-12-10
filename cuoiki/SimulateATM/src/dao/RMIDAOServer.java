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

	protected RMIDAOServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
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
			stm = DAO.getConnection().createStatement();
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
			stm = DAO.getConnection().createStatement();
			ResultSet rs = stm.executeQuery(
					"select * from account where card_id like '" + cardId + "' and pin like '" + pin + "'");
			if (rs.next()) {
				Account account = new Account(rs.getInt("id"), rs.getString("name"), rs.getString("card_id"), pin,
						rs.getInt("role"), rs.getInt("bank_id"));
				return account;
			}
		} catch (SQLException e) {
			System.out.println("Lỗi getAccount");
		}
		return null;
	}
}
