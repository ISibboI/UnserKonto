package unserkonto.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import unserkonto.cli.commands.ChangeAccountNameCommand;
import unserkonto.cli.commands.CreateEntityCommand;
import unserkonto.cli.commands.CreateTransferCommand;
import unserkonto.cli.commands.ExitCommand;
import unserkonto.cli.commands.HelpCommand;
import unserkonto.cli.commands.ListEntitiesCommand;
import unserkonto.cli.commands.ListTransfersCommand;
import unserkonto.cli.commands.SaveCommand;
import unserkonto.model.Account;
import unserkonto.model.EntityManager;

public class CLI implements Runnable {
	public static void main(String[] args) {
		new CLI().run();
	}

	private boolean running = true;
	private Map<String, Command> commandsByName = new HashMap<>();
	private Set<Command> commands = new HashSet<>();

	private final Account account;

	public CLI() {
		File accountFile = new File("account.jso");

		Account account = null;
		
		if (accountFile.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(accountFile));
				EntityManager.getInstance().readEntities(in);
				account = (Account) in.readObject();
				System.out.println("Loaded account '" + account.getName() + "'. Type ? for help");
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error reading account data");
				e.printStackTrace();
			}
		} else {
			account = new Account();
			System.out.println("Created new account. Type ? for help");
		}
		
		this.account = account;

		addCommand(new ExitCommand(this));
		addCommand(new SaveCommand(account));
		addCommand(new HelpCommand(this));
		
		addCommand(new ChangeAccountNameCommand(account));
		
		addCommand(new CreateEntityCommand(account));
		addCommand(new ListEntitiesCommand());
		
		addCommand(new CreateTransferCommand(account));
		addCommand(new ListTransfersCommand(account));
	}

	public void run() {
		Scanner scanner = new Scanner(System.in);

		while (running) {
			System.out.print("> ");
			String line = scanner.nextLine().trim();

			int commandEnd = line.indexOf(' ');
			String parameters = "";
			String commandName = line;

			if (commandEnd != -1) {
				commandName = line.substring(0, commandEnd);
				parameters = line.substring(commandEnd + 1).trim();
			}

			if (commandName.isEmpty()) {
				continue;
			}

			Command command = commandsByName.get(commandName);

			if (command == null) {
				System.out.println("No such command: '" + commandName + "'");
			} else {
				command.execute(parameters);
			}
		}
	}

	public void exit() {
		running = false;
	}

	public Account getAccount() {
		return account;
	}
	
	public Collection<Command> getCommands() {
		return Collections.unmodifiableSet(commands);
	}

	public void addCommand(Command command) {
		for (String name : command.getNames()) {
			if (commandsByName.containsKey(name)) {
				throw new RuntimeException("Duplicate command name: " + name + " in commands " + command + " and "
						+ commandsByName.get(name));
			}

			commandsByName.put(name, command);
		}
		
		commands.add(command);
	}
}
