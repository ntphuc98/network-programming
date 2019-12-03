package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.IDemo;

public class Client {

	public static void main(String[] args) {
		try {
			Registry rgsty = LocateRegistry.getRegistry(8000);
			IDemo demo = (IDemo) rgsty.lookup("rmi://localhost/tinhtoan");
			
			int a = demo.tong(10, 11);
			System.out.println(a);
			
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
