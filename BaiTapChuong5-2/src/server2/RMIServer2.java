package server2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer2 {

	public static void main(String[] args) {
		Registry rgsty;
		try {
			rgsty = LocateRegistry.createRegistry(8080);
			rgsty.rebind("rmi://localhost/calculate", new CalculateServer2());
			rgsty.rebind("rmi://localhost/shape", new ShapeServer2());
			rgsty.rebind("rmi://localhost/string", new StringServer2());
			System.out.println("Server 2 RMI đang hoạt động...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
