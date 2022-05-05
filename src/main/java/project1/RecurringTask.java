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
        boolean validName = false;
        while(!validName) {
            String inputName = keyboard.nextLine().trim();
            if (!Schedule.hm.containsKey(inputName) && inputName.length() <= 30) {
                validName = true;
                this.setName(inputName);
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
                    System.out.println("Task not created, returning to main menu.\n");
                    keyboard.close();
                    return;
                }
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
                keyboard.close();
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
        while(true){
            System.out.println("Enter start hour: ");
            float startHour = (keyboard.nextFloat());
            System.out.println("Enter start minute: ");
            float startMinute = (keyboard.nextFloat());
            System.out.println("Enter start am or pm: ");
            String dayTime = (keyboard.next());
            if (Main.verifyStartTime(startHour, startMinute, dayTime) != 0) {
                setStartTime(Main.verifyStartTime(startHour, startMinute, dayTime));
                break;
            }
            System.out.println("Invalid time. retry date entry?\n 1. Yes\n 2. No\n");
            int choice = keyboard.nextInt();
            if (choice != 1){
                createFailed();
                keyboard.close();
                return;
            }
        }
        while(true ) {
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
                keyboard.close();
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
                keyboard.close();
                return;
            }
        }
        int iStartDate;
        while(true) {
            System.out.println("Enter start date in format YYYYMMDD: ");
            iStartDate = (keyboard.nextInt());
            if (Main.verifyDate(iStartDate)) {

                setStartDate(iStartDate);

                break;
            }
            System.out.println("Invalid start date. retry start date entry?\n 1. Yes\n 2. No\n");
            int choice = keyboard.nextInt();
            if (choice != 1){
                createFailed();
                keyboard.close();
                return;
            }
        }
        while(true) {
            System.out.println("Enter end date: ");
            int iEndDate = (keyboard.nextInt());
            keyboard.close();
            if (Main.verifyEndDate(iStartDate,iEndDate)) {

                setEndDate(iEndDate);
                break;
            }
            System.out.println("Invalid date. retry date entry?\n 1. Yes\n 2. No\n");
            int choice = keyboard.nextInt();
            if (choice != 1){
                createFailed();
                keyboard.close();
                return;
            }
        }

        return;
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
                keyboard.close();
                return;
            }
            if (select == 1){
                System.out.println("Enter Task Name: ");
                boolean validName = false;
                while(!validName) {
                    String inputName = keyboard.nextLine().trim();
                    if (!Schedule.hm.containsKey(inputName) && inputName.length() <= 30) {
                        validName = true;
                        this.setName(inputName);
                        editSuccess();
                        keyboard.close();
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
                            keyboard.close();
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
                        keyboard.close();
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
                keyboard.close();
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
                        keyboard.close();
                        return;
                    }
                    System.out.println("Invalid time. retry date entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        keyboard.close();
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
                        keyboard.close();
                        return;
                    }
                    System.out.println("Invalid duration. retry duration entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        keyboard.close();
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
                        keyboard.close();
                        return;
                    }
                    System.out.println("Invalid start date. retry start date entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        keyboard.close();
                        return;
                    }
                }
            }
            if (select == 6){
                int iStartDate = startDate;
                while(true) {
                    System.out.println("Enter end date: ");
                    int iEndDate = (keyboard.nextInt());
                    keyboard.close();
                    if (Main.verifyEndDate(iStartDate,iEndDate)) {

                        setEndDate(iEndDate);
                        break;
                    }
                    System.out.println("Invalid end date. retry end date entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        keyboard.close();
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
                        keyboard.close();
                        return;
                    }
                    System.out.println("Invalid frequency. retry frequency entry?\n 1. Yes\n 2. No\n");
                    int choice = keyboard.nextInt();
                    if (choice != 1){
                        editFailed();
                        keyboard.close();
                        return;
                    }
                }
            }
            if (setValid == true) {
                System.out.println("\nInvalid parameter, please enter again.");
            }
        }
    }

    private void createFailed() {
        System.out.println("Task not created, returning to main menu.\n");
    }

    private void editFailed() {
        System.out.println("Task not edited, returning to main menu.\n");
    }

    private void editSuccess() {
        System.out.println("Task edit successful, returning to main menu.\n");
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

    public float getStartTime() {
        return startTime;
    }

    public String[] getValidTypes() {
        return validTypes;
    }
}
