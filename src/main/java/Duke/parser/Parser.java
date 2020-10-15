package duke.parser;

import duke.exception.*;
import duke.taskList.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	/**
	 * @return user's command option e.g. todo,list,bye
	 */
	public static String getOption(String command) {
		String pattern = "^(todo|list|deadline|event|find|done|delete|bye).*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(command);
		if (m.find()) {
			return m.group(1);
		} else {
			return "illegal";
		}
	}

	/**
	 * @return the description of user's input todo task
	 * @throws EmptyDescriptionException If description is null
	 */
	public static String getTodo(String command) throws EmptyDescriptionException {
		String todo_pattern = "todo (.*)";
		Pattern r = Pattern.compile(todo_pattern);
		Matcher m = r.matcher(command);
		if (Pattern.matches("todo *", command)) {
			throw new EmptyDescriptionException();
		} else {
			m.find();
			return m.group(1).trim();
		}
	}

	/**
	 * @return the task description of user's input deadline or event
	 * @throws EmptyDescriptionException If description is null
	 */
	public static String getTask(String command) throws EmptyDescriptionException {
		String pattern = "(event|deadline)( .* )(/at|/by)( .*)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(command);
		if (Pattern.matches("event|deadline *", command)) {
			throw new EmptyDescriptionException();
		}
		m.find();
		return m.group(2).trim();
	}

	/**
	 * Returns the time for event or deadline.
	 * Accept dates in yyyy-mm-dd format (e.g., 2019-10-15)
	 * and print in a different format such as MMM dd yyyy e.g., (Oct 15 2019)
	 *
	 * @return time for event or deadline task
	 * @throws EmptyTimeException If no String for time information is found
	 */
	public static String getTime(String command) throws EmptyTimeException {
		String pattern = "(event|deadline)( .* )(/at|/by)(.*)";
		String time;
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(command);
		if (m.find()) {
			time = m.group(4).trim();
		} else throw new EmptyTimeException();
		String time_pattern = "\\d\\d\\d\\d-\\d\\d-\\d\\d";//yyyy-mm-dd format
		boolean isDate = Pattern.matches(time_pattern, time);
		if (isDate) {
			LocalDate Date = LocalDate.parse(time);
			return Date.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH));
		} else return time;
	}

	/**
	 * @return the keyword that user wants to search in the task list
	 * @throws EmptyFindException If no keyword is found
	 */
	public static String getFind(String command) throws EmptyFindException {
		String pattern = "find *";
		if (Pattern.matches(pattern, command)) {
			throw new EmptyFindException();
		} else {
			return command.substring(command.indexOf(" ") + 1);
		}
	}

	/**
	 * @param taskList the list of all tasks input
	 * @return index the index of task that user wants to delete or mark as done
	 * @throws OutOfIndexBound     If the index > size of list
	 * @throws EmptyIndexException If user does not input any integer
	 */
	public static int getIndex(TaskList taskList, String command) throws OutOfIndexBound, EmptyIndexException {
		String pattern = "(done|delete)( )(\\d+)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(command);
		int index;
		if (Pattern.matches("done|delete *", command)) {
			throw new EmptyIndexException();
		} else {
			m.find();
			index = Integer.parseInt(m.group(3));
		}
		if (taskList.size() < index || index < 1) {
			throw new OutOfIndexBound();
		}
		return index;
	}


}
