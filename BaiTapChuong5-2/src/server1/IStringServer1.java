package server1;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IStringServer1  extends Remote{
	String to_lower(String str) throws RemoteException;
	String to_upper(String str) throws RemoteException;
}
