package project1;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Math; // Needed to use Math.round()


public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		welcomeMessage();
		chooseUse();
	}

	private static void welcomeMessage() {
		System.out.println("Welcome to PSS!\n");
	}


	private static void chooseUse() throws IOException, ParseException {
		System.out.println("Please choose from the following:\n"
				+ "1. view schedule in file\n"
				+ "2. create task\n"
				+ "3. delete task\n"
				+ "4. edit task\n"
				+ "5. view a task\n"
				+ "6. read schedule from file\n"
				+ "7. exit program\n");
		try (Scanner keyboard = new Scanner(System.in)) {
			int choice = keyboard.nextInt();
			switch (choice) {
				case 1:
					// view schedule
					createWriteSchedule(keyboard);
					break;
				case 2:
					createTask();
					System.out.println("testing monthly schedule =============");
		//			Schedule.writeMonthlySchedule(2022,06);
					//createWriteSchedule(keyboard);
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
					// view a task
					viewTask();
					break;
				case 6:
					// read schedule from file
					Schedule.readSchedule();
					break;
				case 7:
					// write schedule to file
	//				createWriteSchedule(keyboard);
					break;
				case 8:
					// exit program
					break;

				default:
					System.out.println("Invalid input, please try again\n");
					chooseUse();
			}
		}

	}


	// ====================================== CREATING TASKS ======================================

	private static void createTask() throws IOException, ParseException {
		System.out.println("Choose from the following task types to create:\n "
				+ "1. Transient Task"
				+ "2. Recurring Task"
				+ "3. Anti Task");
		try (Scanner keyboard = new Scanner(System.in)) {
			int choice = keyboard.nextInt();
			switch (choice) {
				case 1:
					// Transient Task
					createTransientTask();
					chooseUse();
					break;

				case 2:
					//RecurringTask
					createRecurringTask();
					chooseUse();
					break;

				case 3:
					// anti task
					createAntiTask();
					chooseUse();
					break;
				default:
					System.out.println("Invalid input, please try again\n");
					createTask();
			}
		}

	}

	public static void createTransientTask()
	{
		TransientTask tt = new TransientTask();
		tt.create();
		//	Schedule.hm.put(tt.getName(), tt);
		System.out.println("Transient Task " + tt.getName() + " added to Schedule");
		tt.view();
	}

	public static void createAntiTask()
	{
		AntiTask at = new AntiTask();
		at.create();

		//verify no overlaps here
		//	Schedule.hm.put(at.getName(), at);
		System.out.println("Anti Task " + at.getName() + " added to Schedule");
		at.view();
	}

	public static void createRecurringTask()
	{
		RecurringTask rt = new RecurringTask();
		rt.create();
		Schedule.hm.put(rt.getName(), rt);
		System.out.println("Recurring Task " + rt.getName() + " added to Schedule");
		rt.view();

	}

	// ====================================== VERIFY INPUT ======================================

	/**
	 * verifies the date of task is valid
	 * @param date		date in form YYYYMMDD given by user
	 * @return boolean    true if date >= current date
	 */
	public static boolean verifyDate(int date)
	{
		Calendar today = new GregorianCalendar();
		int currentYear = today.get(Calendar.YEAR);
		int currentMonth = today.get(Calendar.MONTH) + 1;
		int currentDay = today.get(Calendar.DAY_OF_MONTH);

		// parse date into year, month, day
		String tempDate = String.valueOf(date);
		int year = Integer.valueOf(tempDate.substring(0,4));
		int month = Integer.valueOf(tempDate.substring(4,6));;
		int day = Integer.valueOf(tempDate.substring(6,8));;

		if( year >= 2020)
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
	public static boolean verifyEndDate(int startDate, int endDate)
	{
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
	 * @return startTime    a float holding modified startTime form, 0 if user inputs are found invalid
	 */
	public static float verifyStartTime(float hour, float minute, String dayTime)
	{
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

	public static float verifyDuration(float hour, float minute)
	{
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

	public static boolean verifyYearMonth(int year, int month)
	{
		Calendar today = new GregorianCalendar();
		int currentYear = today.get(Calendar.YEAR);
		int currentMonth = today.get(Calendar.MONTH) + 1;

		// if viewing current year, month must be >= current month
		if (year == currentYear && month >= currentMonth)
			return true;
			// can view any month of a year after current year
		else if(year > currentYear)
			return true;
			// invalid year and month
		else {
			System.out.println("Invalid year or month. Please try again\n");
			return false;
		}
	}

	// ====================================== CHECK FOR TASK OVERLAP ======================================
	//if true it does overlap
	public static boolean checkOverlapTime(float start1, float dur1, float start2, float dur2)
	{
		if(start1 == start2)
			return true;
		else if(start1 < start2 && start2 < start1 + dur1)
			return true;
		else if(start2 < start1 && start1 < start2 + dur2)
			return true;
		return false;
	}

	public static boolean checkOverlapDate(int startDate, int endDate, int date, int freq)
	{
		int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
		int newDate = startDate;
		while(newDate <= endDate)
		{
			String tempDate = String.valueOf(newDate);
			int year = Integer.valueOf(tempDate.substring(0,4));
			int month = Integer.valueOf(tempDate.substring(4,6));
			int day = Integer.valueOf(tempDate.substring(6,8));
			if(newDate == date)
				return true;

			day = day + freq;
			if(day > days[month - 1])
			{
				day = day - days[month -1];
				if(month < 12)
					month++;
				else{
					month = 1;
					year++;
				}

			}
			StringBuilder sb = new StringBuilder();
			sb.append(year);
			if(month < 10)
				sb.append("0");
			sb.append(month);
			if(day < 10)
				sb.append("0");
			sb.append(day);

			newDate = Integer.valueOf(sb.toString());
			//System.out.println(newDate);
		}
		return false;
	}


	// ====================================== VIEW TASKS/ WRITE SCHEDULE ======================================

	public static void viewTask()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Current Tasks");
		//	System.out.println(Schedule.hm.keySet());
		System.out.println("Select a Task to View: ");
		String view = keyboard.nextLine().trim();
		//	Schedule.hm.get(view).view();
	}

	public static void createWriteSchedule(Scanner keyboard) throws IOException, ParseException {
		System.out.println("Please choose which type of schedule you would like to print:\n"
				+ "1. daily schedule\n"
				+ "2. weekly schedule\n"
				+ "3. monthly schedule\n"
				+ "4. exit program\n");

		int choice = keyboard.nextInt();
		switch (choice) {
			case 1:
				// daily schedule  -- Mary
				String dailyFilename;

				//verify file to write to was created
				do{
					dailyFilename = fileCreation(keyboard);
					System.out.println(dailyFilename);
				}while (dailyFilename.equals("error"));

				// call write mehtod


				break;
			case 2:
				// weekly schedule -- Linda
				String weeklyFilename;
				//verify file to write to was created
				do{
					weeklyFilename = fileCreation(keyboard);
					System.out.println(weeklyFilename);
				}while (weeklyFilename.equals("error"));

				// call method

				break;
			case 3:
				// monthly schedule  -- Alondra
				String monthlyFilename;

				//verify file to write to was created
				do{
					monthlyFilename = fileCreation(keyboard);
					System.out.println(monthlyFilename);
				}while (monthlyFilename.equals("error"));

				// verify date to view monthly schedule is correct
				int year;
				int month;
				do {
					System.out.println("Enter the year whose monthly schedule you'd like to view (YYYY): ");
					year = keyboard.nextInt();
					System.out.println("Enter the month whose monthly schedule you'd like to view (MM): ");
					month = keyboard.nextInt();
				}
				while (!verifyYearMonth(year, month));

				Schedule.writeMonthlySchedule(year, month, monthlyFilename);
				chooseUse();
				break;
			case 4:
				// exit program
				break;
			default:
				System.out.println("Invalid input, please try again\n");
				//				createWriteSchedule(keyboard);
		}
	}

	public static String fileCreation(Scanner keyboard){
		String filename;
		System.out.println("Enter filename where you'd like to print schedule (no extension): ");
		filename = "/Users/alondrasanchez/IdeaProjects/cs3560_pss/"+ keyboard.next() + ".json";
		try{
			FileWriter fw = new FileWriter(filename);
			return filename;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error");
			return "error";
		}

	}

}

