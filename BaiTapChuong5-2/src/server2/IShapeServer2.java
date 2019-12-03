package server2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IShapeServer2 extends Remote{
	float dientichTamGiac(int a, int b, int c) throws RemoteException;

	int dientichHCN(int a, int b) throws RemoteException;

	int dientichHV(int a) throws RemoteException;
}
