package project1;

import java.util.GregorianCalendar;
import java.util.Scanner;
import java.lang.Math; // Needed to use Math.round()
import java.util.Calendar;

public class Main {

	public static void main(String[] args) {
		welcomeMessage();
		chooseUse();
	}

	private static void welcomeMessage() {
		System.out.println("Welcome to PSS!\n");
	}

	private static void chooseUse() {
		System.out.println("Please choose from the following:\n"
				+ "1. view schedule\n"
				+ "2. create task\n"
				+ "3. delete task\n"
				+ "4. edit task\n"
				+ "5. read schedule from file\n"
				+ "6. write schedule to a file\n"
				+ "7. exit program\n");
		try (Scanner keyboard = new Scanner(System.in)) {
			int choice = keyboard.nextInt();
			switch (choice) {
				case 1:
					// view schedule
					//
					//
					break;
				case 2:
					createTask();
					// call menu for user to specify the type of task they want to create
					// call create method for the certain task type
					break;
				case 3:
					// delete a task
					break;
				case 4:
					// edit task
					break;
				case 5:
					// read schedule from file
					break;
				case 6:
					createWriteMenu();

					break;
				case 7:
					// exit program
					break;
				default:
					System.out.println("Invalid input, please try again\n");
					chooseUse();
			}
		}
	}


	public static void createWriteMenu(){
		// write schedule to file
		System.out.println("Please choose which schedule you would like to print to file: \n"
				+ "1. Daily\n"
				+ "2. Weekly\n"
				+ "3. Monthly\n" );

		try (Scanner kb = new Scanner(System.in)) {
			int opt = kb.nextInt();
			switch (opt) {
				case 1:
					// daily schedule  -- Mary

					break;
				case 2:
					//Weekly schedule  -- Linda


					break;
				case 3:
					// monthly schedule -- Alondra

					break;
				default:
					System.out.println("Invalid input, please try again\n");
					chooseUse();													/// need to update
			}
		}
	}

	private static void createTask() {
		System.out.println("Choose from the following task types to create:\n "
				+ "1. Transient Task"
				+ "2. Recurring Task"
				+ "3. Anti Task");
		try (Scanner keyboard = new Scanner(System.in)) {
			int choice = keyboard.nextInt();
			switch (choice) {
				case 1:
					// Transient Task
					// ask user to name the task
					// verify name is not a duplicate
					// create task with an empty constructor
					// call create method for corresponding task

					TransientTask Test1 = new TransientTask();
					Test1.create();
					Schedule.hm.put(Test1.getName(), Test1);


//					Test1.view();
//
//					Test1.edit();
//					Test1.view();


					break;

				case 2:
					RecurringTask test1 = new RecurringTask();
					test1.create();
					test1.view();

					//test1.edit();
					//test1.view();
					// Recurring Task
					// ask user to name the task
					// verify name is not a duplicate
					// create task with an empty constructor
					// call create method for corresponding task
					break;

				case 3:
					// anti task
					// ask user to name the task
					// verify name is not a duplicate
					// create task with an empty constructor
					// call create method for corresponding task

					AntiTask antiTest = new AntiTask();
					antiTest.create();
					antiTest.view();

					antiTest.edit();
					antiTest.view();

					break;
				default:
					System.out.println("Invalid input, please try again\n");
					createTask();
			}
		}

	}

	private static void createSchedule() {

	}

	/**
	 * verifies the date of task is valid
	 * @param date		date in form YYYYMMDD given by user
	 * @return boolean    true if date >= current date
	 */
	public static boolean verifyDate(int date) {
		Calendar today = new GregorianCalendar();
		int currentYear = today.get(Calendar.YEAR);
		int currentMonth = today.get(Calendar.MONTH) + 1;
		int currentDay = today.get(Calendar.DAY_OF_MONTH);

		// parse date into year, month, day
		String tempDate = String.valueOf(date);
		int year = Integer.valueOf(tempDate.substring(0,4));
		int month = Integer.valueOf(tempDate.substring(4,6));;
		int day = Integer.valueOf(tempDate.substring(6,8));;

		if( year >= currentYear && month >= currentMonth && day >= currentDay)
			return true;

		return false;
	}

	/**
	 * verifies the given endDate is greater than or equal to startDate
	 *
	 * @param startDate an integer in form YYYYMMDD
	 * @param endDate   an integer in form YYYYMMDD
	 * @return true if endDate is greater than or equal to startDate
	 */
	public static boolean verifyEndDate(int startDate, int endDate) {
		return endDate > startDate;
	}

	/**
	 * verfies user input a valid frequency for task
	 *
	 * @param frequency an int holding the user given input
	 * @return true if frequency is 1 or 7, false otherwise
	 */
	public static boolean verifyFrequency(int frequency) {
		return (frequency == 1 || frequency == 7);
	}

	/**
	 * verifies the user input for start time is valid
	 *
	 * @param hour    user given hour the task starts at
	 * @param minute  user given minute task starts at
	 * @param dayTime a user given string, "am" indicating morning start time, "pm" indicating night start time
	 * @reutrn startTime    a float holding modified startTime form, 0 if user inputs are found invalid
	 */
	public static float verifyStartTime(float hour, float minute, String dayTime) {

		// is hour between 0 and 24 inclusive, is minute between 0 and 59 inclusive
		if ((0 <= hour && hour <= 12) && (0 <= minute && minute <= 59)) {
			switch (dayTime) {
				case "am":
					minute = minute / 15;
					minute = Math.round(minute);
					minute = (float) (minute * .25);
					return hour + minute;

				case "pm":
					hour += 12;
					minute = minute / 15;
					minute = Math.round(minute);
					minute = (float) (minute * .25);
					if(hour == 24)
						return minute;
					return hour + minute;
			}
		}
		return 0;
	}

	public static float verifyDuration(float hour, float minute) {
		// are both hour and minute positive integers
		if (hour >= 0 && minute >= 0) {
			if (minute < 60) {
				minute = Math.round(minute / 15);
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

	public static boolean verifyCategory(int taskSelectionNum, String userInput) {
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
}

