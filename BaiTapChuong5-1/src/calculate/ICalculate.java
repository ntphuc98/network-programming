package calculate;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculate extends Remote{
	int add(int a, int b) throws RemoteException;
	int sub(int a, int b) throws RemoteException;
	int multiply(int a, int b) throws RemoteException;
	float divide(int a, int b) throws RemoteException;
}