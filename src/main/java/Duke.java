import java.util.Scanner;

public class Duke {
	private static int tasksTotal;
	private static final Task[] tasks = new Task[100];

	public static void main(String[] args) {
		printWelcomeMessage();
		commandChecker();
		printExitMessage();
	}

	public static void printExitMessage() {
		printLine();
		System.out.println("     Bye. Hope to see you again soon!");
		printLine();
	}

	public static void printWelcomeMessage() {
		printLine();
		System.out.println("     Hello！ I'm Duke");
		System.out.println("     What can I do for you?");
		printLine();
	}

	public static void printLine() {
		System.out.println("    ____________________________________________________________");
	}

	public static void printAddMessage(Task task) {
		printLine();
		System.out.println("     Got it. I've added this task:");
		System.out.println("      " + task);
	}

	public static void printNumOfTasksInList() {
		if (tasksTotal == 1) {
			System.out.println("     Now you have 1 task in the list.");
		} else {
			System.out.println("     Now you have " + tasksTotal + " tasks in the list.");
		}
		printLine();
	}

	public static void printList() {
		printLine();
		System.out.println("     Here are the tasks in your list:");
		for (int i = 0; i < tasksTotal; i++) {
			System.out.println("      " + (i + 1) + "." + tasks[i]);
		}
		printLine();
	}

	public static void printMarkMessage(Task task) {
		printLine();
		System.out.println("     Nice! I've marked this task as done:");
		System.out.println("       " + task);
		printLine();
	}

	public static void commandChecker() {
		Task taskToAdd;
		Task taskToMark;
		String command;
		Scanner in = new Scanner(System.in);
		command = in.nextLine();
		while (!command.equals("bye")) {
			String option = commandToOption(command);
			switch (option) {
			case "list":
				printList();
				break;
			case "done":
				taskToMark = tasks[commandToIndex(command) - 1];
				taskToMark.markedAsDone();
				printMarkMessage(taskToMark);
				break;
			case "todo":
				taskToAdd = new Todo(commandToTask(command));
				addTask(taskToAdd);
				break;
			case "deadline":
				String by = commandToTime(command);
				taskToAdd = new Deadline(commandToTask(command));
				((Deadline) taskToAdd).setBy(by);
				addTask(taskToAdd);
				break;
			case "event":
				String at = commandToTime(command);
				taskToAdd = new Event(commandToTask(command));
				((Event) taskToAdd).setAt(at);
				addTask(taskToAdd);
				break;
			}
			command = in.nextLine();
		}
	}

	public static void addTask(Task taskToAdd) {
		tasks[tasksTotal] = taskToAdd;
		tasksTotal++;
		printAddMessage(taskToAdd);
		printNumOfTasksInList();
	}

	public static String commandToOption(String command) {
		if (!command.contains(" ")) return command;
		else return command.substring(0, command.indexOf(" "));
	}

	public static String commandToTask(String command) {
		if (command.contains("/")) return command.substring(command.indexOf(" ") + 1, command.indexOf("/"));
		else return command.substring(command.indexOf(" ") + 1);
	}

	public static String commandToTime(String command) {
		if (command.contains("deadline")) return command.substring(command.indexOf("/") + "by ".length() + 1);
		if (command.contains("event")) return command.substring(command.indexOf("/") + "at ".length() + 1);
		else return null;
	}

	public static int commandToIndex(String command) {
		return Integer.parseInt(command.substring(command.indexOf(" ") + 1));
	}
}
