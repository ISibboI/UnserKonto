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

import unserkonto.cli.commands.ChangeFlatNameCommand;
import unserkonto.cli.commands.CreateInhabitantCommand;
import unserkonto.cli.commands.CreateTransferCommand;
import unserkonto.cli.commands.ExitCommand;
import unserkonto.cli.commands.HelpCommand;
import unserkonto.cli.commands.ListInhabitantsCommand;
import unserkonto.cli.commands.ListTransfersCommand;
import unserkonto.cli.commands.SaveCommand;
import unserkonto.model.Flat;

public class CLI implements Runnable {
	public static void main(String[] args) {
		new CLI().run();
	}

	private boolean running = true;
	private Map<String, Command> commandsByName = new HashMap<>();
	private Set<Command> commands = new HashSet<>();

	private Flat flat;

	public CLI() {
		File accountFile = new File("account.jso");

		if (accountFile.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(accountFile))) {
				flat = (Flat) in.readObject();
				System.out.println("Loaded flat '" + flat.getName() + "'. Type ? for help");
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error reading account data");
				e.printStackTrace();
			}
		} else {
			flat = new Flat("");
			System.out.println("Created new account. Type ? for help");
		}

		addCommand(new ExitCommand(this, "e", "q", "exit", "quit", "end"));
		addCommand(new SaveCommand(this, "save"));
		addCommand(new HelpCommand(this, "help", "h", "?"));

		addCommand(new ChangeFlatNameCommand(this, "changeFlatName"));

		addCommand(new CreateInhabitantCommand(this, "createInhabitant", "addInhabitant"));
		addCommand(new ListInhabitantsCommand(this, "listInhabitants"));

		addCommand(new CreateTransferCommand(this, "createTransfer", "addTransfer"));
		addCommand(new ListTransfersCommand(this, "listTransfers", "listMoneyTransfers"));
	}

	@Override
	public void run() {
		try (Scanner scanner = new Scanner(System.in)) {
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
	}

	public void exit() {
		running = false;
	}

	public Flat getFlat() {
		return flat;
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
