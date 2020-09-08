package Duke.task;

public class Deadline extends Task {
	protected String by;

	public Deadline(String description) {
		super(description);
	}

	public void setBy(String by) {
		this.by = by;
	}

	@Override
	public String toString() {
		return "[D]" + super.toString() + " (by: " + this.by + ")";
	}
}
