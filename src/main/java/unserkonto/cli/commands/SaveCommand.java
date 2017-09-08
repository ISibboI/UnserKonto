package unserkonto.cli.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;
import unserkonto.model.Account;
import unserkonto.model.EntityManager;

public class SaveCommand implements Command {
	private final Account account;
	
	public SaveCommand(Account account) {
		this.account = account;
	}

	@Override
	public String[] getNames() {
		return new String[] {"save"};
	}

	@Override
	public void execute(String parameters) {
		File accountFile = new File("account.jso");
		
		if (accountFile.exists()) {
			accountFile.delete();
		}
		
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(accountFile));
			EntityManager.getInstance().writeEntities(out);
			out.writeObject(account);
			System.out.println("Account data saved");
		} catch (IOException e) {
			System.out.println("Could not save account data");
			e.printStackTrace();
		}
	}
}