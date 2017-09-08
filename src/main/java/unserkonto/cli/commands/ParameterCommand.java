package unserkonto.cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unserkonto.cli.Command;
import unserkonto.model.Account;

public abstract class ParameterCommand implements Command {
	private final Account account;

	public ParameterCommand(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	@Override
	public void execute(String parameters) {
		String[] splitParameters = parameters.split("\"");

		boolean inside = parameters.startsWith("\"");
		List<String> parsedParameters = new ArrayList<>();

		for (String splitParameter : splitParameters) {
			if (inside && splitParameter.length() > 0) {
				parsedParameters.add(splitParameter);
			} else if (!inside && splitParameter.length() > 0) {
				parsedParameters.addAll(Arrays.asList(splitParameter.split("\\s+")));
			}

			inside = !inside;
		}

		execute(parsedParameters.toArray(new String[0]));
	}

	protected abstract void execute(String[] parameters);
}
