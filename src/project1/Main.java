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
		do {
			Scanner keyboard = new Scanner(System.in);
	    	System.out.println("Please choose from the following:\n"
	    			+ "1. view schedule\n"
	    			+ "2. create task\n"
	    			+ "3. delete task\n"
	    			+ "4. edit task\n"
	    			+ "5. read schedule from file\n"
	    			+ "6. exit program\n");
			int choice = keyboard.nextInt();
			switch (choice) {
			case 1:
			case 2:	createTask();
					keyboard.close();
					continue;
			case 3:
			case 4:
			case 5:
			case 6:
			default: System.out.println("Invalid input, please try again\n");
					 keyboard.close();
			}
		} while (true);
    }

	private static void createTask() {
		RecurringTask newTask = new RecurringTask();
		newTask.create();
		Schedule.hm.put(newTask.getName(), newTask);
		System.out.println("Success");
		RecurringTask testTask =(RecurringTask)Schedule.hm.get("test");
		testTask.view();
	}
		public static float calcEndTime(float inStartTime, float inDuration) {
		int integer = (int)(inStartTime+inDuration);
		float decimal = (inStartTime + inDuration) - integer; 
		if (((inStartTime+inDuration)-integer)>=.60) {
			integer++;
			decimal-=.60;
		}
		if(integer >23) {
			integer-=24;
		}
		return integer+decimal;
	}
}
