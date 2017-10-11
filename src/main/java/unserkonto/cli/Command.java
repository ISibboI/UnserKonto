package unserkonto.cli;

import unserkonto.model.Account;
import unserkonto.model.Flat;
import unserkonto.model.InhabitantManager;

public abstract class Command {
	private final String[] names;
	private final CLI cli;
	
	public Command(CLI cli, String... names) {
		this.cli = cli;
		this.names = names;
	}
	
	public String[] getNames() {
		return names;
	}
	
	protected CLI getCLI() {
		return cli;
	}
	
	protected Flat getFlat() {
		return cli.getFlat();
	}
	
	protected Account getAccount() {
		return getFlat().getAccount();
	}
	
	protected InhabitantManager getInhabitantManager() {
		return getFlat().getInhabitantManager();
	}
	
	public abstract void execute(String parameters);
}
