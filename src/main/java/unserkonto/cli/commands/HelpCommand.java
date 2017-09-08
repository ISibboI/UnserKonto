package unserkonto.cli.commands;

import java.util.Arrays;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;

public class HelpCommand implements Command {
	private CLI cli;
	
	public HelpCommand(CLI cli) {
		this.cli = cli;
	}
	
	@Override
	public String[] getNames() {
		return new String[] {"help", "h", "?"};
	}

	@Override
	public void execute(String parameters) {
		System.out.println("The following commands are available:");
		
		for (Command c: cli.getCommands()) {
			System.out.println(Arrays.toString(c.getNames()));
		}
	}
}