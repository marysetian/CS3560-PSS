package project1;

import java.util.Iterator;
import java.util.Map;
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
                sb.append("anti_");  //"anti_name_date"
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

        RecurringTask getInfo = (RecurringTask) Schedule.hm.get(inputName);
        float aStart = getInfo.getStartTime();
        float aDuration = getInfo.getDuration();
        setStartTime(aStart);
        setDuration(aDuration);


        int recurStart = getInfo.getStartDate();
        int recurEnd = getInfo.getEndDate();
        //date input and verification  yyyy mm dd
        boolean validDate = false;
        while(!validDate) {
            System.out.println("Enter Date (YYYYMMDD): ");
            int iDate = keyboard.nextInt();

            //verify if anti task is within start and enddate
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
        Boolean canDelete = true; boolean break1 = false;
        for(Map.Entry mapElement : Schedule.hm.entrySet())
        {
            String key = (String)mapElement.getKey();
            if(Schedule.hm.get(key).getTaskType().equals("Transient"))
            {
                TransientTask check = (TransientTask) Schedule.hm.get(key);
                if(getDate() == check.getDate() && Main.checkOverlapTime(getStartTime(),getDuration(), check.getStartTime(), check.getDuration()))
                {
                    System.out.println("Anti Task overlaps with a Transient Task, Cannot be deleled.");
                    canDelete = false;
                    break1 = true;
                }
            }
            else if(Schedule.hm.get(key).getTaskType().equals("Recurring"))
            {
                RecurringTask check1 = (RecurringTask) Schedule.hm.get(key);
                String nn = getName();
                String ss = nn.substring(5, nn.length() - 9);
                if(Main.checkOverlapTime(getStartTime(),getDuration(), check1.getStartTime(), check1.getDuration()))
                {
                    //verify anti task date is within recurring
                    if(Main.verifyEndDate(check1.getStartDate(), getDate()) && Main.verifyEndDate(getDate(), check1.getEndDate()))
                    {
                        if(Main.checkOverlapDate(check1.getStartDate(), check1.getEndDate(), getDate(), check1.getFrequency()) && !ss.equals(check1.getName()))
                        {
                            System.out.println("Anti Task overlaps with a Recurring Task, Cannot be deleted.");
                            canDelete = false;
                            break1 = true;
                        }

                    }
                }
            }
            if(break1)
                break;

        }

        if(canDelete) {
            Schedule.hm.remove(getName());
            System.out.println("Anti-Task Deleted");
        }
    }

    /**
     * displays a menu for a user to edit any task attribute
     * */
    public void edit() {

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
