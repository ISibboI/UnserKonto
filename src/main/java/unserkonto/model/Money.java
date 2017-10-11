package unserkonto.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class Money implements Serializable {
	private static final long serialVersionUID = 2854115104743760979L;

	private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("###,###,###,##0.00€");

	public static Money parseMoney(String moneyString) {
		moneyString = moneyString.trim();
		boolean euro = true;

		// Parse unit

		if (moneyString.startsWith("ct")) {
			euro = false;
			moneyString = moneyString.substring(2).trim();
		}

		if (moneyString.endsWith("ct")) {
			euro = false;
			moneyString = moneyString.substring(0, moneyString.length() - 2).trim();
		}

		if (moneyString.startsWith("€")) {
			moneyString = moneyString.substring(1).trim();
		}

		if (moneyString.endsWith("€")) {
			moneyString = moneyString.substring(0, moneyString.length() - 1).trim();
		}

		// Parse number

		char decimalSeparator = MONEY_FORMAT.getDecimalFormatSymbols().getDecimalSeparator();
		int decimalSeparatorIndex = moneyString.indexOf(decimalSeparator);

		if (decimalSeparatorIndex == -1) {
			decimalSeparatorIndex = moneyString.length();
		}

		// Before the decimal separator

		String precommaString = moneyString.substring(0, decimalSeparatorIndex);
		char groupingSeparator = MONEY_FORMAT.getDecimalFormatSymbols().getGroupingSeparator();
		precommaString = precommaString.replace(groupingSeparator + "", "");
		precommaString = precommaString.replaceAll("\\s+", "");

		int amount;

		try {
			amount = Integer.parseInt(precommaString);
		} catch (NumberFormatException e) {
			System.out.println("Wrong currency format for '" + moneyString
					+ "'. Problem is probably before the decimal separator");
			return null;
		}

		if (euro) {
			if (amount >= Integer.MAX_VALUE / 100) {
				System.out.println(
						"Number too big. Can only handle money up to about " + (Integer.MAX_VALUE / 100) + "€");
				return null;
			}

			amount *= 100;
		}

		// Behind the decimal separator

		String postcommaString = moneyString.substring(decimalSeparatorIndex);

		if (!euro && postcommaString.length() > 0) {
			System.out.println("Cannot handle broken cents");
			return null;
		}

		if (postcommaString.length() > 0) {
			postcommaString = postcommaString.substring(1);
		}

		postcommaString = postcommaString.replaceAll("\\s+", "");

		if (postcommaString.length() > 0) {
			int postcommaAmount;

			try {
				postcommaAmount = Integer.parseInt(postcommaString);
			} catch (NumberFormatException e) {
				System.out.println("Wrong currency format for '" + moneyString
						+ "'. Problem is probably behind the decimal separator");
				return null;
			}

			if (postcommaAmount < 0) {
				System.out.println("No minus sign allowed behind decimal separator");
				return null;
			}

			if (postcommaAmount < 10 && !postcommaString.startsWith("0")) {
				postcommaAmount *= 10;
			}

			if (postcommaAmount >= 100) {
				System.out.println("Can only handle up to two digits behind comma");
				return null;
			}

			if (amount < 0 || precommaString.indexOf(MONEY_FORMAT.getDecimalFormatSymbols().getMinusSign()) != -1) {
				postcommaAmount *= -1;
			}

			amount += postcommaAmount;
		}
		
		return new Money(amount);
	}

	private final int amount;

	public Money() {
		amount = 0;
	}

	public Money(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return MONEY_FORMAT.format(amount / 100.0);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Money) {
			Money m = (Money) o;
			return m.amount == amount;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return amount;
	}

	public Money add(int amount) {
		return add(new Money(amount));
	}

	public Money add(Money other) {
		int sum = amount + other.amount;
		int signum = other.amount > 0 ? 1 : other.amount < 0 ? -1 : 0;

		if (signum * sum < signum * amount) {
			throw new OverflowException("add", amount, other.amount);
		}

		return new Money(sum);
	}

	public Money subtract(int amount) {
		return add(new Money(-amount));
	}

	public Money subtract(Money other) {
		return add(new Money(-other.amount));
	}

	public Collection<Money> divide(int divisor) {
		if (divisor < 1) {
			throw new IllegalArgumentException("Divisor must be greater than zero");
		}

		Collection<Money> result = new ArrayList<>(divisor);

		int signum = amount < 0 ? -1 : 1;
		int absoluteAmount = signum * amount;
		int quotient = amount / divisor;
		int remainder = absoluteAmount % divisor;

		for (int i = 0; i < remainder; i++) {
			result.add(new Money(quotient + signum));
		}

		for (int i = remainder; i < divisor; i++) {
			result.add(new Money(quotient));
		}

		return result;
	}

	public boolean isPositive() {
		return amount > 0;
	}
	
	public boolean isZero() {
		return amount == 0;
	}
	
	public boolean isNegative() {
		return amount < 0;
	}
}