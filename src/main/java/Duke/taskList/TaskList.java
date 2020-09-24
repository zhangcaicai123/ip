package duke.taskList;

import duke.task.Task;

import java.util.ArrayList;

public class TaskList {
	private ArrayList<Task> taskList = new ArrayList<>();

	public TaskList() {
	}

	public TaskList(ArrayList<Task> loadedList) {
		this.taskList = loadedList;
	}

	public void addTask(Task taskToAdd) {
		this.taskList.add(taskToAdd);
		printAddMessage(taskToAdd);
		printNumOfTasksInList();
	}

	public void deleteTask(int taskIndex) {
		printDeleteMessage(taskIndex);
		taskList.remove(taskIndex);
	}

	public Task get(int index) {
		return this.taskList.get(index);
	}

	public int size() {
		return this.taskList.size();
	}

	private void showLine() {
		System.out.println("    ____________________________________________________________");
	}

	public void printList() {
		showLine();
		if (this.size() > 0) {
			System.out.println("     Here are the tasks in your list:");
			for (Task task : this.taskList) {
				System.out.println("      " + (this.taskList.indexOf(task) + 1) + "." + task);
			}
		} else {
			System.out.println("	 You don't have any task in your list.");
		}
		showLine();
	}

	public void printAddMessage(Task task) {
		showLine();
		System.out.println("     Got it. I've added this task:");
		System.out.println("      " + task);
	}

	public void printNumOfTasksInList() {
		if (this.taskList.size() == 1) {
			System.out.println("     Now you have 1 task in the list.");
		} else {
			System.out.println("     Now you have " + this.taskList.size() + " tasks in the list.");
		}
		showLine();
	}

	public void printDeleteMessage(int index) {
		showLine();
		System.out.println("	 Noted. I've removed this task:");
		System.out.printf("\t   %s%n", this.taskList.get(index));
	}

	private ArrayList<Task> find(String keyword) {
		ArrayList<Task> findList = new ArrayList<>();
		for (Task task : this.taskList) {
			if (task.getDescription().contains(keyword)) findList.add(task);
		}
		return findList;
	}

	public void printSearchResult(String keyword) {
		ArrayList<Task> results = find(keyword);
		showLine();
		if (this.size() > 0) {
			System.out.println("     Here are the matching tasks in your list:");
			for (Task task : results) {
				System.out.println("      " + (results.indexOf(task) + 1) + "." + task);
			}
		} else {
			System.out.println("	 You don't have any matching task in your list.");
		}
		showLine();
	}

}
