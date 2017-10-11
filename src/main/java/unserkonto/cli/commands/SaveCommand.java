package unserkonto.cli.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;

public class SaveCommand extends Command {
	public SaveCommand(CLI cli, String... names) {
		super(cli, names);
	}

	@Override
	public void execute(String parameters) {
		File accountFile = new File("account.jso");
		
		if (accountFile.exists()) {
			accountFile.delete();
		}
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(accountFile))) {
			out.writeObject(getFlat());
			System.out.println("Account data saved");
		} catch (IOException e) {
			System.out.println("Could not save account data");
			e.printStackTrace();
		}
	}
}