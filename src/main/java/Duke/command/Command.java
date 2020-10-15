package duke.command;

import duke.exception.*;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.taskList.TaskList;
import duke.ui.Ui;

import java.io.IOException;

public class Command {
	private String command;

	public Command() {
	}

	/**
	 * Set user's input as command in Command class
	 *
	 * @param command user's command
	 */
	public void setCommand(String command) {
		this.command = command;
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
		String option = Parser.getOption(command);
		switch (option) {
		case "list":
			taskList.printList();
			break;
		case "done":
			done(taskList, storage);
			break;
		case "todo":
			addToDo(taskList, storage);
			break;
		case "deadline":
			addDeadline(taskList, storage);
			break;
		case "event":
			addEvent(taskList, storage);
			break;
		case "delete":
			delete(taskList, storage);
			break;
		case "find":
			find(taskList);
			break;
		case "bye":
			break;
		default:
			exception.printIllegalCommandExceptionMessage();
			break;
		}


	}

	/**
	 * Execute done command
	 *
	 * @param taskList the list of all tasks input
	 * @param storage  the file stores all tasks in the list
	 */
	private void done(TaskList taskList, Storage storage) {
		try {
			int index = Parser.getIndex(taskList, command) - 1;
			Task taskToMark = taskList.get(index);
			taskToMark.markedAsDone();
			Ui.printMarkMessage(taskToMark);
			storage.updateDoneToFile(index, taskList);
		} catch (OutOfIndexBound e) {
			exception.printOutOfIndexBoundMessage();
		} catch (IOException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		} catch (EmptyIndexException e) {
			exception.printEmptyIndexExceptionMessage();
		}
	}

	/**
	 * Add todo task to the task list
	 *
	 * @param taskList the list of all tasks input
	 * @param storage  the file stores all tasks in the list
	 */
	private void addToDo(TaskList taskList, Storage storage) {
		try {
			Todo taskToAdd = new Todo(Parser.getTodo(command));
			taskList.addTask(taskToAdd);
			storage.appendToFile(taskToAdd.text() + System.lineSeparator());
		} catch (EmptyDescriptionException e) {
			exception.printEmptyDescriptionExceptionMessage("todo");
		} catch (IOException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
	}

	/**
	 * Add deadline task to the task list
	 *
	 * @param taskList the list of all tasks input
	 * @param storage  the file stores all tasks in the list
	 */
	private void addDeadline(TaskList taskList, Storage storage) {
		try {
			String by = Parser.getTime(command);
			Deadline taskToAdd = new Deadline(Parser.getTask(command));
			taskToAdd.setBy(by);
			taskList.addTask(taskToAdd);
			storage.appendToFile(taskToAdd.text() + System.lineSeparator());
		} catch (EmptyDescriptionException e) {
			exception.printEmptyDescriptionExceptionMessage("deadline");
		} catch (EmptyTimeException e) {
			exception.printEmptyTimeExceptionMessage("deadline");
		} catch (IOException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
	}

	/**
	 * Add event to the task list
	 *
	 * @param taskList the list of all tasks input
	 * @param storage  the file stores all tasks in the list
	 */
	private void addEvent(TaskList taskList, Storage storage) {
		try {
			String at = Parser.getTime(command);
			Event taskToAdd = new Event(Parser.getTask(command));
			taskToAdd.setAt(at);
			taskList.addTask(taskToAdd);
			storage.appendToFile(taskToAdd.text() + System.lineSeparator());
		} catch (EmptyDescriptionException e) {
			exception.printEmptyDescriptionExceptionMessage("event");
		} catch (EmptyTimeException e) {
			exception.printEmptyTimeExceptionMessage("event");
		} catch (IOException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
	}

	/**
	 * Delete task from task list
	 *
	 * @param taskList the list of all tasks input
	 * @param storage  the file stores all tasks in the list
	 */
	private void delete(TaskList taskList, Storage storage) {
		try {
			int index = Parser.getIndex(taskList, command) - 1;
			taskList.deleteTask(index);
			storage.deleteTaskFromFile(index);
			taskList.printNumOfTasksInList();
		} catch (OutOfIndexBound e) {
			exception.printOutOfIndexBoundMessage();
		} catch (IOException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		} catch (EmptyIndexException e) {
			exception.printEmptyIndexExceptionMessage();
		}
	}

	/**
	 * Find task in the task list with keyword
	 *
	 * @param taskList the list of all tasks input
	 */
	private void find(TaskList taskList) {
		try {
			String keyword = Parser.getFind(command);
			taskList.printSearchResult(keyword);
		} catch (EmptyFindException e) {
			exception.printEmptyKeywordMessage();
		}
	}
}
