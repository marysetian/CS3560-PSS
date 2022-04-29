package project1;

public class RecurringTask extends Task {
    // todo : double check if these can be inherited from Task
//    private String name;
//    private String type;
//    private float startTime;
//    private float duration;

    private int startDate;  //YYYYMMDD
    private int endDate;    //YYYYMMDD
    private int frequency;  // 1 - daily , 7 weekly
    private boolean available;  // true = anti-task exists for this task
    private String[] validTypes = {"Class", "Study", "Sleep", "Exercise", "Work", "Meal"};


    /**
    * constructor for a Recurring Task
    * @param name   name of Recurring task - must be unique
    * @param type   must be a value from type array
    * @param startTime  time task begins
    * @param duration   duration of task
    * @param startDate  Date task begins
    * @param endDate    Date task ends
    * @param frequency  how often task occurs
    * */
    public RecurringTask(String name, String type, float startTime, float duration, int startDate, int endDate, int frequency){
        //Todo : implement constructor
        super(name,type,startTime,duration);
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
    }

    /**
    * print all attributes of Task
    * */
    public void view(){}

    /**
    * deletes task and corresponding anti-task
    * */
    public void delete(){}

    /**
    * displays a menu for a user to edit any task attributes except "available"
    * */
    public void edit(){}

    // todo : create getters and setters
}
