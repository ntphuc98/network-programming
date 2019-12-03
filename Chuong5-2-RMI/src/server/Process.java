package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import imethod.IProcess1;

public class Process extends UnicastRemoteObject implements IProcess1 {
	public Process() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public float calculate(char operator, int a, int b) throws RemoteException {
		float result = 0 ;
		switch (operator) {
		case '+':
			return a+b;
		case '-':
			return a-b;
		case '*':
			return a*b;
		case '/':
			return (float)a/(float)b;
		}
		return result;
	}
	@Override
	public int sRectangle(int a, int b) throws RemoteException {
		return a * b;
	}
	@Override
	public int pRectangle(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return (a + b) * 2;
	}

	
}
