package imethod;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IProcess1 extends Remote{
	float calculate(char operator, int a, int b) throws RemoteException;
	int sRectangle(int a, int b) throws RemoteException;
	int pRectangle(int a, int b) throws RemoteException;
}
