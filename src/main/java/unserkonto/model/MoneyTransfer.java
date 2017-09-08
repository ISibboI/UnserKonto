package unserkonto.model;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

public class MoneyTransfer implements Serializable {
	private static final long serialVersionUID = -6570559992255516858L;
	private final Money money;
	private final Entity partner;
	private final Date date;

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
		
		this.money = money;
		this.partner = partner;
		this.date = date;
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
		return "MoneyTransfer of " + money + " on " + date + " with " + partner;
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
}