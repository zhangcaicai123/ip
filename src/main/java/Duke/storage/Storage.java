package duke.storage;

import duke.exception.DukeException;
import duke.task.*;
import duke.taskList.TaskList;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
	private final String projectRoot = System.getProperty("user.dir");
	private final String directory = projectRoot + "/data";
	private final String filePath = directory + "/duke.txt";

	public Storage(){}

	public boolean createDirectory(String directoryName) {
		File dir = new File(directoryName);
		if (!dir.exists()) {
			return dir.mkdirs();
		} else {
			return true;
		}
	}

	public void createFile(String pathName, String directoryName) {
		boolean mkdirs = createDirectory(directoryName);
		if (mkdirs) {
			 File f = new File(pathName);
		}
	}

	public ArrayList<Task> load() throws DukeException {
		File loadFile = new File(this.filePath);
		ArrayList<Task> loadList = new ArrayList<>();
		if(!loadFile.exists()){
			createFile(this.filePath,directory);
		} else{
			Scanner file = null;
			try {
				file = new Scanner(loadFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (file.hasNext()){
				String text = file.nextLine();
				Task taskToLoad;
				String time;
				String[] Text = text.trim().split(" \\| " );
				String description = Text[2];
				String status = Text[1];
				if(text.startsWith("T")){
					taskToLoad = new Todo(description);
				} else if (text.startsWith("D")){
					taskToLoad = new Deadline(description);
					time = Text[3];
					((Deadline) taskToLoad).setBy(time);
				} else if (text.startsWith("E")){
					taskToLoad = new Event(description);
					time = Text[3];
					((Event) taskToLoad).setAt(time);
				}else{
					throw new DukeException();
				}
				if(status.equals("1")) {
					taskToLoad.markedAsDone();
				}
				loadList.add(taskToLoad);
			}
			file.close();
			System.out.println("List has been loaded successfully.");

		}
		return loadList;
	}

	public void updateDoneToFile(int index, TaskList taskList) throws IOException {
		File newFile = new File(directory + "/data-new.txt");
		File f = new File(this.filePath);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		PrintWriter writer = new PrintWriter(newFile);
		String line;
		int lineNum = 0;
		while ((line = reader.readLine()) != null) {
			if (lineNum == index) {
				writer.println(taskList.get(index).text());
				writer.flush();
				lineNum++;
				continue;
			}
			lineNum++;
			writer.println(line);
			writer.flush();
		}
		reader.close();
		writer.close();
		f.delete();
		newFile.renameTo(f);
	}

	public void deleteTaskFromFile(int index) throws IOException {
		File newFile = new File(directory + "/data-new.txt");
		File f = new File(this.filePath);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		PrintWriter writer = new PrintWriter(newFile);
		String line;
		int lineNum = 0;
		while ((line = reader.readLine()) != null) {
			if (lineNum == index) {
				lineNum++;
				continue;
			}
			lineNum++;
			writer.println(line);
			writer.flush();
		}
		reader.close();
		writer.close();

		f.delete();
		newFile.renameTo(f);
	}

	public void appendToFile(String textToAppend) throws IOException {
		FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
		fw.write(textToAppend);
		fw.close();
	}

}
