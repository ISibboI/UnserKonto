package unserkonto.cli.commands;

import java.util.Arrays;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;

public class ExitCommand implements Command {
	private final CLI cli;
	
	public ExitCommand(CLI cli) {
		this.cli = cli;
	}
	
	@Override
	public String[] getNames() {
		return new String[] {"e", "q", "exit", "quit", "end"};
	}

	@Override
	public void execute(String parameters) {
		new SaveCommand(cli.getAccount()).execute(parameters);
		cli.exit();
	}
}