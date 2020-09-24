package duke.command;

import duke.exception.*;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.taskList.TaskList;
import java.io.IOException;

public class Command {
	private String command;

	public Command() {}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getOption() {
		if (!command.contains(" ")) return command;
		else return command.substring(0, command.indexOf(" "));
	}

	public String getTask() throws EmptyDescriptionException {
		if (!command.contains(" ")) {
			throw new EmptyDescriptionException();
		} else if (command.contains("/")) {
			return command.substring(command.indexOf(" ") + 1, command.indexOf("/")-1);
		} else {
			return command.substring(command.indexOf(" ") + 1);
		}
	}

	public String getTime() throws EmptyTimeException {
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

	public int getIndex(TaskList taskList) throws OutOfIndexBound {
		int index = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
		if (taskList.size() < index || index < 1) {
			throw new OutOfIndexBound();
		}
		return index;
	}

	public boolean isExit(){
		return command.equals("bye");
	}

	public void execute(TaskList taskList, Storage storage){
		Task taskToAdd;
		Task taskToMark;
		String option = getOption();
		switch (option) {
		case "list":
			taskList.printList();
			break;
		case "done":
			try {
				int index =  getIndex(taskList) - 1;
				taskToMark = taskList.get(index);
				taskToMark.markedAsDone();
				storage.updateDoneToFile(index,taskList);
			} catch (OutOfIndexBound e ) {
				exception.printOutOfIndexBoundMessage();
			} catch (IOException e){
				System.out.println("Something went wrong: " + e.getMessage());
			}
			break;
		case "todo":
			try {
				taskToAdd = new Todo(getTask());
				taskList.addTask(taskToAdd);
				storage.appendToFile(taskToAdd.text()+System.lineSeparator());
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
				storage.appendToFile(taskToAdd.text()+System.lineSeparator());
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
				storage.appendToFile(taskToAdd.text()+System.lineSeparator());
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
			} catch (OutOfIndexBound e ) {
				exception.printOutOfIndexBoundMessage();
			} catch(IOException e){
				System.out.println("Something went wrong: " + e.getMessage());
			}
			break;
		case "bye": break;
		default:
			exception.printIllegalCommandExceptionMessage();
		}

	}

}
