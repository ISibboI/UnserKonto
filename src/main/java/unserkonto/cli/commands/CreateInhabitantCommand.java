package unserkonto.cli.commands;

import unserkonto.cli.CLI;
import unserkonto.model.Inhabitant;

public class CreateInhabitantCommand extends ParameterCommand {
	public CreateInhabitantCommand(CLI cli, String... names) {
		super(cli, names);
	}

	@Override
	protected void execute(String[] parameters) {
		if (parameters.length < 1) {
			System.out.println("Please specify the entity name");
			return;
		}
		
		if (parameters.length > 1) {
			System.out.println("Only one parameter allowed");
			return;
		}
		
		String name = parameters[0];
		
		if (getFlat().getInhabitantManager().hasName(name)) {
			System.out.println("An entity with that name already exists");
			return;
		}
		
		Inhabitant entity = getFlat().getInhabitantManager().createEntity(name);
		System.out.println("Created entity '" + entity.getName() + "' with id " + entity.getId());
	}
}