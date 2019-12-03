package shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Shape extends UnicastRemoteObject implements IShape {

	public Shape() throws RemoteException {
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

	@Override
	public float dientichTamGiac(int a, int b, int c) throws RemoteException {
		if (a >= b + c || b >= a + c || c >= a + b)
	        return 0;
		float p = (float) chuviTamGiac(a, b, c) / 2;
		return (float) Math.sqrt(p * (p - a) * (p - b) * (p - c));
	}

	@Override
	public int dientichHCN(int a, int b) throws RemoteException {
		return a * b;
	}

	@Override
	public int dientichHV(int a) throws RemoteException {
		return a * a;
	}

}
