package service;

import java.text.DecimalFormat;

public class Service {
	public static boolean isValidPin(String pin) {
		if (pin.length() == 6 && isNumber(pin))
			return true;
		return false;
	}

	public static boolean isConfirmPIN(String pin, String comfirmPin) {
		if (isValidPin(comfirmPin) && pin.equals(comfirmPin))
			return true;
		return false;
	}

	public static boolean isNumber(String str) {
		String regex = "[0-9]+";
		return str.matches(regex);
	}

	public static int toNumber(String input) {
		return Integer.parseInt(input);
	}
	public static String toVNDCurrency(double input) {
		return DecimalFormat.getInstance().format(input);
	}
}
