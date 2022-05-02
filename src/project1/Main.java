package project1;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	welcomeMessage();
    	chooseUse();
        //  ---------------------MENU ----------------------------
        // display menu for user
        //      1. view schedule
        //      2. create task
        //      3. delete task
        //      4. edit task
        //      5. read schedule from file
        //      6. exit program
    }

    private static void welcomeMessage(){
    	System.out.println("Welcome to PSS!\n");
    }
    
	private static void chooseUse() {
    	System.out.println("Please choose from the following:\n"
    			+ "1. view schedule\n"
    			+ "2. create task\n"
    			+ "3. delete task\n"
    			+ "4. edit task\n"
    			+ "5. read schedule from file\n"
    			+ "6. exit program\n");
    	try (Scanner keyboard = new Scanner(System.in)) {
			int choice = keyboard.nextInt();
			switch (choice) {
			case 1:
			case 2:	createTask();
					chooseUse();
					break;
			case 3:
			case 4:
			case 5:
			case 6:
			default: System.out.println("Invalid input, please try again\n");
					chooseUse();
			}
		}
    }

	private static void createTask() {
		//test example
		String name = "test";
		String type = "Transient";
		float startTime = 100;
		float duration = 100;
		int date = 100;
		TransientTask exampleTask = new TransientTask(name, type, startTime, duration, date);
		exampleTask.view();
	}
}
