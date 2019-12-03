package server2;

import java.rmi.Remote;
import java.rmi.RemoteException;



public interface IStringServer2 extends Remote {
	String to_title(String str) throws RemoteException;
}
