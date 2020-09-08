package duke.command;

import duke.exception.*;

public class Command {
	private String command;

	public Command() {
	}

	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String commandToOption(String command) {
		if (!command.contains(" ")) return command;
		else return command.substring(0, command.indexOf(" "));
	}

	public String commandToTask(String command) throws EmptyDescriptionException {
		if (!command.contains(" ")) {
			throw new EmptyDescriptionException();
		} else if (command.contains("/")) {
			return command.substring(command.indexOf(" ") + 1, command.indexOf("/"));
		} else {
			return command.substring(command.indexOf(" ") + 1);
		}
	}

	public String commandToTime(String command) throws EmptyTimeException {
		if (!command.contains("/")) {
			throw new EmptyTimeException();
		}
		if (command.contains("deadline")) {
			return command.substring(command.indexOf("/") + "by ".length() + 1);
		}
		if (command.contains("event")) {
			return command.substring(command.indexOf("/") + "at ".length() + 1);
		} else {
			return null;
		}
	}

	public int commandToIndex(String command, int tasksTotal) throws OutOfIndexBound {
		int index = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
		if (tasksTotal < index || index < 1) {
			throw new OutOfIndexBound();
		}
		return index;
	}
}
