package unserkonto.cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unserkonto.cli.CLI;
import unserkonto.cli.Command;

public abstract class ParameterCommand extends Command {
	public ParameterCommand(CLI cli, String... names) {
		super(cli, names);
	}

	@Override
	public void execute(String parameters) {
		String[] splitParameters = parameters.split("\"");

		boolean inside = false;
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
