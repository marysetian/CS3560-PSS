package project1;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Schedule {

    public static Map<String, Task>hm = new HashMap<>();

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

    //hashmap that stores the tasks by the given date and sorts tasks by time
    public static TreeMap<Double, Task> tm = new TreeMap<>();

    public static void writeDaily(String fileName, int givenDate) throws IOException, java.text.ParseException {

        //iterate through hashmap that holds all the tasks
        for (Map.Entry mapElement : Schedule.hm.entrySet()) {
            String key = (String) mapElement.getKey();

            //if task type = transient
            if (Schedule.hm.get(key).getTaskType().equals("Transient")) {
                TransientTask tempTransient = (TransientTask) Schedule.hm.get(key);

                //if the transient task's date == givenDate, put it in the tree map
                if (tempTransient.getDate() == givenDate) {

                    //key: start time, value: transient task object
                    tm.put((double) tempTransient.getStartTime(), tempTransient);
                }
            }

            //if task type = recurring
            if (Schedule.hm.get(key).getTaskType().equals("Recurring")) {
                RecurringTask tempRecurring = (RecurringTask) Schedule.hm.get(key);
                int startDate = tempRecurring.getStartDate();
                int endDate = tempRecurring.getEndDate();
                int frequency = tempRecurring.getFrequency();

                //check to see if the recurring task occurs on the given date
                boolean isIn = verifyRecurringDate(givenDate, startDate, endDate, frequency);

                //if given date is included between start date and end date
                if (isIn) {
                    for (Map.Entry mapElement2 : Schedule.hm.entrySet()) {
                        String key2 = (String) mapElement2.getKey();

                        String recurAntiName = "anti_" + tempRecurring.getName() + "_" + givenDate;

                        //if anti task does not match recurring task, put in tree map
                        if (!hm.containsKey(recurAntiName)) {
                            tm.put((double) tempRecurring.getStartTime(), tempRecurring);

                        } else {
                            System.out.println("Cannot add " + tempRecurring.getName() + " on " + givenDate + " because it has a matching anti task: " + Schedule.hm.get(key2).getName());
                            break;
                        }
                    }
                }
            }
        }
        writeToJSON(fileName, tm);
    }


    //write schedule to json file for a week
    public static void writeWeeklyToFile( String filename, int givenDate) throws ParseException {

        // we will increment the original givenDate by 7 to get an entire week of dates
        int numOfDaysToIncre = 7;
        String endDate;
        int intEndDate;

        //convert date to string
        String strDate = Integer.toString(givenDate);

        //get the range of dates from givenDate + 7 days and store into array
        endDate = incrementDayCalendar(strDate, numOfDaysToIncre);

        //convert string date into an int
        intEndDate = Integer.parseInt(endDate);

        //new treeMap to hold the new tasks that fit within the range
        // treeMap to hold the exported data from hm to organize by time - for WEEKLY
        Map<Double, Task> weeklyTasks = new TreeMap<>();

        for (Map.Entry mapElement : hm.entrySet()) {
            String key = (String) mapElement.getKey();

            //if the taskType is transient
            if (hm.get(key).getTaskType().equals("Transient")){

                //temp transient task
                TransientTask tempTransient = (TransientTask) Schedule.hm.get(key);

                if (givenDate <= tempTransient.getDate() && tempTransient.getDate() <= intEndDate) {
                    String date = String.valueOf(tempTransient.getDate());
                    String time = String.valueOf(tempTransient.getStartTime());

                    Double weeklyKey = Double.parseDouble(date + time);
                    weeklyTasks.put(weeklyKey, tempTransient);
                }

                //if type is recurring
            } else if (hm.get(key).getTaskType().equals("Recurring")) {

                //temp recurring task
                RecurringTask tempRecurring = (RecurringTask) Schedule.hm.get(key);

                ArrayList<Integer> arrList;

                //get the dates of recurring tasks and store into arrayList
                arrList = returnRecurringDates(givenDate, intEndDate, tempRecurring.getStartDate(), tempRecurring.getEndDate(), tempRecurring.getFrequency());

                for(int i = 0; i < arrList.size(); i++) {

                    //task date lies within the given start date and +7 days (range for the week)
                    if (givenDate <= arrList.get(i) && arrList.get(i) <= intEndDate) {

                        String date, time;
                        Double weeklyKey;

                        String recurAntiName = "anti_" + tempRecurring.getName() + "_" + arrList.get(i);

                        if(!hm.containsKey(recurAntiName)) {

                            date = Integer.toString(arrList.get(i));
                            time = String.valueOf(tempRecurring.getStartTime());

                            String tempKey = date + time;

                            weeklyKey = Double.parseDouble(date + time);

                            System.out.println("DATE: " + date + ", Time: " + time);
                            System.out.println("tempKey: " + tempKey);

                            System.out.println("DATE: " + arrList.get(i) + ", KEY: " + weeklyKey);

                            weeklyTasks.put(weeklyKey, tempRecurring);

                        } else  {
                            date = Integer.toString(arrList.get(i));
                            time = String.valueOf(tempRecurring.getStartTime());

                            weeklyKey = Double.parseDouble(date + time);
                            System.out.println("UNABLE TO ADD RECURRING TASK BC OF ANTI TASK: " + tempRecurring.getName() + ", with date: " +
                                                date + ", And KEY: " +  weeklyKey);
                        }
                    }
                }
            }
        }

        writeToJSON(filename, weeklyTasks);
    }

    public static String incrementDayCalendar(String date, int numOfDaysToIncre) throws ParseException {

        String endDate;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, numOfDaysToIncre);
            endDate = sdf.format(c.getTime());

        return endDate;
    }

    // returns a list of the recurring occuring in a given range
    public static ArrayList<Integer> returnRecurringDates(int rangeStart, int rangeEnd, int taskStartDate, int taskEndDate, int frequency) throws ParseException {
        ArrayList<Integer> recurringDays = new ArrayList<Integer>(); // holds all days a recurring task takes place

        int recurringDate = taskStartDate;

        while(recurringDate <= taskEndDate){
            if (rangeStart <= recurringDate && recurringDate<= rangeEnd){
                recurringDays.add(recurringDate);
            }
            String tempDate = String.valueOf(recurringDate);
            tempDate = incrementDayCalendar(tempDate, frequency);
            recurringDate = Integer.parseInt(tempDate);
        }
        return recurringDays;
    }

    // returns true if a recurring task does occur in given range
    public static boolean checkRecurringInRange(int rangeStart, int rangeEnd, int taskStartDate, int taskEndDate, int frequency){

        int recurringDate = taskStartDate;

        // is there at least one recurring date within given range?
        while(recurringDate <= taskEndDate){
            if (rangeStart <= recurringDate && recurringDate <= rangeEnd){
                return true;
            }
        }
        return false;
    }

    public static void writeToJSON(String filename, Map<Double, Task> hm2){

        for(Map.Entry mapElement : hm2.entrySet()) {
            Double key = (Double) mapElement.getKey();

            if (hm2.get(key).getTaskType().equals("Transient")) {

                TransientTask tempTransient = (TransientTask) hm2.get(key);

                writeTransient(filename, tempTransient);

            } else if (hm2.get(key).getTaskType().equals("Recurring")) {

                RecurringTask tempRecurr = (RecurringTask) hm2.get(key);

                writeRecurring(filename, tempRecurr, key);

            }
        }
    }

    public static void writeTransient(String filename, TransientTask ttTask){

            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();

            //Inserting key-value pairs into the json object
            jsonObject.put(ttTask.getName() + ": ", ("Task Type: " + ttTask.getDate() +"Name: " + ttTask.getName() + ", Type: " + ttTask.getType() + ", StartTime: " +
                    ttTask.getStartTime() + ", Duration: " + ttTask.getDuration() + ", Task Type:" +
                    ttTask.getTaskType()));

        try {

            //creates new file if it does not exist, appends if it does
            BufferedWriter out = new BufferedWriter(new FileWriter(filename, true));
            out.write(jsonObject.toJSONString());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created: "+ jsonObject);
    }

    public static void writeRecurring(String filename, RecurringTask recurTask, Double key){

        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();

        //Inserting key-value pairs into the json object
        jsonObject.put(recurTask.getName() + ": ", ( "Task Type: " + recurTask.getTaskType() + ", Name: " + recurTask.getName() + ", Type: " + recurTask.getType() +
                ", StartDate: " + recurTask.getStartDate() + ", StartTime: " + recurTask.getStartTime() + ", Duration: " + recurTask.getDuration() +
                recurTask.getEndDate() + ", " + recurTask.getFrequency() + ", KEY: " + key ));

        try {

            //creates new file if it does not exist, appends if it does
            BufferedWriter out = new BufferedWriter(new FileWriter(filename, true));
            out.write(jsonObject.toJSONString());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created: "+ jsonObject);
    }


    public static void readSchedule() throws IOException {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter filename to read schedule from: ");
        String filename = "C:\\Users\\thaol\\Desktop\\CS3560 OOP\\CS3560_PSS\\src\\" + kb.nextLine() + ".json";

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray taskList = (JSONArray) obj;
            System.out.println(taskList);

            //Iterate over employee array
            taskList.forEach( task -> parseTaskObject( (JSONObject) task ) );

        } catch (FileNotFoundException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }


    }

    private static void parseTaskObject(JSONObject task)
    {
        //Get task object within list
        String type = (String)task.get("Type");
        System.out.println(type);

//Transient
        if (type.equals("Shopping") || type.equals("Appointment") || type.equals("Visit") ){
            String name = (String)task.get("Name");

            long date = (long)task.get("Date");
            long startTime = (long)task.get("StartTime");
            double duration = (double)task.get("Duration");

            // validate name of task
            boolean validName = false;
            if (!Schedule.hm.containsKey(name)) {
                validName = true;
            }

            //validate taskType
            boolean validTaskType = false;
            validTaskType = true;

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
            float durationMin = (float) (duration - (int) Math.floor(duration));
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
            if(Main.verifyDate((int) date))
            {
                validDate = true;
            }

            if(validName && validDate && validDuration && validTaskType && validStartTime){
                TransientTask tempTrans = new TransientTask(name, type, formattedStartTime, formattedDuration, (int) date, "Transient");
                Schedule.hm.put(tempTrans.getName(), tempTrans);
                tempTrans.view();
            }
        }

        //Recurring
        else if (type.equals("Class") || type.equals("Study") || type.equals("Sleep") ||
                type.equals("Exercise") || type.equals("Work") || type.equals("Meal")){

            String name = (String)task.get("Name");
            long startDate = (long)task.get("StartDate");
            long endDate = (long)task.get("EndDate");
            long startTime = (long)task.get("StartTime");
            Double duration = (Double)task.get("Duration");
            long frequency = (long)task.get("Frequency");

            RecurringTask recurrTask = new RecurringTask();

            recurrTask.createFromFile(name, type, startTime, duration, startDate, endDate, frequency);

            hm.put(recurrTask.getName(), recurrTask);
            System.out.println("Recurring Task " + recurrTask.getName() + " added to Schedule");
            recurrTask.view();

        }
    }

    public static boolean verifyRecurringDate(int givenDate, int startDate, int endDate, int frequency) throws ParseException, java.text.ParseException {
        ArrayList<Integer> recurringDays = new ArrayList<Integer>();
        int recurringDate = startDate;
        recurringDays.add(Integer.valueOf(recurringDate));
        while (recurringDate < endDate) {
            if (frequency == 1) {
                String tempDate = String.valueOf(recurringDate);
                tempDate = incrementDayCalendar(tempDate, 1);
                recurringDate = Integer.parseInt(tempDate);
                recurringDays.add(Integer.valueOf(recurringDate));
            } else if (frequency == 7) {
                String tempDate = String.valueOf(recurringDate);
                tempDate = incrementDayCalendar(tempDate, 7);
                recurringDate = Integer.parseInt(tempDate);
                recurringDays.add(Integer.valueOf(recurringDate));
            }
        }

        for (int i = 0; i < recurringDays.size(); i++) {
            if (givenDate == recurringDays.get(i)) {
                return true;
            }
        }
        return false;
    }
}
