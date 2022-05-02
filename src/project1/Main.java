package project1;


import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//welcomeMessage();
		//chooseUse();
		System.out.println(verifyDuration(1, 80));
		System.out.println(verifyDuration(2, 28));
		System.out.println(verifyDuration(-1, 45));
		System.out.println(verifyDuration(2, -52));
		System.out.println(verifyDuration(0, 0));
		System.out.println(verifyDuration(0, 30));
		System.out.println(verifyCategory(1, "Cancellation"));
		System.out.println(verifyCategory(1, "cancellation"));
		System.out.println(verifyCategory(2, "Appointment"));
		System.out.println(verifyCategory(2, "Exercise"));
		System.out.println(verifyCategory(3, "Meal"));
		System.out.println(verifyCategory(3, "Work"));
		System.out.println(verifyCategory(3, "Cancellation"));
		System.out.println(verifyCategory(5, "Cancellation"));
	}

	public static float verifyDuration(float hour, float minute) {
		if (hour >= 0 && minute >= 0) {
			if (minute < 60) {
				minute = Math.round(minute/15);
				minute = (float) ((minute * 0.25));
			} else {
				hour = (float) (hour + Math.floor(minute / 60));
				minute = (minute % 60);
				minute = Math.round(minute / 15);
				minute = (float) ((minute * 0.25));
			}
			return hour + minute;

		} else {
			return 0;
		}
	}
	public static boolean verifyCategory ( int taskSelectionNum, String userInput){
		String[] categories = {"Visit", "Shopping", "Appointment", "Class", "Study", "Sleep", "Exercise", "Work", "Meal"};
		switch (taskSelectionNum) {
			case 1:
				return userInput.equals("Cancellation");
			case 2:
				for (int i = 0; i < 3; i++) {
					if (categories[i].equals(userInput)) {
						return true;

					}
				}
				return false;
			case 3:
				for (int i = 3; i < categories.length; i++) {
					if (categories[i].equals(userInput)) {
						return true;
					}
				}
			return false;
			default:
				return false;
		}
	}


		private static void welcomeMessage () {
			System.out.println("Welcome to PSS!\n");
		}

		private static void chooseUse () {
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
						//view schedule
					case 2:
						chooseUse();
						createTask();
					case 3:
					case 4:
					case 5:
					case 6:
					default:
						System.out.println("Invalid input, please try again\n");
						chooseUse();
				}
			}
		}


		private static void createTask () {
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






