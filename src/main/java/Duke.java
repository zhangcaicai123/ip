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
        public void printMarkMessage(String description) {
            printLine();
            System.out.println("     Nice! I've marked this task as done:");
            System.out.println("       ["+tickSymbol+"] "+description);//print tick and the task marked
            printLine();
        }
        @Override
        public String toString (){
            return "["+this.getStatusIcon()+ "] "+description;
        }
    }
    public static class Todo extends Task{

        public Todo(String description) {
            super(description);
        }
        @Override
        public String toString(){
            return "[T]" + super.toString();
        }

    }
    public static class Deadline extends Task{
        protected String by;
        public Deadline(String description) {
            super(description);
            this.by = by;
        }
        public String getBy(){
            return by;
        }
        public void setBy(String by ){
            this.by = by;
        }
        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + this.by + ")";
        }
    }
    public static class Event extends Task{
        protected String at;
        public Event(String description) {
            super(description);
            this.at=at;
        }
        public void setAt(String at ){
            this.at = at;
        }
        @Override
        public String toString(){
            return "[E]"+super.toString()+" (at: "+this.at+")";
        }

    }
    public static void main(String[] args) {
        printWelcomeMessage();
        String command;
        Task [] tasks = new Task[100];
        int tasksTotal = 0;
        Task taskToAdd = null;
        Task taskToMark = null;
        Scanner in = new Scanner(System.in);
        command = in.nextLine();
        while(!command.equals("bye")){
            if(command.equals("list")) {
                printLine();
                System.out.println("     Here are the tasks in your list:");
                for(int i=0;i<tasksTotal;i++){
                   System.out.println("      "+(i+1)+"."+tasks[i]);
                }
                printLine();
            }
            else if(command.contains("done")){
                int index = Integer.parseInt(command.substring(5));
                taskToMark = tasks[index-1];
                taskToMark.markedAsDone();
            }
            else if(command.contains("todo")||command.contains("deadline")||command.contains("event")){
                if (command.contains("todo")) {
                    String taskTodo = command.substring(5);
                    taskToAdd = new Todo(taskTodo);
                }
                if(command.contains("deadline")){
                    int position = command.indexOf("/");
                    String taskDeadline = command.substring(9,position-1);
                    String by = command.substring(position+4);
                    taskToAdd = new Deadline(taskDeadline);
                    ((Deadline) taskToAdd).setBy(by);
                }
                if(command.contains("event")){
                    int position = command.indexOf("/");
                    String taskEvent = command.substring(6,position-1);
                    String at = command.substring(position+4);
                    taskToAdd = new Event(taskEvent);
                    ((Event) taskToAdd).setAt(at);
                }
                tasks[tasksTotal]=taskToAdd;
                tasksTotal++;
                printAddMessage(taskToAdd);
                printNumOfTasksInList(tasksTotal);
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
    public static void printAddMessage(Task task){
        printLine();
        System.out.println("     Got it. I've added this task:");
        System.out.println("      "+task);
    }
    public static void printNumOfTasksInList(int tasksTotal){
        if(tasksTotal==1) System.out.println("     Now you have 1 task in the list.");
        else System.out.println("     Now you have "+tasksTotal+" tasks in the list.");
        printLine();
    }

}
