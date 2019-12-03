package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import calculate.Calculate;
import shape.Shape;
import string.StringProcess;

public class RMIServer {

	public static void main(String[] args) {
		Registry rgsty;
		try {
			rgsty = LocateRegistry.createRegistry(8000);
			rgsty.rebind("rmi://localhost/calculate", new Calculate());
			rgsty.rebind("rmi://localhost/shape", new Shape());
			rgsty.rebind("rmi://localhost/string", new StringProcess());
			System.out.println("Server RMI đang hoạt động...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
