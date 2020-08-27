import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    public static class Task {
        protected String description;
        protected boolean isDone;
        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }
        public String getStatusIcon() {
            return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
        }
        public void markedAsDone(){
            this.isDone = true;
        }
        //...
    }
    public static class TaskList{
        static List<Task> Tasks;
        static int taskNum = 0;
        public TaskList(){
            Tasks = new ArrayList<>();
        }
        public void addTask(Task task){
            Tasks.add(task);
            taskNum++;
        }
        public static Task findTask(int index){
            return Tasks.get(index);
        }
        public void printList(){
            System.out.println("    ____________________________________________________________");
            for (int i=0;i<taskNum;i++){
                System.out.print("     ");
                System.out.print(i+1);
                System.out.println(".["+ findTask(i).getStatusIcon()+"] "+findTask(i).description);
            }
            System.out.println("    ____________________________________________________________");
        }

    }
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello！ I'm Duke");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________");
        String line;
        TaskList taskList = new TaskList ();
        Task t ;
        Task m;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();

        while(!line.equals("bye")){
            if(line.equals("list")) taskList.printList();
            else if(line.contains("done")){
                int index = Integer.parseInt(line.substring(line.indexOf("e")+2));
                m = TaskList.findTask(index-1);
                m.markedAsDone();
                System.out.println("    ____________________________________________________________");
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       [\u2713] "+m.description);
                System.out.println("    ____________________________________________________________");
            }
            else{
            echo(line);
            t = new Task(line);
            taskList.addTask(t);
            }
            line = in.nextLine();
        }
        bye();


    }
    public static void echo(String line){
        System.out.println("    ____________________________________________________________");
        System.out.println("     added: "+line);
        System.out.println("    ____________________________________________________________");
    }
    public static void bye(){
        System.out.println("    ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }



}
