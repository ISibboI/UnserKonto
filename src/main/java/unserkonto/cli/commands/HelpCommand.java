package unserkonto.cli.commands;

import java.util.Arrays;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;

public class HelpCommand extends Command {
	public HelpCommand(CLI cli, String... names) {
		super(cli, names);
	}

	@Override
	public void execute(String parameters) {
		System.out.println("The following commands are available:");
		
		for (Command c: getCLI().getCommands()) {
			System.out.println(Arrays.toString(c.getNames()));
		}
	}
}