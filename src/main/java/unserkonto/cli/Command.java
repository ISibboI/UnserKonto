package unserkonto.cli;

public interface Command {
	public String[] getNames();
	
	public void execute(String parameters);
}
