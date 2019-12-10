package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import dao.ICheckDB;
import model.Bank;

public class ServerMain extends DAO{
	public static void main(String[] args) {
		ServerSocket ss;
		ICheckDB dao;
		Registry rgsty;
		ServerMain serverMain = new ServerMain();
		List<Bank> bankList = Bank.getAll();
		try {
			ss = new ServerSocket(8000);
			rgsty = LocateRegistry.getRegistry(8080);
			dao = (ICheckDB) rgsty.lookup("rmi://localhost/dao");
			System.out.println("Service Server đang hoạt động!");
			while (true) {
				Socket socket = ss.accept();
				Transaction transaction = new Transaction(socket, bankList, dao);
				transaction.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		DAO.close();
	}
}
