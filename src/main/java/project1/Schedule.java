package project1;
import java.util.HashMap;
import java.util.Map;

public class Schedule {

    public static Map<String, Task>hm = new HashMap<>();


    /*
    Recurring Task Map keeps Track if a Recurring Task exists when an Anti Task is being created
     */
    public static Map<String, RecurringTask> recurringTaskMap = new HashMap<>();

    /*
    Map will link an Anti Task to its RecurringTask
     */
    public static Map<AntiTask, RecurringTask> antiRecurringMap = new HashMap<>();




    /**
     * prints a user's schedule to a file
     * @param filename  file to be written to
     */
    public void printToFile(String filename){};

    /**
    * reads a schedule from a user provided file
    * @param filename  name of file to be reae*/
    public void readFromFile(String filename){};

    /**
     * verify file category is valid when reading from a file
     * */


}
