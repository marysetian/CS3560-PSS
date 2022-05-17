package project1;

import java.util.*;

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
    private String taskType = "recurring";
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
        this.type = type;
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
        boolean validName = false;
        while(!validName) {
            System.out.println("Enter Task Name: ");
            String inputName = keyboard.nextLine().trim();
            if (!Schedule.hm.containsKey(inputName) && inputName.length() <= 30) {
                validName = true;
                this.setName(inputName);
            }
            else {
                if (Schedule.hm.containsKey(inputName)) {
                    System.out.println("Invalid name: schedule contains duplicate.\n");
                }
                else if (inputName.length() > 30) {
                    System.out.println("Invalid name: name too long.\n");
                }
                System.out.println("Retry name entry?\n1. Yes \n2. No");
                int choice = keyboard.nextInt();
                if (choice != 1) {
                	createFailed();
                    return;
                }
                //consume nextline to read next iteration properly
                keyboard.nextLine();
            }
        }
        boolean setValid = true;
        while(setValid) {
            System.out.println("\nSelect Type, or enter 0 to return to menu: \n");
            System.out.println("0. Exit\n1. Class \n2. Study \n3. Sleep \n4. "
                    + "Exercise \n5. Work \n6. Meal \n");
            int choice = keyboard.nextInt();
            if (choice == 0) {
                createFailed();
                return;
            }
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
        //loop for checking for task conflicts once task parameters set
        while(true) {
        	//loop for entering time, and verifying validity
	        while(true){
	            System.out.println("Enter start hour: ");
	            float startHour = (keyboard.nextFloat());
	            System.out.println("Enter start minute: ");
	            float startMinute = (keyboard.nextFloat());
	            System.out.println("Enter start am or pm: ");
	            String dayTime = (keyboard.next());
	            float absoluteTime = Main.verifyStartTime(startHour, startMinute, dayTime);
	            if (absoluteTime != 0) {
	                setStartTime(absoluteTime);
	                break;
	            }
	            System.out.println("Invalid time. retry time entry?\n 1. Yes\n 2. No\n");
	            int choice = keyboard.nextInt();
	            if (choice != 1){
	                createFailed();
	                return;
	            }
	        }
	        while(true) {
	            System.out.println("Enter Duration hour: ");
	            float durHour = (keyboard.nextFloat());
	            System.out.println("Enter Duration minute: ");
	            float durMinute = (keyboard.nextFloat());
	            if (Main.verifyDuration(durHour, durMinute) != 0) {
	                setDuration(Main.verifyDuration(durHour, durMinute));
	                break;
	            }
	            System.out.println("Invalid duration. retry duration entry?\n 1. Yes\n 2. No\n");
	            int choice = keyboard.nextInt();
	            if (choice != 1){
	                createFailed();
	                return;
	            }
	        }
	
	        while(true) {
	            System.out.println("Enter frequency: ");
	            int iFrequency = (keyboard.nextInt());
	            if (Main.verifyFrequency(iFrequency)) {
	                setFrequency(iFrequency);
	                break;
	            }
	            System.out.println("Invalid frequency. retry frequency entry?\n 1. Yes\n 2. No\n");
	            int choice = keyboard.nextInt();
	            if (choice != 1){
	                createFailed();
	                return;
	            }
	        }
	        int iStartDate;
	        while(true) {
	            System.out.println("Enter start date in format YYYYMMDD: ");
	            iStartDate = keyboard.nextInt();
	            if (Main.verifyDate(iStartDate)) {
	
	                setStartDate(iStartDate);
	
	                break;
	            }
	            System.out.println("Invalid start date. retry start date entry?\n 1. Yes\n 2. No\n");
	            int choice = keyboard.nextInt();
	            if (choice != 1){
	                createFailed();
	                return;
	            }
	        }
	        while(true) {
	            System.out.println("Enter end date: ");
	            int iEndDate = (keyboard.nextInt());
	            if (Main.verifyEndDate(iStartDate,iEndDate)) {
	
	                setEndDate(iEndDate);
	                break;
	            }
	            System.out.println("Invalid date. retry date entry?\n 1. Yes\n 2. No\n");
	            int choice = keyboard.nextInt();
	            if (choice != 1){
	                createFailed();
	                return;
	            }
	        }
	        //check for conflicts
	        while (true) {
		        Task conflictTask = checkConflicts(startTime, duration, startDate, endDate);
		        if (conflictTask != null) {
		        	outputConflict(conflictTask);
		        	if (conflictTask instanceof TransientTask) {
		        		System.out.println("Automatically create antitask?\n 1. Yes\n 2. No\n");
			            int choice = keyboard.nextInt();
			            if (choice == 1){
			                //automatically create antiTask
			            	AntiTask autoAnti = new AntiTask("anti_"+name+"_"+((TransientTask)conflictTask).getDate(),
			            			"Cancellation",conflictTask.getStartTime(),conflictTask.getDuration(),
			            			((TransientTask) conflictTask).getDate());
			                //TODO: add "antiTask" at end of constructor for new anti
			            	Schedule.hm.put(autoAnti.getName(), autoAnti);
			            	available = true;
			            	continue;
			            }
			            System.out.println("retry task creation?\n 1. Yes\n 2. No\n");
			            int choice1 = keyboard.nextInt();
			            if (choice1 != 1){
			            	//delete any automatic antitasks created
			            	delete();
			            	createFailed();
			            	return;
			            }
		        	}
		        }
		        break;
	        }
	        Schedule.hm.put(name, this);
	        return;
        }
    }
    /**
     * print all attributes of Task
     * */
    public void view() {
        System.out.println("Name: " + getName() +"\nType: " + getType() + "\nStart time: " + getStartTime()
                + "\nDuration: " + getDuration() + "\nStart date: " + getStartDate()
                + "\nEnd date: " + endDate + "\nfrequency: " + getFrequency());
    }

    /**
     * displays a menu for a user to edit any task attributes except "available"
     * */
    public void edit(){
        Scanner keyboard = new Scanner(System.in);
        boolean setValid = true;
        while(setValid) {
            System.out.println("\nSelect parameter to edit or 0 to exit: \n");
            System.out.println("0. Exit\n1. Name \n2. Type \n3. Start Time \n4. Duration"
                    +"\n5. Start date \n6. End date \n7. Frequency \n");
            int select = keyboard.nextInt();
            //switch for parameter to edit
            if (select == 0){
                editFailed();
                return;
            }
            if (select == 1){
                System.out.println("Enter Task Name: ");
                boolean validName = false;
                while(!validName) {
                	//consume next line
                	keyboard.nextLine();
                    String inputName = keyboard.nextLine().trim();
                    if (!Schedule.hm.containsKey(inputName) && inputName.length() <= 30) {
                        validName = true;
                        this.setName(inputName);
                        editSuccess();
                        return;
                    }
                    else {
                        if (Schedule.hm.containsKey(inputName)) {
                            System.out.println("Invalid name: schedule contains duplicate.\n");
                        }
                        else if (inputName.length() >= 30) {
                            System.out.println("Invalid name: name too long.\n");
                        }
                        System.out.println("Retry name entry?\n1. Yes \n2. No");
                        int choice = keyboard.nextInt();
                        if (choice == 1) {
                            continue;
                        }
                        else {
                            editFailed();
                            return;
                        }
                    }
                }
            }
            if (select == 2){
                while(setValid) {
                    System.out.println("\nSelect Type, or enter 0 to return to menu: \n");
                    System.out.println("0. Exit\n1. Class \n2. Study \n3. Sleep \n4. "
                            + "Exercise \n5. Work \n6. Meal \n");
                    int choice = keyboard.nextInt();
                    if (choice == 0) {
                        editFailed();
                        return;
                    }
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
                editSuccess();
                return;
            }
            if (select == 3){
                while(true){
                    System.out.println("Enter start hour: ");
                    float startHour = (keyboard.nextFloat());
                    System.out.println("Enter start minute: ");
                    float startMinute = (keyboard.nextFloat());
                    System.out.println("Enter start am or pm: ");
                    String dayTime = (keyboard.next());
                    if (Main.verifyStartTime(startHour, startMinute, dayTime) != 0) {
                        editSuccess();
                        return;
                    }
                    System.out.println("Invalid time. retry date entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        return;
                    }
                }
            }
            if (select == 4){
                while(true ) {
                    System.out.println("Enter Duration hour: ");
                    float durHour = (keyboard.nextFloat());
                    System.out.println("Enter Duration minute: ");
                    float durMinute = (keyboard.nextFloat());
                    if (Main.verifyDuration(durHour, durMinute) != 0) {
                        editSuccess();
                        return;
                    }
                    System.out.println("Invalid duration. retry duration entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        return;
                    }
                }
            }
            if (select == 5){
                while(true) {
                    System.out.println("Enter start date in format YYYYMMDD: ");
                    int iStartDate = (keyboard.nextInt());
                    if (Main.verifyFrequency(iStartDate)) {
                        editSuccess();
                        return;
                    }
                    System.out.println("Invalid start date. retry start date entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        return;
                    }
                }
            }
            if (select == 6){
                int iStartDate = startDate;
                while(true) {
                    System.out.println("Enter end date: ");
                    int iEndDate = (keyboard.nextInt());
                    if (Main.verifyEndDate(iStartDate,iEndDate)) {

                        setEndDate(iEndDate);
                        break;
                    }
                    System.out.println("Invalid end date. retry end date entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        return;
                    }
                }
            }
            if (select == 7){
                while(true) {
                    System.out.println("Enter frequency: ");
                    int iFrequency = (keyboard.nextInt());
                    if (Main.verifyFrequency(iFrequency)) {
                        editSuccess();
                        return;
                    }
                    System.out.println("Invalid frequency. retry frequency entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        return;
                    }
                }
            }
            if (setValid == true) {
                System.out.println("\nInvalid parameter, please enter again.");
            }
        }
    }
    
    /**
     * method for deleting the task and any associated antitasks
     */
    public void delete() {
    	//basic check for no antitasks
    	if (!available) {
    		Schedule.hm.remove(name);
    		return;
    	}
    	//for antitasks present
    	for (Map.Entry mapElement : Schedule.hm.entrySet()) {
    		Task checkTask = (Task)mapElement.getValue();
    		if (checkTask instanceof AntiTask) {
    			if (checkTask.getName().contains("anti_"+name+"_")){
    				Schedule.hm.remove(checkTask.getName());
    			}
    		}
    	}
    	Schedule.hm.remove(name);
    	return;
    }
    /**
     * Method for finding task name conflicts
     * @return  task that conflicts with recurringTask
     */
    public boolean checkNameConflicts() {
    	//basic test for same name
    	if (Schedule.hm.containsKey(name)) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Method for finding task time conflicts
     * @return  task that conflicts with recurringTask
     */
    public Task checkConflicts(float inStartTime, float inDuration, int inStartDate, int inEndDate) {
    	//loop for finding tasks which conflict
    	for (Map.Entry mapElement : Schedule.hm.entrySet()) {
    		Task checkTask = (Task)mapElement.getValue();
    		float endTime = Main.calcEndTime(inStartTime, inDuration);
    		if (checkTask instanceof AntiTask) {
    			continue;
    		}
    		float checkTaskEndTime = Main.calcEndTime(checkTask.getStartTime(), checkTask.getDuration());
    		
    		//check for task conflicts
    		if (isTimeBetween(checkTask,startTime,endTime)
    	    		&&isDateBetween(checkTask, startDate,endDate)) {
    			//case 1: this recurring task occurs 7 days a week
    			if (frequency == 1) {
    				//checkTask is a RecurringTask, either 1 or 7 days a week
    				if (checkTask instanceof RecurringTask) {
    					return checkTask;
		    		}
    				//checkTask is a TransientTask
    				if (checkTask instanceof TransientTask) {
    	    			if(!Schedule.hm.containsKey("anti_"+name+"_"+((TransientTask)checkTask).getDate())) {
    			    			return checkTask;
    			    	}	
    				}
    			}
    			//case 2: this recurring task occurs 1 day a week
        		if (frequency == 7) {
        			Calendar thisTaskDates = new GregorianCalendar();
        			// parse date into year, month, day
        			String tempDate = String.valueOf(startDate);
        			int year = Integer.valueOf(tempDate.substring(0,4));
        			int month = Integer.valueOf(tempDate.substring(4,6));;
        			int day = Integer.valueOf(tempDate.substring(6,8));;
        			if(checkTask instanceof RecurringTask) {
        				
        			}
        			if(checkTask instanceof TransientTask) {
        				
        			}
        		}
    		}
    	}
    	return null;
    }
    
    /**
     * Method for finding task date conflicts
     * @return  task that conflicts with recurringTask
     */
    /*
    public Task checkDateConflicts() {

    	for (Map.Entry mapElement : Schedule.hm.entrySet()) {
    		Task checkTask = (Task)mapElement.getValue();
    		float checkTaskEndTime = checkTask.getStartTime() + checkTask.getDuration();
    		//check for recurring task type conflicts
            if (checkTask instanceof RecurringTask) {
            	if (checkTask.getStartTime() >= startTime
            			&& checkTask.getStartTime() <= (startTime+duration)
            			|| checkTaskEndTime >= startTime
            			&& checkTaskEndTime <= (startTime+duration)) { 
            		return checkTask;
            	}
            }
            if (checkTask instanceof TransientTask) {
            	if (		
            			checkTask.getStartTime() >= startTime
            			&& checkTask.getStartTime() <= (startTime+duration)
            			|| checkTaskEndTime >= startTime
            			&& checkTaskEndTime <= (startTime+duration)) { 
            		return checkTask;
            	}
            }
    	}
    	return null;
    }*/
    /**
     * Method for determining if time of task is at same time as this recurring task
     * @param inTask		task to be checked for time between
     * @param inStartTime	StartTime to check against checkTask
     * @param inEndTime		EndTime to check against checkTask
     * @return				True if task time is between specified times
     */
    private boolean isTimeBetween(Task checkTask, float inStartTime, float inEndTime) {
    	float checkTaskEndTime = Main.calcEndTime(checkTask.getStartTime(), checkTask.getDuration());
    	if ((checkTask.getStartTime() >= inStartTime
    			&& checkTask.getStartTime() <= inEndTime)
    			||  (checkTaskEndTime >= inStartTime
    			&& checkTaskEndTime <= inEndTime)) {
    		return true;
		}
    	return false;
    }
    /**
     * Method for determining if date of task is between dates of this recurring task
     * @param checkTask		task to be checked for date between
     * @param startDate		startDate to check against checkTask
     * @param endDate		endDate to check against checkTask
     * @return				true if task is between dates specified
     */
    private boolean isDateBetween(Task checkTask, int inStartDate, int inEndDate) {
		int checkTaskStartDate;
		int checkTaskEndDate;
		//TODO: Ensure tasks have proper start date and end date based on if they wrap to next day
    	if (checkTask instanceof TransientTask) {
			checkTaskStartDate = ((TransientTask)checkTask).getDate();
			checkTaskEndDate = checkTaskStartDate;
		}
    	else {
    		checkTaskStartDate = ((RecurringTask)checkTask).getStartDate();
    		checkTaskEndDate = ((RecurringTask)checkTask).getEndDate();
    	}
    	if ((checkTaskStartDate >= inStartDate
    			&& checkTaskStartDate <= inEndDate)
    			||  (checkTaskEndDate >= inStartDate
    			&& checkTaskEndDate <= inEndDate)) {
    		return true;
		}
    	return false;
    }
    
    //TODO: date between or time between function to clean up code?
    /**
     * Method for outputting conflict message for user
     * @param conflictTask task that conflicts
     */
    private void outputConflict(Task conflictTask) {
    	if (conflictTask == null) {
    		return;
    	}
    	System.out.println("Task has conflict with task\"" +conflictTask.getName()
    			+ "\" with parameters");
    	if (conflictTask instanceof TransientTask) {
    		((TransientTask) conflictTask).view();
    	}
    	if (conflictTask instanceof RecurringTask) {
    		((RecurringTask) conflictTask).view();
    	}

    }

    private void createSuccess() {
        System.out.println("Task creation successful, returning to main menu.\n");
    }
    
    private void createFailed() {
        System.out.println("Task not created, returning to main menu.\n");
    }

    private void editSuccess() {
        System.out.println("Task edit successful, returning to main menu.\n");
    }
    
    private void editFailed() {
        System.out.println("Task not edited, returning to main menu.\n");
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public void setFrequency(int frequency) {
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
    
    public String getTaskType() {
    	return taskType;
    }

    public float getStartTime() {
        return startTime;
    }

    public String[] getValidTypes() {
        return validTypes;
    }
}
