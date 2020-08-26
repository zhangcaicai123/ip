import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello！ I'm Duke");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________");
        String line;
        int itemCount = 0;
        String [] list = new String[100];
        Scanner in = new Scanner(System.in);
        line = in.nextLine();
        while(!line.equals("bye")){
            if(line.equals("list")) printList(list,itemCount);
            else{
            echo(line);
            list[itemCount] = line;
            itemCount++;
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
    public static void printList(String[] list,int itemCount){
        System.out.println("    ____________________________________________________________");
        for (int i=0;i<itemCount;i++){
            System.out.print("     ");
            System.out.print(i+1);
            System.out.println(". "+list[i]);
        }
        System.out.println("    ____________________________________________________________");
    }
}
