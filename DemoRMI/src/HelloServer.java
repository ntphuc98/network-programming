package server;

import java.rmi.Naming;

public class HelloServer {
	public static void main(String args[]) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Hello obj = new Hello();
			Naming.rebind("rmi://localhost:5000/hello", obj);
			System.out.println("HelloObject is registried");
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
}
