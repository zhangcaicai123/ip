import duke.exception.*;
import duke.command.Command;
import duke.ui.Ui;
import duke.taskList.TaskList;
import duke.storage.Storage;


public class Duke {

	private final Storage storage;
	private TaskList tasks;
	private final Ui ui = new Ui();
	private final Command c = new Command();

	public Duke() {
		storage = new Storage();
		try {
			//load tasks in data file to current task list
			tasks = new TaskList(storage.load());
		} catch (DukeException e) {
			ui.showLoadingError();
			tasks = new TaskList();
		}
	}

	public void run() {
		ui.showWelcomeMessage();
		boolean isExit = false;
		while (!isExit) {
			String fullCommand = ui.readCommand();
			c.setCommand(fullCommand);
			c.execute(tasks, storage);
			isExit = c.isExit();
		}
		ui.showExitMessage();
	}

	public static void main(String[] args) {
		new Duke().run();
	}


}
