package project1;

import java.util.Scanner;

public class TransientTask extends Task {
    // todo : double check if these can be inherited from Task
    // private String name;
    // private String type;
    // private float startTime;
    // private float duration;

    private int date;    // YYYYMMDD
    private String[] validTypes = {"Visit", "Shopping", "Appointment"};

    /**
     * constructor for a TransientTask
     * @param name   name of Transient task - must be unique
     * @param type   must be a value from type array
     * @param startTime  time task begins
     * @param duration   duration of task
     * @param date    used with startTime to cancel a Recurring task
     * */
    public TransientTask(String name, String type, float startTime, float duration, int date){
        // todo : implement constructor
        super(name,type,startTime,duration);
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

        System.out.println("Enter Task Name: ");
        String iName = keyboard.nextLine().trim();
        setName(iName);

        boolean setValid = true;
        while(setValid) {
            System.out.println("Select Type: ");
            System.out.println("1. Visit \n2. Shopping \n3. Appointment");
            int iType = Integer.parseInt(keyboard.nextLine());
            if (iType == 1){
                setType(validTypes[0]);
                setValid = false;}
            if (iType == 2){
                setType(validTypes[1]);
                setValid = false;}
            if (iType == 3){
                setType(validTypes[2]);
                setValid = false;}
        }

        System.out.println("Enter Start Time: ");
        float iStart = Float.parseFloat(keyboard.nextLine());
        setStartTime(iStart);

        System.out.println("Enter Duration: ");
        float iDuration = Float.parseFloat(keyboard.nextLine());
        setDuration(iDuration);

        System.out.println("Enter Date (YYYYMMDD): ");
        int iDate = Integer.parseInt(keyboard.nextLine());
        setDate(iDate);

    }


    /**
     * print all attributes of TransientTask
     * */
    public void view(){
        System.out.println("Name: " + getName() +"\nType: " + getType() + "\nStart Time: " + getStartTime() + "\nDuration: " + getDuration() + "\nDate: " + getDate());
    }

    /**
     * delete anti-task iff there is no transient task overlapping the recurring task corresponding to this antitask
     * */
    public void delete(){}

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
                    System.out.println("Enter New Task Name: ");
                    String iName = keyboard.nextLine().trim();
                    setName(iName);
                    break;
                case 2:
                    System.out.println("Select New Type: ");
                    System.out.println("1. Visit \n2. Shopping \n3. Appointment");
                    int iType = Integer.parseInt(keyboard.nextLine());
                    if (iType == 1)
                        setType(validTypes[0]);
                    else if (iType == 2)
                        setType(validTypes[1]);
                    else if (iType == 3)
                        setType(validTypes[2]);
                    break;
                case 3:
                    System.out.println("Enter New Start Time: ");
                    float iStart = Float.parseFloat(keyboard.nextLine());
                    setStartTime(iStart);
                    break;
                case 4:
                    System.out.println("Enter New Duration: ");
                    float iDuration = Float.parseFloat(keyboard.nextLine());
                    setDuration(iDuration);
                    break;
                case 5:
                    System.out.println("Enter New Date (YYYYMMDD): ");
                    int iDate = Integer.parseInt(keyboard.nextLine());
                    setDate(iDate);
                    break;
            }
        }
    }

    // todo : create getters and setters
    public void setDate(int date)
    {
        this.date = date;
    }

    public int getDate()
    {
        return date;
    }


}






// public class TransientTask extends Task {
//     // todo : double check if these can be inherited from Task
//     // private String name;
//     // private String type;
//     // private float startTime;
//     // private float duration;

//     private int date;    // YYYYMMDD
//     private String[] type = {"Visit", "Shopping", "Appointment"};

//     /**
//      * constructor for a TransientTask
//      * @param name   name of Transient task - must be unique
//      * @param type   must be a value from type array
//      * @param startTime  time task begins
//      * @param duration   duration of task
//      * @param date    used with startTime to cancel a Recurring task
//      * */
//     public TransientTask(String name, String type, float startTime, float duration, int date){
//         // todo : implement constructor
//     };

//     /**
//      * print all attributes of TransientTask
//      * */
//     public void view(){}

//     /**
//      * delete anti-task iff there is no transient task overlapping the recurring task corresponding to this antitask
//      * */
//     public void delete(){}

//     /**
     //* displays a menu for a user to edit any task attribute
    // * */
    //public void edit(){}

    // todo : create getters and setters

