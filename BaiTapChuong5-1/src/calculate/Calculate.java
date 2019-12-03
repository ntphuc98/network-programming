package calculate;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Calculate extends UnicastRemoteObject implements ICalculate {

	public Calculate() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int add(int a, int b) throws RemoteException {
		return a + b;
	}

	@Override
	public int sub(int a, int b) throws RemoteException {
		return a - b;
	}

	@Override
	public int multiply(int a, int b) throws RemoteException {
		return a * b;
	}

	@Override
	public float divide(int a, int b) throws RemoteException {
		return b == 0 ? 0 : (float) a / (float) b;
	}

}
