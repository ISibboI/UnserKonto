package unserkonto.cli.commands;

import unserkonto.model.Account;

public class ChangeAccountNameCommand extends ParameterCommand {
	public ChangeAccountNameCommand(Account account) {
		super(account);
	}
	
	@Override
	public String[] getNames() {
		return new String[] {"changeAccountName"};
	}

	@Override
	protected void execute(String[] parameters) {
		if (parameters.length == 0) {
			System.out.println("Specify the new account name");
			return;
		}
		
		if (parameters.length > 1) {
			System.out.println("Only one parameter allowed");
			return;
		}
		
		String name = parameters[0];
		getAccount().setName(name);
		System.out.println("Set account name to '" + name + "'");
	}
}