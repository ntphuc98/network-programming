package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) {
		try {
			Registry rgsty = LocateRegistry.createRegistry(8000);
			rgsty.rebind("rmi://localhost/tinhtoan", new Demo());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
