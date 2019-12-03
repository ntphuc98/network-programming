package server1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShapeServer1 extends UnicastRemoteObject implements IShapeServer1{
	public ShapeServer1() throws RemoteException {
		super();
	}
	@Override
	public int chuviTamGiac(int a, int b, int c) throws RemoteException {
		if (a >= b + c || b >= a + c || c >= a + b)
	        return 0;
		return a + b + c;
	}
	@Override
	public int chuviHCN(int a, int b) throws RemoteException {
		return (a + b) * 2;
	}
	@Override
	public int chuviHV(int a) throws RemoteException {
		return a * 4;
	}
}
