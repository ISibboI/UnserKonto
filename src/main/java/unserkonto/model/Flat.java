package unserkonto.model;

import java.io.IOException;
import java.io.Serializable;

public class Flat implements Serializable {
	private static final long serialVersionUID = -1627422224755887046L;

	private String name;
	private Account account;
	private InhabitantManager inhabitantManager;

	public Flat(Account account, InhabitantManager inhabitantManager) {
		this.account = account;
		this.inhabitantManager = inhabitantManager;
	}

	public Flat(String name) {
		this.name = name;
		account = new Account();
		inhabitantManager = new InhabitantManager();
	}

	@SuppressWarnings("unused")
	private Flat() {
	}

	public String getName() {
		return name;
	}

	public Account getAccount() {
		return account;
	}

	public InhabitantManager getInhabitantManager() {
		return inhabitantManager;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(name);
		inhabitantManager.writeInhabitants(out);
		out.writeObject(account);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		name = (String) in.readObject();
		inhabitantManager = new InhabitantManager();
		inhabitantManager.readInhabitants(in);
		account = (Account) in.readObject();
	}
}
