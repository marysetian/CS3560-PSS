package project1;

public class AntiTask extends Task{

    // todo : double check if these can be inherited from Task
//    private String name;
//    private String type;
//    private float startTime;
//    private float duration;

    private int date;    // YYYYMMDD
    private final String type = "Cancellation";


    /**
     * constructor for a AntiTask
     * @param name   name of Recurring task - must be unique
     * @param type   must be a value from type array
     * @param startTime  time task begins
     * @param duration   duration of task
     * @param date    used with startTime to cancel a Recurring task
     * */
    public AntiTask(String name, String type, float startTime, float duration, int date){
        // todo : implement constructor
        super(name,type,startTime,duration);
        this.date = date;
    };



    /**
     * print all attributes of AntiTask
     * */
    public void view(){}

    /**
     * delete anti-task iff there is no transient task overlapping the recurring task corresponding to this antitask
     * */
    public void delete(){}

    /**
     * displays a menu for a user to edit any task attribute
     * */
    public void edit(){}

    // todo : create getters and setters
    // todo : do we want to asd cancelRecurring??
}
