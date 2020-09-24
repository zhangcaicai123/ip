package duke.task;

public class Task {
	protected String description;
	protected boolean isDone;
	final String TICK_SYMBOL = "\u2713";
	final String CROSS_SYMBOL = "\u2718";

	public Task(String description) {
		this.description = description;
		this.isDone = false;
	}

	public String getDescription() {
		return this.description;
	}

	public String getStatusIcon() {
		return (isDone ? TICK_SYMBOL : CROSS_SYMBOL); //return tick or X symbols
	}

	public void markedAsDone() {
		this.isDone = true;
		printMarkMessage(this);
	}

	@Override
	public String toString() {
		return "[" + this.getStatusIcon() + "] " + description;
	}

	public String text() {
		if (this.isDone)
			return "| 1 | " + description;
		else return "| 0 | " + description;
	}

	private void showLine() {
		System.out.println("    ____________________________________________________________");
	}

	private void printMarkMessage(Task task) {
		showLine();
		System.out.println("     Nice! I've marked this task as done:");
		System.out.println("       " + task);
		showLine();
	}
}
