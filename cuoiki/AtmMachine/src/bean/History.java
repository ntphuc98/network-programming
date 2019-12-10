package bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;

public class History implements Serializable {
	private int type;
	private double amount;
	private String other_user;
	private String note;
	private Timestamp cr_date;

	public History() {
		super();
		// TODO Auto-generated constructor stub
	}

	public History(int type, double amount, String other_user, String note, Timestamp cr_date) {
		super();
		this.type = type;
		this.amount = amount;
		this.other_user = other_user;
		this.note = note;
		this.cr_date = cr_date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAmount() {
		return DecimalFormat.getInstance().format(amount);
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOther_user() {
		return other_user;
	}

	public void setOther_user(String other_user) {
		this.other_user = other_user;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getCr_date() {
		return cr_date;
	}

	public void setCr_date(Timestamp cr_date) {
		this.cr_date = cr_date;
	}

}
