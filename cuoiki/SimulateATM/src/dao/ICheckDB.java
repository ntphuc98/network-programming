package dao;

import java.io.IOException;
import java.rmi.Remote;

import model.Account;

public interface ICheckDB extends Remote {
	public boolean checkCardId(String cardId) throws IOException;

	public Account getAccount(String cardId, String pin) throws IOException;
}
