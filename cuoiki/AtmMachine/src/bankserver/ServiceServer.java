package bankserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import irmi.IProcessDB;

public class ServiceServer {

	public static void main(String[] args) {
		ServerSocket ss;
		IProcessDB dao;
		Registry rgsty;
		try {
			ss = new ServerSocket(8000);
			rgsty = LocateRegistry.getRegistry(8080);
			dao = (IProcessDB) rgsty.lookup("rmi://localhost/database");
			System.out.println("Service Server đang hoạt động!");
			while (true) {
				Socket socket = ss.accept();
				ServiceProcessing rp = new ServiceProcessing(socket, dao);
				rp.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch ( NotBoundException e) {
			e.printStackTrace();
		}
	}
}
