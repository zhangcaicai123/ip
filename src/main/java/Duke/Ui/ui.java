package duke.ui;

import java.util.Scanner;

public class Ui {
	public Ui(){}
	public void showLine() {
		System.out.println("    ____________________________________________________________");
	}

	public void showWelcomeMessage(){
		showLine();
		System.out.println("     Hello！ I'm Duke");
		System.out.println("     What can I do for you?");
		showLine();
	}

	public void showExitMessage(){
		showLine();
		System.out.println("     Bye. Hope to see you again soon!");
		showLine();
	}

	public String readCommand() {
		String input;
		Scanner in = new Scanner(System.in);
		input = in.nextLine();
		return input;
	}

	public void showLoadingError(){
		System.out.println("	 There are some errors when loading file.");
	}


}
