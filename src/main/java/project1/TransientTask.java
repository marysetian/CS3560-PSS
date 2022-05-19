package project1;

import java.util.Scanner;

public class TransientTask extends Task {
    private int date;    // YYYYMMDD
    private String[] validTypes = {"Visit", "Shopping", "Appointment"};
    private final String classType = "Transient";


    /**
     * constructor for a TransientTask
     * @param name   name of Transient task - must be unique
     * @param type   must be a value from type array
     * @param startTime  time task begins
     * @param duration   duration of task
     * @param date    used with startTime to cancel a Recurring task
     * */
    public TransientTask(String name, String type, float startTime, float duration, int date, String taskType){
        // todo : implement constructor
        super(name,type,startTime,duration, taskType);
        this.date = date;

    }

    /**
     * create a tranisient task
     */
    public TransientTask() {}

    public void createFromFile(String iname, int date, float startTime, float duration, String type)
    {
        boolean valid = false;

        // validate name of task
        boolean validName = false;
        if (!Schedule.hm.containsKey(iname)) {
            validName = true;
        }

        //validate taskType
        boolean validTaskType = false;
        if (type.equals("Shopping") || type.equals("Appointment") || type.equals("Visit") ){
            validTaskType = true;
        }

        // validate task startTime
        boolean validStartTime = false;
        float startHour ;
        float startMin = 0;
        String dayTime;
        if (startTime <= 12){
            startHour = (float) Math.floor(startTime);
            dayTime = "am";
        }
        else {
            startHour = (float) Math.floor(startTime - 12);
            dayTime = "pm";
        }
        float formattedStartTime = Main.verifyStartTime(startHour, startMin, dayTime);
        if (formattedStartTime != 0)
            validStartTime = true;

        // validate duration
        boolean validDuration = false;
        float durationHour = (float) Math.floor(duration);
        float durationMin = duration - (int) Math.floor(duration);
        if(durationMin == .25){
            durationMin = 15;
        }
        else if(durationMin == .5){
            durationMin = 30;
        }
        else if(durationMin == .75){
            durationMin = 45;
        }
        else{
            durationMin =0;
        }
        float formattedDuration = Main.verifyDuration(durationHour, durationMin);
        if(formattedDuration != 0){
            validDuration = true;
        }

        // validate date
        boolean validDate = false;
        if(Main.verifyDate(date))
        {
            validDate = true;
        }

        if(validName && validDate && validDuration && validTaskType && validStartTime){
            setName(iname);
            setDate(date);
            setDuration(formattedDuration);
            setType(type);
            setStartTime(formattedStartTime);
            setTaskType(classType);
        }
    }

    public void create()
    {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("\t\t\t\t\t\tCREATE TRANSIENT TASK");
        System.out.println("--------------------------------------------------------------------------------");


        boolean validName = false;
        while(!validName) {
            System.out.println("Enter Transient Task Name: ");
            String inputName = keyboard.nextLine().trim();
            if (Schedule.hm.containsKey(inputName)) {
                System.out.println("Invalid name: schedule contains duplicate.");

            }
            else {
                validName = true;
                setName(inputName);
            }
        }

        boolean setValid = false;
        while(!setValid) {
            System.out.println("Select Type: ");
            System.out.println("1. Visit \n2. Shopping \n3. Appointment");
            int iType = keyboard.nextInt();
            if (iType == 1){
                setType(validTypes[0]);
                setValid = true;}
            else if (iType == 2){
                setType(validTypes[1]);
                setValid = true;}
            else if (iType == 3){
                setType(validTypes[2]);
                setValid = true;}
            else
                System.out.println("\nInvalid type, please enter again.");
        }

        //start time input and verification
        boolean validStartTime = false;
        while(!validStartTime) {
            System.out.println("Enter start hour (1-12): ");
            float startHour = (keyboard.nextFloat());

            System.out.println("Enter start minute: ");
            float startMinute = (keyboard.nextFloat());

            System.out.println("Enter start am or pm: ");
            String dayTime = (keyboard.next());
            float iStart = Main.verifyStartTime(startHour, startMinute, dayTime);
            if(iStart != 0)
            {
                setStartTime(iStart);
                validStartTime = true;
            }
            else
                System.out.println("INVALID Start Time, Enter a Start Hour between 1 - 12 and Start Minute between 0 - 59");
        }

        //duration time input and verification
        boolean validDuration = false;
        while(!validDuration) {
            System.out.println("Enter Duration hour: ");
            float durHour = (keyboard.nextFloat());

            System.out.println("Enter Duration minute: ");
            float durMinute = (keyboard.nextFloat());

            float iDuration = Main.verifyDuration(durHour, durMinute);

            if(iDuration != 0) {
                setDuration(iDuration);
                validDuration = true;
            }
            else
                System.out.println("INVALID Duration, Enter numbers greater than 0 for Duration Hour and Minute");
        }


        //date input and verification  yyyy mm dd
        boolean validDate = false;
        while(!validDate) {
            System.out.println("Enter Date (YYYYMMDD): ");
            int iDate = keyboard.nextInt();

            if(Main.verifyDate(iDate))
            {
                setDate(iDate);
                validDate = true;
            }
            else
                System.out.println("INVALID Date, Enter valid date values");
        }

        setTaskType(classType);

    }


    /**
     * print all attributes of TransientTask
     * */
    public void view(){
        System.out.println("Name: " + getName() +"\nType: " + getType() + "\nStart Time: " + getStartTime() + "\nDuration: " + getDuration() + "\nDate: " + getDate() + "\nTask Type: " + getTaskType());
    }

    /**
     * delete anti-task iff there is no transient task overlapping the recurring task corresponding to this antitask
     * */
    public void delete()
    {
        Schedule.hm.remove(getName());
    }

    /**
     * displays a menu for a user to edit any task attribute
     * */
    public void edit() {
        System.out.println("\t\t\t\t\t\tEDIT TRANSIENT TASK");
        System.out.println("--------------------------------------------------------------------------------");
        while (true) {
            System.out.println("Current Task Attributes:");
            view();

            System.out.println("Select an attribute to edit or exit: ");
            System.out.println("0. Exit \n1. Name\n2. Type \n3. Start Time \n4. Duration \n5. Date");

            Scanner keyboard = new Scanner(System.in);
            int input = Integer.parseInt(keyboard.nextLine());

            if (input == 0)
                break;

            switch (input) {
                case 1:
                    boolean validName = false;
                    while(!validName) {
                        System.out.println("Enter New Transient Task Name: ");
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
                        setName(inputName);
                    }
                    break;
                case 2:
                    boolean setValid = false;
                    while(!setValid) {
                        System.out.println("Select New Type: ");
                        System.out.println("1. Visit \n2. Shopping \n3. Appointment");
                        int iType = keyboard.nextInt();
                        if (iType == 1){
                            setType(validTypes[0]);
                            setValid = true;}
                        else if (iType == 2){
                            setType(validTypes[1]);
                            setValid = true;}
                        else if (iType == 3){
                            setType(validTypes[2]);
                            setValid = true;}
                        else
                            System.out.println("\nInvalid type, please enter again.");

                    }
                    break;
                case 3:
                    boolean validStartTime = false;
                    while(!validStartTime) {
                        System.out.println("Enter New start hour (1-12): ");
                        float startHour = (keyboard.nextFloat());

                        System.out.println("Enter New start minute (0-59): ");
                        float startMinute = (keyboard.nextFloat());

                        System.out.println("Enter New start (am/pm): ");
                        String dayTime = (keyboard.next());
                        float iStart = Main.verifyStartTime(startHour, startMinute, dayTime);
                        if(iStart != 0)
                        {
                            setStartTime(iStart);
                            validStartTime = true;
                        }
                        else
                            System.out.println("INVALID Start Time, Enter a Start Hour between 1 - 12 and Start Minute between 0 - 59");
                    }
                    break;
                case 4:
                    boolean validDuration = false;
                    while(!validDuration) {
                        System.out.println("Enter New Duration hour: ");
                        float durHour = (keyboard.nextFloat());

                        System.out.println("Enter New Duration minute: ");
                        float durMinute = (keyboard.nextFloat());

                        float iDuration = Main.verifyDuration(durHour, durMinute);

                        if(iDuration != 0) {
                            setDuration(iDuration);
                            validDuration = true;
                        }
                        else
                            System.out.println("INVALID Duration, Enter numbers greater than 0 for Duration Hour and Minute");
                    }
                    break;
                case 5:
                    boolean validDate = false;
                    while(!validDate) {
                        System.out.println("Enter New Date (YYYYMMDD): ");
                        int iDate = keyboard.nextInt();

                        if(Main.verifyDate(iDate))
                        {
                            setDate(iDate);
                            validDate = true;
                        }
                        else
                            System.out.println("INVALID Date, Enter valid date values");
                    }
                    break;
            }
        }
    }

    // todo : create getters and setters
    private void setDate(int date)
    {
        this.date = date;
    }

    public int getDate()
    {
        return date;
    }



}