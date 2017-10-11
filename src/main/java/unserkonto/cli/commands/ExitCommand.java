package unserkonto.cli.commands;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;

public class ExitCommand extends Command {
	public ExitCommand(CLI cli, String... names) {
		super(cli, names);
	}
	
	@Override
	public void execute(String parameters) {
		new SaveCommand(getCLI()).execute(parameters);
		getCLI().exit();
	}
}