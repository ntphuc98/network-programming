package client;

import java.rmi.Naming;

import server.IHello;

public class Client {
	public static void main(String args[]) {
		String helloURL = "rmi://localhost/HelloObject";
		IHello object = null;
		try {
			object = (IHello) Naming.lookup(helloURL);
			String message = object.sayHello();
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Client Error :" + e);
		}
	}
}
