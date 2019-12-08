package bean;

import java.io.Serializable;

public class Account implements Serializable{
	private String name;
	private String cardID;
	private String pin;
	public Account() {
		
	}
	
	public Account(String name, String cardID, String pin) {
		super();
		this.name = name;
		this.cardID = cardID;
		this.pin = pin;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	
}
