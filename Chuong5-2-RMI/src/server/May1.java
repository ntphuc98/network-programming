package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class May1 {

	public static void main(String[] args) {
		try {
			Registry rgsty = LocateRegistry.createRegistry(8000);
			rgsty.rebind("rmi://localhost/process", new Process());
			System.out.println("Máy 1 RMI đang hoạt động...");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
