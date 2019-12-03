package bai1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Process extends UnicastRemoteObject implements IProcess{
	public Process() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int add(int a, int b) throws RemoteException {
		return a + b;
	}

	@Override
	public int sub(int a, int b) throws RemoteException {
		return a - b;
	}
	public String to_lower(String str) throws RemoteException {
		char s[] = str.toCharArray();
		for (int i = 0; i < s.length; i++) {
			if (s[i] >= 65 && s[i] <= 90) {
				s[i] = (char) (s[i] + 32);
			}
		}
		return new String(s);
	}

	public String to_upper(String str) throws RemoteException {
		char s[] = str.toCharArray();
		for (int i = 0; i < s.length; i++) {
			if (s[i] >= 'a' && s[i] <= 'z') {
				s[i] = (char) (s[i] - 32);
			}
		}
		return new String(s);
	}

	public String to_title(String str) throws RemoteException {
		char s[] = str.toCharArray();
		
		for (int i = 0; i < s.length; i++) {
			if (s[i] >= 'A' && s[i] <= 'Z')
				s[i] += 32;
			if ( i == 0 || s[i - 1] == ' ') {
				if (s[i] >= 'a' && s[i] <= 'z')
					s[i] = (char) (s[i] - 32);
			}
		}
		return new String(s);
	}
}
