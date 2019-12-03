package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDemo extends Remote {
	// nên throws RemoteException
	int tong(int a, int b) throws RemoteException;

	int hieu(int a, int b) throws RemoteException;

	int tich(int a, int b) throws RemoteException;

	float thuong(int a, int b) throws RemoteException;
	
}
