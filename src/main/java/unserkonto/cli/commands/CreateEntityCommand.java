package unserkonto.cli.commands;

import unserkonto.model.Account;
import unserkonto.model.Entity;
import unserkonto.model.EntityManager;

public class CreateEntityCommand extends ParameterCommand {
	public CreateEntityCommand(Account account) {
		super(account);
	}

	@Override
	public String[] getNames() {
		return new String[] {"createEntity", "addEntity"};
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
		
		if (EntityManager.getInstance().hasName(name)) {
			System.out.println("An entity with that name already exists");
			return;
		}
		
		Entity entity = EntityManager.getInstance().createEntity(name);
		System.out.println("Created entity '" + entity.getName() + "' with id " + entity.getId());
	}
}