package unserkonto.cli.commands;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import unserkonto.cli.CLI;
import unserkonto.model.Inhabitant;
import unserkonto.model.Money;
import unserkonto.model.MoneyTransfer;

public class CreateTransferCommand extends ParameterCommand {
	public CreateTransferCommand(CLI cli, String... names) {
		super(cli, names);
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
		Inhabitant partner; 

		try {
			partnerId = Integer.parseInt(partnerName);
		} catch (NumberFormatException e) {
			partnerId = -1;
		}
		
		if (partnerId != -1) {
			if (!getFlat().getInhabitantManager().hasEntity(partnerId)) {
				System.out.println("Unknown entity id '" + partnerId + "'");
				return;
			}
			
			partner = getFlat().getInhabitantManager().getEntity(partnerId);
		} else {
			if (!getFlat().getInhabitantManager().hasEntity(partnerName)) {
				System.out.println("Unknown entity name '" + partnerName + "'");
				return;
			}
			
			partner = getFlat().getInhabitantManager().getEntity(partnerName);
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
		
		getFlat().getAccount().addTransfer(transfer);
		System.out.println("Added " + transfer);
	}
}