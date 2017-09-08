package unserkonto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements Serializable {
	private static final long serialVersionUID = -868075282146734105L;
	private final List<MoneyTransfer> transfers = new ArrayList<>();
	private Money currentBalance = new Money(0);
	
	public void addTransfer(MoneyTransfer t) {
		transfers.add(t);
		currentBalance = currentBalance.add(t.getMoney());
	}
	
	public List<MoneyTransfer> getTransfers() {
		return Collections.unmodifiableList(transfers);
	}
	
	public Money getCurrentBalance() {
		return currentBalance;
	}
}