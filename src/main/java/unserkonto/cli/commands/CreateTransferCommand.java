package unserkonto.cli.commands;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import unserkonto.model.Account;
import unserkonto.model.Entity;
import unserkonto.model.EntityManager;
import unserkonto.model.Money;
import unserkonto.model.MoneyTransfer;

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
		if (parameters.length < 3 || parameters.length > 4) {
			System.out.println("Need 3 or 4 parameters. Money Partner Date [Due Date]");
			return;
		}

		Money money = Money.parseMoney(parameters[0]);

		if (money == null) {
			return;
		}
		
		if (money.isZero()) {
			System.out.println("Cannot create a transfer of zero euros");
			return;
		}

		String partnerName = parameters[1];
		int partnerId;
		Entity partner; 

		try {
			partnerId = Integer.parseInt(partnerName);
		} catch (NumberFormatException e) {
			partnerId = -1;
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
		
		String dateString = parameters[2];
		DateFormat df = DateFormat.getDateInstance();
		Date date;
		
		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			System.out.println("Malformed date string '" + dateString + "'");
			return;
		}
		
		MoneyTransfer transfer = new MoneyTransfer(money, partner, date);
		
		if (parameters.length >= 4) {
			String dueDateString = parameters[3];
			Date dueDate;
			
			try {
				dueDate = df.parse(dueDateString);
			} catch (ParseException e) {
				System.out.println("Malformed date string '" + dueDateString + "'");
				return;
			}
			
			transfer.setDueDate(dueDate);
		}
		
		getAccount().addTransfer(transfer);
		System.out.println("Added " + transfer);
	}
}