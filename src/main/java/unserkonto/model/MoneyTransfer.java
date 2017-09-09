package unserkonto.model;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class MoneyTransfer implements Serializable {
	private static final long serialVersionUID = -6570559992255516858L;
	private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
	private final Money money;
	private final Entity partner;
	private final Date date;
	private Date dueDate;

	@SuppressWarnings("unused")
	private MoneyTransfer() {
		money = null;
		partner = null;
		date = null;
	}

	public MoneyTransfer(Money money, Entity partner, Date date) {
		Objects.requireNonNull(money, "money");
		Objects.requireNonNull(partner, "partner");
		Objects.requireNonNull(date, "date");
		
		if (money.isZero()) {
			throw new IllegalArgumentException("Money is zero");
		}
		
		this.money = money;
		this.partner = partner;
		this.date = date;
		setDueDate(date);
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(money);
		out.writeInt(partner.getId());
		out.writeObject(date);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		Object m = in.readObject();
		int id = in.readInt();
		Object d = in.readObject();
		Object dd = in.readObject();
		
		try {
			Field money = getClass().getDeclaredField("money");
			money.setAccessible(true);
			money.set(this, m);
			money.setAccessible(false);
			
			Field partner = getClass().getDeclaredField("partner");
			partner.setAccessible(true);
			partner.set(this, EntityManager.getInstance().getEntity(id));
			partner.setAccessible(false);
			
			Field date = getClass().getDeclaredField("date");
			date.setAccessible(true);
			date.set(this, d);
			date.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			throw new IOException("Cannot create money transfer object");
		}
		
		if (dd != null) {
			dueDate = (Date) dd;
		} else {
			dueDate = date;
		}
	}

	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
		throw new UnsupportedOperationException("There can't be null money transfers");
	}

	public Money getMoney() {
		return money;
	}

	public Entity getPartner() {
		return partner;
	}

	public Date getDate() {
		return date;
	}
	
	public String toString() {
		String tofrom = money.isPositive() ? " from " : " to ";
		
		return "MoneyTransfer of " + money + " on " + DATE_FORMAT.format(date) + tofrom + partner + " due at " + DATE_FORMAT.format(dueDate);
	}
	
	public boolean equals(Object o) {
		if (o instanceof MoneyTransfer) {
			MoneyTransfer t = (MoneyTransfer) o;
			return t.money.equals(money) && t.partner.equals(partner) && t.date.equals(date);
		}
		
		return false;
	}
	
	public int hashCode() {
		return money.hashCode() ^ partner.hashCode() ^ date.hashCode();
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		Objects.requireNonNull(dueDate);
		this.dueDate = dueDate;
	}
}