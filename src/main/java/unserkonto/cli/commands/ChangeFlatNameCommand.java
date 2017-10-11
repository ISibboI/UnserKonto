package unserkonto.cli.commands;

import unserkonto.cli.CLI;

public class ChangeFlatNameCommand extends ParameterCommand {
	public ChangeFlatNameCommand(CLI cli, String... names) {
		super(cli, names);
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
		getFlat().getAccount().setName(name);
		System.out.println("Set account name to '" + name + "'");
	}
}