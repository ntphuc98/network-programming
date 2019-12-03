package server1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IShapeServer1 extends Remote{
	int chuviTamGiac(int a, int b, int c) throws RemoteException;
	int chuviHCN(int a, int b) throws RemoteException;
	int chuviHV(int a) throws RemoteException;
}
