package unserkonto.model;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class MoneyTransfer implements Serializable {
	private static final long serialVersionUID = -6570559992255516858L;
	private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
	private Money money;
	private int partnerId;
	private Date date;
	private Date dueDate;

	@SuppressWarnings("unused")
	private MoneyTransfer() {
		money = null;
		partnerId = -1;
		date = null;
	}

	public MoneyTransfer(Money money, Inhabitant partner, Date date) {
		Objects.requireNonNull(money, "money");
		Objects.requireNonNull(partner, "partner");
		Objects.requireNonNull(date, "date");
		
		if (money.isZero()) {
			throw new IllegalArgumentException("Money is zero");
		}
		
		this.money = money;
		this.partnerId = partner.getId();
		this.date = date;
		setDueDate(date);
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(money);
		out.writeInt(partnerId);
		out.writeObject(date);
		out.writeObject(dueDate);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		money = (Money) in.readObject();
		partnerId = in.readInt();
		date = (Date) in.readObject();
		dueDate = (Date) in.readObject();
	}

	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
		throw new UnsupportedOperationException("There can't be null money transfers");
	}

	public Money getMoney() {
		return money;
	}

	public int getPartnerId() {
		return partnerId;
	}
	
	public Inhabitant getPartner(InhabitantManager entityManager) {
		return entityManager.getEntity(partnerId);
	}

	public Date getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		String tofrom = money.isPositive() ? " from " : " to ";
		
		return "MoneyTransfer of " + money + " on " + DATE_FORMAT.format(date) + tofrom + partnerId + " due at " + DATE_FORMAT.format(dueDate);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MoneyTransfer) {
			MoneyTransfer t = (MoneyTransfer) o;
			return t.money.equals(money) && t.partnerId == partnerId && t.date.equals(date);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return money.hashCode() ^ partnerId ^ date.hashCode();
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		Objects.requireNonNull(dueDate);
		this.dueDate = dueDate;
	}
}