import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello！ I'm Duke");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________");
        String line;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();
        while(!line.equals("bye")){
            echo(line);
            line = in.nextLine();
        }
        System.out.println("    ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");

    }
    public static void echo(String line){
        System.out.println("    ____________________________________________________________");
        System.out.println("     "+line);
        System.out.println("    ____________________________________________________________");
    }
}
