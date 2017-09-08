package unserkonto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Account implements Serializable {
	private static final long serialVersionUID = -868075282146734105L;
	private final List<MoneyTransfer> transfers = new ArrayList<>();
	private Money currentBalance = new Money(0);
	private String name = "";
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Objects.requireNonNull(name);
		this.name = name;
	}
}