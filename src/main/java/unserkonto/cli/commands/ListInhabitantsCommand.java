package unserkonto.cli.commands;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;
import unserkonto.model.Inhabitant;

public class ListInhabitantsCommand extends Command {
	public ListInhabitantsCommand(CLI cli, String... names) {
		super(cli, names);
	}

	@Override
	public void execute(String parameters) {
		for (Inhabitant e: getFlat().getInhabitantManager().getInhabitants()) {
			System.out.println(e.getId() + ": " + e.getName());
		}
	}
}