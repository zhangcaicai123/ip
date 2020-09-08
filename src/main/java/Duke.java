import java.util.Scanner;

public class Duke {
	private static int tasksTotal;
	private static final Task[] tasks = new Task[100];

	public static void main(String[] args) {
		printWelcomeMessage();
		try {
			commandChecker();
		} catch (IllegalCommandException e) {
			printIllegalCommandExceptionMessage();
		}
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
		if (tasksTotal > 0) {
			System.out.println("     Here are the tasks in your list:");
			for (int i = 0; i < tasksTotal; i++) {
				System.out.println("      " + (i + 1) + "." + tasks[i]);
			}
		} else {
			System.out.println("	 You don't have any task in your list.");
		}
		printLine();
	}

	public static void printMarkMessage(Task task) {
		printLine();
		System.out.println("     Nice! I've marked this task as done:");
		System.out.println("       " + task);
		printLine();
	}

	public static void commandChecker() throws IllegalCommandException {
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
				try {
					taskToMark = tasks[commandToIndex(command) - 1];
					taskToMark.markedAsDone();
					printMarkMessage(taskToMark);
				} catch (OutOfIndexBound e) {
					printOutOfIndexBoundMessage();
				}
				break;
			case "todo":
				try {
					taskToAdd = new Todo(commandToTask(command));
					addTask(taskToAdd);
				} catch (EmptyDescriptionException e) {
					printEmptyDescriptionExceptionMessage(option);
				}
				break;
			case "deadline":
				try {
					String by = commandToTime(command);
					taskToAdd = new Deadline(commandToTask(command));
					((Deadline) taskToAdd).setBy(by);
					addTask(taskToAdd);
				} catch (EmptyDescriptionException e) {
					printEmptyDescriptionExceptionMessage(option);
				} catch (EmptyTimeException e) {
					printEmptyTimeExceptionMessage(option);
				}
				break;
			case "event":
				try {
					String at = commandToTime(command);
					taskToAdd = new Event(commandToTask(command));
					((Event) taskToAdd).setAt(at);
					addTask(taskToAdd);
				} catch (EmptyDescriptionException e) {
					printEmptyDescriptionExceptionMessage(option);
				} catch (EmptyTimeException e) {
					printEmptyTimeExceptionMessage(option);
				}
				break;
			default:
				throw new IllegalCommandException();
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

	public static String commandToTask(String command) throws EmptyDescriptionException {
		if (!command.contains(" ")) {
			throw new EmptyDescriptionException();
		} else if (command.contains("/")) {
			return command.substring(command.indexOf(" ") + 1, command.indexOf("/"));
		} else {
			return command.substring(command.indexOf(" ") + 1);
		}
	}

	public static String commandToTime(String command) throws EmptyTimeException {
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

	public static int commandToIndex(String command) throws OutOfIndexBound {
		int index = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
		if (tasksTotal < index || index < 1) {
			throw new OutOfIndexBound();
		}
		return index;
	}

	public static void printIllegalCommandExceptionMessage() {
		printLine();
		System.out.printf("\t ☹ OOPS!!! I'm sorry, but I don't know what that means :-(%n");
		printLine();
	}

	public static void printEmptyDescriptionExceptionMessage(String option) {
		printLine();
		System.out.printf("\t ☹ OOPS!!! The description of a %s cannot be empty.%n", option);
		printLine();
	}

	public static void printEmptyTimeExceptionMessage(String option) {
		printLine();
		System.out.printf("\t ☹ OOPS!!! You haven't set a time for the %s.%n", option);
		printLine();
	}

	public static void printOutOfIndexBoundMessage() {
		printLine();
		System.out.printf("\t ☹ OOPS!!! You seem to input wrong index of the task.%n");
		printLine();
	}
}
