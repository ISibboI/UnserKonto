package unserkonto.cli.commands;

import unserkonto.cli.Command;
import unserkonto.model.Entity;
import unserkonto.model.EntityManager;

public class ListEntitiesCommand implements Command {

	@Override
	public String[] getNames() {
		return new String[] {"listEntities"};
	}

	@Override
	public void execute(String parameters) {
		for (Entity e: EntityManager.getInstance().getEntities()) {
			System.out.println(e.getId() + ": " + e.getName());
		}
	}
}