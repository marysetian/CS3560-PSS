package project1;

import java.util.Map;
import java.util.Scanner;

public class TransientTask extends Task {
    // todo : double check if these can be inherited from Task
    // private String name;
    // private String type;
    // private float startTime;
    // private float duration;

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

    public TransientTask() {}


    /**
     * create a tranisient task
     */

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


        boolean set = true; boolean break1 = false;
        for(Map.Entry mapElement : Schedule.hm.entrySet())
        {
            String key = (String) mapElement.getKey();
            if (Schedule.hm.get(key).getTaskType().equals("Transient"))
            {
                TransientTask check = (TransientTask) Schedule.hm.get(key);
                if(getDate() == check.getDate() && Main.checkOverlapTime(getStartTime(), getDuration(),check.getStartTime(), check.getDuration()))
                {
                    System.out.println("Task Overlaps with Transient Task, Cannot be added to Schedule");
                    set = false;
                    break1 = true;
                }
            }
            else if(Schedule.hm.get(key).getTaskType().equals("Recurring"))
            {
                RecurringTask check1 = (RecurringTask) Schedule.hm.get(key);
                if(Main.verifyEndDate(check1.getStartDate(), getDate()) && Main.verifyEndDate(getDate(), check1.getEndDate()))
                {
                    if(Main.checkOverlapTime(getStartTime(),getDuration(), check1.getStartTime(), check1.getDuration()))
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append("anti_");
                        sb.append(check1.getName()); sb.append("_");
                        sb.append(getDate());
                        //System.out.println(sb.toString());
                        if(Schedule.hm.containsKey(sb.toString()))
                        {
                            System.out.println("Anti Task Exists, Added Transient to Schedule");
                            break1 = true;
                        }
                        else
                        {
                            System.out.println("Task Overlaps with Recurring Task, Cannot be added to Schedule");
                            set = false;
                            break1 = true;
                        }
                    }
                }
            }
            if(break1)
                break;
        }
        if(set)
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
        System.out.println("Transient-Task Deleted");
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
            System.out.println("0. Exit \n1. Name\n2. Type \n3. Start Time and Duration \n4. Date");

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
                        Schedule.hm.remove(getName());
                        setName(inputName);
                        Schedule.hm.put(getName(),this);

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
                    boolean validStartTime = false; float iStart = 0;
                    while(!validStartTime) {
                        System.out.println("Enter New start hour (1-12): ");
                        float startHour = (keyboard.nextFloat());

                        System.out.println("Enter New start minute (0-59): ");
                        float startMinute = (keyboard.nextFloat());

                        System.out.println("Enter New start (am/pm): ");
                        String dayTime = (keyboard.next());
                        iStart = Main.verifyStartTime(startHour, startMinute, dayTime);
                        if(iStart != 0)
                        {
                           // setStartTime(iStart);
                            validStartTime = true;
                        }
                        else
                            System.out.println("INVALID Start Time, Enter a Start Hour between 1 - 12 and Start Minute between 0 - 59");
                    }
                        boolean validDuration = false; float iDuration = 0;
                        while(!validDuration) {
                            System.out.println("Enter New Duration hour: ");
                            float durHour = (keyboard.nextFloat());

                            System.out.println("Enter New Duration minute: ");
                            float durMinute = (keyboard.nextFloat());

                            iDuration = Main.verifyDuration(durHour, durMinute);

                            if(iDuration != 0) {
                                //setDuration(iDuration);
                                validDuration = true;
                            }
                            else
                                System.out.println("INVALID Duration, Enter numbers greater than 0 for Duration Hour and Minute");
                        }
                        //if starttime and duration overlap with other task dont make changes
                        //else can change
                    boolean set = true; boolean break1 = false;
                    for(Map.Entry mapElement : Schedule.hm.entrySet())
                    {
                        String key = (String) mapElement.getKey();
                        if (Schedule.hm.get(key).getTaskType().equals("Transient") && !key.equals(getName()))
                        {
                            TransientTask check = (TransientTask) Schedule.hm.get(key);
                            if(getDate() == check.getDate() && Main.checkOverlapTime(iStart, iDuration,check.getStartTime(), check.getDuration()))
                            {
                                System.out.println("Task Overlaps with Transient Task, Cannot be added to Schedule");
                                set = false;
                                break1 = true;
                            }
                        }
                        else if(Schedule.hm.get(key).getTaskType().equals("Recurring"))
                        {
                            RecurringTask check1 = (RecurringTask) Schedule.hm.get(key);
                            if(Main.verifyEndDate(check1.getStartDate(), getDate()) && Main.verifyEndDate(getDate(), check1.getEndDate()))
                            {
                                if(Main.checkOverlapTime(iStart,iDuration, check1.getStartTime(), check1.getDuration()))
                                {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("anti_");
                                    sb.append(check1.getName()); sb.append("_");
                                    sb.append(getDate());
                                    //System.out.println(sb.toString());
                                    if(Schedule.hm.containsKey(sb.toString()))
                                    {
                                       // System.out.println("Anti Task Exists, Added Transient to Schedule");
                                        break1 = true;
                                    }
                                    else
                                    {
                                        System.out.println("Task Overlaps with Recurring Task, Cannot be added to Schedule");
                                        set = false;
                                        break1 = true;
                                    }
                                }
                            }
                        }
                        if(break1)
                            break;
                    }
                    if(set)
                    {
                        setStartTime(iStart);
                        setDuration(iDuration);
                    }
                    else
                        System.out.println("Cannot Edit Start Time and Duration, Overlaps with other Task");
                    break;
                case 4:
                    boolean validDate = false; int iDate = 0; boolean setDate = true;
                    while(!validDate) {
                        System.out.println("Enter New Date (YYYYMMDD): ");
                        iDate = keyboard.nextInt();

                        if(Main.verifyDate(iDate))
                        {
                            //setDate(iDate);
                            validDate = true;
                        }
                        else
                            System.out.println("INVALID Date, Enter valid date values");
                    }
                    boolean break2 = false;
                    for(Map.Entry mapElement : Schedule.hm.entrySet())
                    {
                        String key = (String) mapElement.getKey();
                        if (Schedule.hm.get(key).getTaskType().equals("Transient"))
                        {
                            TransientTask check = (TransientTask) Schedule.hm.get(key);
                            if(iDate == check.getDate() && Main.checkOverlapTime(getStartTime(), getDuration(),check.getStartTime(), check.getDuration()))
                            {
                                System.out.println("Task Overlaps with Transient Task, Cannot be added to Schedule");
                                set = false;
                                break2 = true;
                            }
                        }
                        else if(Schedule.hm.get(key).getTaskType().equals("Recurring"))
                        {
                            RecurringTask check1 = (RecurringTask) Schedule.hm.get(key);
                            if(Main.verifyEndDate(check1.getStartDate(), iDate) && Main.verifyEndDate(iDate, check1.getEndDate()))
                            {
                                if(Main.checkOverlapTime(getStartTime(),getDuration(), check1.getStartTime(), check1.getDuration()))
                                {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("anti_");
                                    sb.append(check1.getName()); sb.append("_");
                                    sb.append(getDate());
                                    //System.out.println(sb.toString());
                                    if(Schedule.hm.containsKey(sb.toString()))
                                    {
                                        System.out.println("Anti Task Exists, Added Transient to Schedule");
                                        break2 = true;
                                    }
                                    else
                                    {
                                        System.out.println("Task Overlaps with Recurring Task, Cannot be added to Schedule");
                                        setDate = false;
                                        break2 = true;
                                    }
                                }
                            }
                        }
                        if(break2)
                            break;
                    }

                    if(setDate)
                        setDate(iDate);
                    else
                        System.out.println("Cannot Edit Date, Overlaps with other Task");

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