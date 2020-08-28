import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    public static class Task {
        protected String description;
        protected boolean isDone;
        final static String tickSymbol =  "\u2713";
        final static String crossSymbol = "\u2718";
        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }
        public String getStatusIcon() {
            return (isDone ? tickSymbol : crossSymbol); //return tick or X symbols
        }
        public void markedAsDone(){
            this.isDone = true;
            printMarkMessage(this.description);
        }
        public static void printMarkMessage(String description) {
            printLine();
            System.out.println("     Nice! I've marked this task as done:");
            System.out.println("       ["+tickSymbol+"] "+description);//print tick and the task marked
            printLine();
        }


    }
    public static class TaskList{
        static List<Task> Tasks;
        static int taskTotal = 0;
        public TaskList(){
            Tasks = new ArrayList<>();
        }
        public void addTask(Task task){
            Tasks.add(task);
            taskTotal++;
            printAddTaskMessage(task.description);
        }
        public static Task findTask(int index){
            return Tasks.get(index);
        }
        public void printList(){
            printLine();
            for (int i=0;i<taskTotal;i++){
                System.out.print("     ");
                System.out.print(i+1);
                System.out.println(".["+ findTask(i).getStatusIcon()+"] "+findTask(i).description);
            }
            printLine();
        }
        public static void printAddTaskMessage(String description){
            printLine();
            System.out.println("     added: "+description);
            printLine();
        }

    }
    public static void main(String[] args) {
        printWelcomeMessage();
        String command;
        TaskList taskList = new TaskList ();
        Task taskToAdd ;
        Task taskToMark;
        Scanner in = new Scanner(System.in);
        command = in.nextLine();
        while(!command.equals("bye")){
            if(command.equals("list")) taskList.printList();
            else if(command.contains("done")){
                int index = Integer.parseInt(command.substring(command.indexOf("e")+2));
                taskToMark = TaskList.findTask(index-1);
                taskToMark.markedAsDone();
            }
            else{
                taskToAdd = new Task(command);
                taskList.addTask(taskToAdd);
            }
            command = in.nextLine();
        }
        printExitMessage();


    }
    public static void printExitMessage(){
        printLine();
        System.out.println("     Bye. Hope to see you again soon!");
        printLine();
    }
    public static void printWelcomeMessage(){
        printLine();
        System.out.println("     Hello！ I'm Duke");
        System.out.println("     What can I do for you?");
        printLine();
    }
    public static void printLine(){
        System.out.println("    ____________________________________________________________");
    }


}
