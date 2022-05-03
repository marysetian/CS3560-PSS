package project1;

import java.util.Scanner;

public class RecurringTask extends Task {
    // todo : double check if these can be inherited from Task
//    private String name;
//    private String type;
//    private float startTime;
//    private float duration;
    //
    //

    private int startDate;  //YYYYMMDD
    private int endDate;    //YYYYMMDD
    private int frequency;  // 1 - daily , 7 weekly
    private boolean available = false;  // true = anti-task exists for this task
    private String[] validTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"};


    /**
    * constructor for a Recurring Task
    * @param name   name of Recurring task - must be unique
    * @param type   must be a value from type array
    * @param StartTime  time task begins
    * @param duration   duration of task
    * @param startDate  Date task begins
    * @param endDate    Date task ends
    * @param frequency  how often task occurs
    * */
    public RecurringTask(String name, String type, float StartTime, float duration,
    		int startDate, int endDate, int frequency){
    	this.name = name;
        this.startTime = StartTime; 
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
    }
    
    public RecurringTask() {
    	
    }
    public void create() {
    	Scanner keyboard = new Scanner(System.in);
    	System.out.println("\t\t\tCREATE RECURRING TASK");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Enter Task Name: ");
        //TODO: determine if main method or task method determines validity
    	boolean validName = false;
    	while(!validName) {
        	String inputName = keyboard.nextLine().trim();
    		if (Schedule.hm.containsKey(inputName)) {
	    		System.out.println("Invalid name: schedule contains duplicate.\n"
	    							+ "Retry name entry? 1. Yes 2. No\n");
	    		int choice = keyboard.nextInt();
	    		if (choice == 1) {
	    			continue;
	    		}
	    		else {
	    			keyboard.close();
	    			return;
	    		}
    		}
    		validName = true;
    		this.setName(inputName);
    	}
    	boolean setValid = true;
        while(setValid) {
            System.out.println("\nSelect Type: \n");
            System.out.println("1. Class \n2. Study \n3. Sleep \n4. "
            		+ "Exercise \n5. Work \n6. Meal \n");
    		int choice = keyboard.nextInt();
            if (choice == 1){
                setType(validTypes[0]);
                setValid = false;}
            if (choice == 2){
                setType(validTypes[1]);
                setValid = false;}
            if (choice == 3){
                setType(validTypes[2]);
                setValid = false;}
            if (choice == 4){
                setType(validTypes[3]);
                setValid = false;}
            if (choice == 5){
                setType(validTypes[4]);
                setValid = false;}
            if (choice == 6){
                setType(validTypes[5]);
                setValid = false;}
            if (setValid == true) {
            	System.out.println("\nInvalid type, please enter again.");
            }
        }
        System.out.println("Enter start hour: ");
        float startHour = (keyboard.nextFloat());

        System.out.println("Enter start minute: ");
        float startMinute = (keyboard.nextFloat());

        System.out.println("Enter start am or pm: ");
        String dayTime = (keyboard.next());
        Main.verifyStartTime(startHour, startMinute, dayTime);
        
        System.out.println("Enter Duration hour: ");
        float durHour = (keyboard.nextFloat());

        System.out.println("Enter Duration minute: ");
        float durMinute = (keyboard.nextFloat());
        Main.verifyDuration(durHour, durMinute);


        System.out.println("Enter frequency: ");
        int iFrequency = (keyboard.nextInt());
        Main.verifyFrequency(iFrequency);
	    
        System.out.println("Enter start year in format YYYY: ");
        int startYear = (keyboard.nextInt());

        System.out.println("Enter start month in format MM: ");
        int startMonth = (keyboard.nextInt());

        System.out.println("Enter start day in format DD: ");
        int startDay = (keyboard.nextInt());

        Main.verifyDate(startYear, startMonth, startDay);

        int iStartDate = startYear + startMonth + startDay;
        
        System.out.println("Enter end date: ");
        int iEndDate = (keyboard.nextInt());
        Main.verifyEndDate(iStartDate,iEndDate);
        keyboard.close();
    }
    /**
    * print all attributes of Task
    * */
    public void view() {
    	System.out.println("Name: " + getName() +"\nType: " + getType() + "\nStart time: " + getStartTime()
    						+ "\nDuration: " + getDuration() + "\n Start date: " + getStartDate()
    						+ "\nEnd date: " + endDate + "\nfrequency: " + getFrequency());
    }

    /**
    * deletes task and corresponding anti-task
    * */
    public void delete(){}

    /**
    * displays a menu for a user to edit any task attributes except "available"
    * */
    public void edit(){
    Scanner keyboard = new Scanner(System.in);
    boolean setValid = true;
    while(setValid) {
        System.out.println("\nSelect parameter to edit: \n");
        System.out.println("1. Name \n2. Type \n3. Start Time \n4. Duration"
        		+ "Start date \n5. End date \n6. Frequency \n");
		int choice = keyboard.nextInt();
		//switch for parameter to edit
        if (choice == 1){
            setType(validTypes[0]);
            setValid = false;}
        if (choice == 2){
            setType(validTypes[1]);
            setValid = false;}
        if (choice == 3){
            setType(validTypes[2]);
            setValid = false;}
        if (choice == 4){
            setType(validTypes[3]);
            setValid = false;}
        if (choice == 5){
            setType(validTypes[4]);
            setValid = false;}
        if (choice == 6){
            setType(validTypes[5]);
            setValid = false;}
        if (setValid == true) {
        	System.out.println("\nInvalid parameter, please enter again.");
        	}
    	}
    }
    private void setName(String name) {
    	this.name = name;
    }
    
    private void setType(String type) {
    	this.type = type;
    }
    
    private void setStartTime(float startTime) {
    	this.startTime = startTime;
    }

    private void setDuration(float duration) {
    	this.duration = duration;
    }
    
    private void setStartDate(int startDate) {
    	this.startDate = startDate;
    }
    
    private void setEndDate(int endDate) {
    	this.endDate = endDate;
    }
    
    private void setFrequency(int frequency) {
    	this.frequency = frequency;
    }

    public String getName() {
    	return name;
    }
	public int getStartDate() {
		return startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public int getFrequency() {
		return frequency;
	}

	public boolean isAvailable() {
		return available;
	}
    
	public float getDuration() {
		return duration;
	}
	
	public String getType() {
		return type;
	}
	
	public float getStartTime() {
		return startTime;
	}
    
	public String[] getValidTypes() {
		return validTypes;
	}
}

