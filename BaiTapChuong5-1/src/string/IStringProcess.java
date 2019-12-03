package string;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IStringProcess extends Remote{
	String to_lower(String str) throws RemoteException;
	String to_upper(String str) throws RemoteException;
	String to_title(String str) throws RemoteException;
}
