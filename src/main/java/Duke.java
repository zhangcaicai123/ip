import java.util.Scanner;

import Duke.task.Task;
import Duke.task.Deadline;
import Duke.task.Todo;
import Duke.task.Event;
import Duke.exception.*;
import Duke.command.Command;

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
		Command command = new Command();
		String input;
		Scanner in = new Scanner(System.in);
		input = in.nextLine();
		command.setCommand(input);
		while (!command.getCommand().equals("bye")) {
			String option = command.commandToOption(command.getCommand());
			switch (option) {
			case "list":
				printList();
				break;
			case "done":
				try {
					taskToMark = tasks[command.commandToIndex(command.getCommand(), tasksTotal) - 1];
					taskToMark.markedAsDone();
					printMarkMessage(taskToMark);
				} catch (OutOfIndexBound e) {
					printOutOfIndexBoundMessage();
				}
				break;
			case "todo":
				try {
					taskToAdd = new Todo(command.commandToTask(command.getCommand()));
					addTask(taskToAdd);
				} catch (EmptyDescriptionException e) {
					printEmptyDescriptionExceptionMessage(option);
				}
				break;
			case "deadline":
				try {
					String by = command.commandToTime(command.getCommand());
					taskToAdd = new Deadline(command.commandToTask(command.getCommand()));
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
					String at = command.commandToTime(command.getCommand());
					taskToAdd = new Event(command.commandToTask(command.getCommand()));
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
			input = in.nextLine();
			command.setCommand(input);
		}
	}

	public static void addTask(Task taskToAdd) {
		tasks[tasksTotal] = taskToAdd;
		tasksTotal++;
		printAddMessage(taskToAdd);
		printNumOfTasksInList();
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
