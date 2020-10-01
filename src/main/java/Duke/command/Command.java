package duke.command;

import duke.exception.*;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.taskList.TaskList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

public class Command {
	private String command;

	public Command() {
	}

	/**
	 * Set user's input as command in Command class
	 *
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return user's command option e.g. todo,list,bye
	 */
	private String getOption() {
		if (!command.contains(" ")) return command;
		else return command.substring(0, command.indexOf(" "));
	}

	/**
	 * @return the description of user's input task
	 * @throws EmptyDescriptionException If description is null
	 */
	private String getTask() throws EmptyDescriptionException {
		if (!command.contains(" ")) {
			throw new EmptyDescriptionException();
		} else if (command.contains("/")) {
			return command.substring(command.indexOf(" ") + 1, command.indexOf("/") - 1);
		} else {
			return command.substring(command.indexOf(" ") + 1);
		}
	}


	/**
	 * @return the keyword that user wants to search in the task list
	 * @throws EmptyFindException If no keyword is found
	 */
	private String getFind() throws EmptyFindException {
		if (!command.contains(" ")) {
			throw new EmptyFindException();
		} else {
			return command.substring(command.indexOf(" ") + 1);
		}
	}


	/**
	 * Returns the time for event or deadline.
	 * Accept dates in yyyy-mm-dd format (e.g., 2019-10-15)
	 * and print in a different format such as MMM dd yyyy e.g., (Oct 15 2019)
	 *
	 * @return time for event or deadline task
	 * @throws EmptyTimeException If no String for time information is found
	 */
	private String getTime() throws EmptyTimeException {
		String time;
		if (!command.contains("/")) {
			throw new EmptyTimeException();
		} else if (command.contains("deadline")) {
			time = command.substring(command.indexOf("/") + "by ".length() + 1);
		} else if (command.contains("event")) {
			time = command.substring(command.indexOf("/") + "at ".length() + 1);
		} else {
			return null;
		}

		String pattern = "\\d\\d\\d\\d-\\d\\d-\\d\\d";//yyyy-mm-dd format
		boolean isDate = Pattern.matches(pattern, time);
		if (isDate) {
			LocalDate Date = LocalDate.parse(time);
			return Date.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH));
		} else return time;
	}

	/**
	 * @param taskList the list of all tasks input
	 * @return index the index of task that user wants to delete or mark as done
	 * @throws OutOfIndexBound If the index > size of list
	 */
	private int getIndex(TaskList taskList) throws OutOfIndexBound {
		int index = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
		if (taskList.size() < index || index < 1) {
			throw new OutOfIndexBound();
		}
		return index;
	}


	/**
	 * @return true if user type "bye"
	 * false if not
	 */

	public boolean isExit() {
		return command.equals("bye");
	}


	/**
	 * Execute the command e.g. add task, print list
	 *
	 * @param taskList the list of all tasks input
	 * @param storage  the file stores all tasks in the list
	 */
	public void execute(TaskList taskList, Storage storage) {
		Task taskToAdd;
		Task taskToMark;
		String option = getOption();
		switch (option) {
		case "list":
			taskList.printList();
			break;
		case "done":
			try {
				int index = getIndex(taskList) - 1;
				taskToMark = taskList.get(index);
				taskToMark.markedAsDone();
				storage.updateDoneToFile(index, taskList);
			} catch (OutOfIndexBound e) {
				exception.printOutOfIndexBoundMessage();
			} catch (IOException e) {
				System.out.println("Something went wrong: " + e.getMessage());
			}
			break;
		case "todo":
			try {
				taskToAdd = new Todo(getTask());
				taskList.addTask(taskToAdd);
				storage.appendToFile(taskToAdd.text() + System.lineSeparator());
			} catch (EmptyDescriptionException e) {
				exception.printEmptyDescriptionExceptionMessage(option);
			} catch (IOException e) {
				System.out.println("Something went wrong: " + e.getMessage());
			}
			break;
		case "deadline":
			try {
				String by = getTime();
				taskToAdd = new Deadline(getTask());

				((Deadline) taskToAdd).setBy(by);
				taskList.addTask(taskToAdd);
				storage.appendToFile(taskToAdd.text() + System.lineSeparator());
			} catch (EmptyDescriptionException e) {
				exception.printEmptyDescriptionExceptionMessage(option);
			} catch (EmptyTimeException e) {
				exception.printEmptyTimeExceptionMessage(option);
			} catch (IOException e) {
				System.out.println("Something went wrong: " + e.getMessage());
			}
			break;
		case "event":
			try {
				String at = getTime();
				taskToAdd = new Event(getTask());
				((Event) taskToAdd).setAt(at);
				taskList.addTask(taskToAdd);
				storage.appendToFile(taskToAdd.text() + System.lineSeparator());
			} catch (EmptyDescriptionException e) {
				exception.printEmptyDescriptionExceptionMessage(option);
			} catch (EmptyTimeException e) {
				exception.printEmptyTimeExceptionMessage(option);
			} catch (IOException e) {
				System.out.println("Something went wrong: " + e.getMessage());
			}
			break;
		case "delete":
			try {
				int index = getIndex(taskList) - 1;
				taskList.deleteTask(index);
				storage.deleteTaskFromFile(index);
				taskList.printNumOfTasksInList();
			} catch (OutOfIndexBound e) {
				exception.printOutOfIndexBoundMessage();
			} catch (IOException e) {
				System.out.println("Something went wrong: " + e.getMessage());
			}
			break;
		case "find":
			try {
				String keyword = getFind();
				taskList.printSearchResult(keyword);
			} catch (EmptyFindException e) {
				System.out.println("You haven't typed the keyword");
			}
		case "bye":
			break;
		default:
			exception.printIllegalCommandExceptionMessage();
		}

	}

}
