package unserkonto.cli.commands;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;
import unserkonto.model.MoneyTransfer;

public class ListTransfersCommand extends Command {
	public ListTransfersCommand(CLI cli, String... names) {
		super(cli, names);
	}

	@Override
	public void execute(String parameters) {
		System.out.println("All money transfers for '" + getAccount().getName() + "'");
		
		for (MoneyTransfer t: getAccount().getTransfers()) {
			System.out.println(t);
		}
	}

}
