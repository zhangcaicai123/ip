import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import duke.task.Task;
import duke.task.Deadline;
import duke.task.Todo;
import duke.task.Event;
import duke.exception.*;
import duke.command.Command;

public class Duke {
	private static int tasksTotal;
	private static final Task[] tasks = new Task[100];
	private static File f = null;
	private static final String projectRoot =  System.getProperty("user.dir");
	private static final ArrayList<Task> taskList = new ArrayList<>();
	private static final String projectRoot = System.getProperty("user.dir");

	public static void main(String[] args) {
		String directory = projectRoot + "/data";
		String path = directory + "/duke.txt";
		createFile(path, directory);
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
		if (taskList.size() == 1) {
			System.out.println("     Now you have 1 task in the list.");
		} else {
			System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
		}
		printLine();
	}

	public static void printList() {
		printLine();
		if (taskList.size() > 0) {
			System.out.println("     Here are the tasks in your list:");
			for (Task task:taskList) {
				System.out.println("      " + (taskList.indexOf(task) + 1) + "." + task);
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
					taskToMark = taskList.get(command.commandToIndex(command.getCommand(), taskList.size()) - 1);
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
				} catch (IOException e) {
					System.out.println("Something went wrong: " + e.getMessage());
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
				} catch (IOException e) {
					System.out.println("Something went wrong: " + e.getMessage());
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
				} catch (IOException e) {
					System.out.println("Something went wrong: " + e.getMessage());
				}
				break;
			case "delete":
				try {
					int index = command.commandToIndex(command.getCommand(), taskList.size()) - 1;
					printDeleteMessage(index);
					taskList.remove(index);
					printNumOfTasksInList();
				} catch (OutOfIndexBound e) {
					printOutOfIndexBoundMessage();
				}
				break;
			default:
				throw new IllegalCommandException();
			}
			input = in.nextLine();
			command.setCommand(input);
		}
	}

	public static void addTask(Task taskToAdd) throws IOException {
		tasks[tasksTotal] = taskToAdd;
		tasksTotal++;
	public static void addTask(Task taskToAdd) {
		taskList.add(taskToAdd);
		printAddMessage(taskToAdd);
		printNumOfTasksInList();
		appendToFile(f.getPath(), String.format("%s%n", taskToAdd.text()));
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

	private static void appendToFile(String filePath, String textToAppend) throws IOException {
		FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
		fw.write(textToAppend);
		fw.close();
	}

	public static void createFile(String pathName, String directoryName) {
		boolean mkdirs = createDirectory(directoryName);
		if (mkdirs) {
			f = new File(pathName);
		}
	}

	public static boolean createDirectory(String directoryName) {
		File dir = new File(directoryName);
		if (!dir.exists()) {
			return dir.mkdirs();
		} else {
			return true;
		}

	public static void printDeleteMessage(int index) {
		printLine();
		System.out.println("	 Noted. I've removed this task:");
		System.out.printf("\t   %s%n", taskList.get(index));
	}
}
