package unserkonto.cli.commands;

import unserkonto.model.Account;
import unserkonto.model.Entity;
import unserkonto.model.EntityManager;
import unserkonto.model.Money;

public class CreateTransferCommand extends ParameterCommand {
	public CreateTransferCommand(Account account) {
		super(account);
	}

	@Override
	public String[] getNames() {
		return new String[] { "createTransfer", "addTransfer" };
	}

	@Override
	protected void execute(String[] parameters) {
		if (parameters.length != 3) {
			System.out.println("Need 3 parameters. Money, Partner and Date");
			return;
		}

		Money money = Money.parseMoney(parameters[0]);

		if (money == null) {
			return;
		}

		String partnerName = parameters[1];
		int partnerId = -1;
		Entity partner; 

		try {
			partnerId = Integer.parseInt(partnerName);
		} finally {
		}
		
		if (partnerId != -1) {
			if (!EntityManager.getInstance().hasEntity(partnerId)) {
				System.out.println("Unknown entity id '" + partnerId + "'");
				return;
			}
			
			partner = EntityManager.getInstance().getEntity(partnerId);
		} else {
			if (!EntityManager.getInstance().hasEntity(partnerName)) {
				System.out.println("Unknown entity name '" + partnerName + "'");
				return;
			}
			
			partner = EntityManager.getInstance().getEntity(partnerName);
		}
		
		// TODO Date
	}
}