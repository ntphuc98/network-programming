package server1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculateServer1 extends UnicastRemoteObject implements ICalculateServer1{
	public CalculateServer1() throws RemoteException {
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
}
