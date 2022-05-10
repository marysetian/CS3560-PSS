package project1;

import java.util.Scanner;

public class AntiTask extends Task{


    // todo : double check if these can be inherited from Task
//    private String name;
//    private String type;
//    private float startTime;
//    private float duration;

    private int date;    // YYYYMMDD
    private final String validType = "Cancellation";
    private String taskType;
    private final String classType = "Anti";


    /**
     * constructor for a AntiTask
     * @param name   name of Recurring task - must be unique
     * @param type   must be a value from type array
     * @param startTime  time task begins
     * @param duration   duration of task
     * @param date    used with startTime to cancel a Recurring task
     * */
    public AntiTask(String name, String type, float startTime, float duration, int date, String taskType){
        // todo : implement constructor
        super(name,type,startTime,duration,taskType);
        this.date = date;
        this.taskType = taskType;
    }

    public AntiTask(){}


    /**
     * create an Anti Task
     */

    public void create()
    {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("\t\t\t\t\t\tCREATE ANTI TASK");
        System.out.println("--------------------------------------------------------------------------------");


        String inputName = new String();

        StringBuilder sb = new StringBuilder();

         boolean validName = false;
         while(!validName) {
            System.out.println("Enter Recurring-Task Name to override: ");
            inputName = keyboard.nextLine().trim();
            if (Schedule.hm.containsKey(inputName) && Schedule.hm.get(inputName).getTaskType().equals("Recurring")) {
                sb.append("Anti_");  //"Anti_name_date"
                sb.append(inputName);
                sb.append("_");
                validName = true;
                }
            else
            {
             System.out.println("Recurring Task Name does not exist, enter valid name");
            }

            }




        //anti task is always Cancellation
        setType(validType);

        //start time input and verification
        /*
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


         */


        RecurringTask getInfo = (RecurringTask) Schedule.hm.get(inputName);
        float aStart = getInfo.getStartTime();
        float aDuratiom = getInfo.getDuration();
        setStartTime(aStart);
        setDuration(aDuratiom);

        /*
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


         */

        int recurStart = getInfo.getStartDate();
        int recurEnd = getInfo.getEndDate();
        //date input and verification  yyyy mm dd
        boolean validDate = false;
        while(!validDate) {
            System.out.println("Enter Date (YYYYMMDD): ");
            int iDate = keyboard.nextInt();

            if(Main.verifyDate(iDate) && Main.verifyEndDate(recurStart, iDate) && Main.verifyEndDate(iDate, recurEnd))
            {
                setDate(iDate);
                sb.append(iDate);
                setName(sb.toString());
                validDate = true;
            }
            else
                System.out.println("INVALID Date, Enter valid date values between Recurring Start and End Date");
        }

        setTaskType(classType);


    }


    /**
     * print all attributes of AntiTask
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
        System.out.println("\t\t\t\t\t\tEDIT ANTI TASK");
        System.out.println("--------------------------------------------------------------------------------");
        while (true) {
            System.out.println("Current Task Attributes:");
            view();

            System.out.println("Select an attribute to edit or exit: ");
            System.out.println("0. Exit \n1. Name\n2. Start Time \n3. Duration \n4. Date");

            Scanner keyboard = new Scanner(System.in);
            int input = Integer.parseInt(keyboard.nextLine());

            if(input == 0)
                break;

            switch (input) {
                case 1:
                    boolean validName = false;
                    while(!validName) {
                        System.out.println("Enter New Anti-Task Name: ");
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
                case 3:
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
                case 4:
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



    // todo : do we want to asd cancelRecurring??
}
