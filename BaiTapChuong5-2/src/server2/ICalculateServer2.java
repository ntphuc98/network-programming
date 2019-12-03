package server2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculateServer2 extends Remote{
	int multiply(int a, int b) throws RemoteException;
	float divide(int a, int b) throws RemoteException;
}
