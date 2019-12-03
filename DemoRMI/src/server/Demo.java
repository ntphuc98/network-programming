package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Demo extends UnicastRemoteObject implements IDemo {

	protected Demo() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int tong(int a, int b) throws RemoteException {
		return a + b;
	}

	@Override
	public int hieu(int a, int b) throws RemoteException {
		return a - b;
	}

	@Override
	public int tich(int a, int b) throws RemoteException {
		return a * b;
	}

	@Override
	public float thuong(int a, int b) throws RemoteException {
		return (float) a / (float) b;
	}

}
