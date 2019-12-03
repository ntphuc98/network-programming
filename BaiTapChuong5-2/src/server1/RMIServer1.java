package server1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer1 {
	public static void main(String[] args) {
		try {
			Registry rgsty = LocateRegistry.createRegistry(8000);
			rgsty.rebind("rmi://localhost/calculate", new CalculateServer1());
			rgsty.rebind("rmi://localhost/shape", new ShapeServer1());
			rgsty.rebind("rmi://localhost/string", new StringServer1());
			System.out.println("Server 1 RMI đang hoạt động...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
