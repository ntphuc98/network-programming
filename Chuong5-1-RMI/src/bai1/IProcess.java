package bai1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IProcess extends Remote{
	float calculate(char operator, int a, int b) throws RemoteException;
	int sRectangle(int a, int b) throws RemoteException;
	float sRectangle(float a, float b) throws RemoteException;
	String to_lower(String str) throws RemoteException;
	String to_upper(String str) throws RemoteException;
	String to_title(String str) throws RemoteException;
}
