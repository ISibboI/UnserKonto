package unserkonto.cli.commands;

import unserkonto.cli.Command;
import unserkonto.model.Account;
import unserkonto.model.MoneyTransfer;

public class ListTransfersCommand implements Command {
	private Account account;
	
	public ListTransfersCommand(Account account) {
		this.account = account;
	}
	
	@Override
	public String[] getNames() {
		return new String[] {"listTransfers", "listMoneyTransfers"};
	}

	@Override
	public void execute(String parameters) {
		System.out.println("All money transfers for '" + account.getName() + "'");
		
		for (MoneyTransfer t: account.getTransfers()) {
			System.out.println(t);
		}
	}

}
