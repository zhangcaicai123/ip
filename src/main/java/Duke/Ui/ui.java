package duke.ui;

import duke.task.Task;

import java.util.Scanner;

public class Ui {
	public Ui() {
	}

	//print horizontal line
	public static void showLine() {
		System.out.println("    ____________________________________________________________");
	}

	public void showWelcomeMessage() {
		showLine();
		System.out.println("     Hello！ I'm Duke");
		System.out.println("     What can I do for you?");
		showLine();
	}

	public void showExitMessage() {
		showLine();
		System.out.println("     Bye. Hope to see you again soon!");
		showLine();
	}

	/**
	 * Read user's command
	 *
	 * @return command
	 */
	public String readCommand() {
		String input;
		Scanner in = new Scanner(System.in);
		input = in.nextLine();
		return input;
	}

	public void showLoadingError() {
		System.out.println("	 There are some errors when loading file.");
	}

	public static void printMarkMessage(Task task) {
		showLine();
		System.out.println("     Nice! I've marked this task as done:");
		System.out.println("       " + task);
		showLine();
	}

}
