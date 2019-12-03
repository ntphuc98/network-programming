package main;

import chat.Chat;

public class May1 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chat server = new Chat(8080, 8000);
		server.start();
	}
}
