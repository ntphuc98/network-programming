package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Hello extends UnicastRemoteObject implements IHello {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Hello() throws RemoteException {
		super();
	}
	public String sayHello() {
		return "Hello World !";
	}

}
