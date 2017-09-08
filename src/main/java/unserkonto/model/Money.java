package unserkonto.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class Money implements Serializable {
	private static final long serialVersionUID = 2854115104743760979L;

	private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("###,###,###,##0.00€");
	
	private final int amount;
	
	public Money() {
		amount = 0;
	}
	
	public Money(int amount) {
		this.amount = amount;
	}
	
	public String toString() {
		return MONEY_FORMAT.format(amount);
	}
	
	public boolean equals(Object o) {
		if (o instanceof Money) {
			Money m = (Money) o;
			return m.amount == amount;
		}
		
		return false;
	}
	
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
}