package irmi;

import java.io.IOException;
import java.rmi.Remote;

import bean.Account;

public interface IProcessDB extends Remote {
	public boolean checkCardId(String cardId) throws IOException;

	public boolean checkActiveCardId(String cardId) throws IOException;

	public Account getUser(String cardId, String pin) throws IOException;

	public float checkBalance(Account user) throws IOException;

	public boolean checkPin(Account account, String pin) throws IOException;

	public boolean updatePin(Account account, String pin) throws IOException;

	public void withdraw(Account user, int amount) throws IOException;

	public void blockUser(String cardId) throws IOException;
}
