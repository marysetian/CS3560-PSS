package project1;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.beans.Transient;
import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;

public class Schedule {

    // Map to hold all Tasks
    public static Map<String, Task> hm = new HashMap<>();


    public static void readSchedule() throws IOException {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter filename to read schedule from: ");
        String filename = "/Users/alondrasanchez/IdeaProjects/cs3560_pss/src/test/" + kb.nextLine() + ".json";

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

            if(validName && validDate && validDuration && validStartTime){
                TransientTask tempTrans = new TransientTask(name, type, formattedStartTime, formattedDuration, (int) date, "Transient");
                Schedule.hm.put(tempTrans.getName(), tempTrans);
                tempTrans.view();
            }
        }
        //Recurring
        else if (type.equals("Class") || type.equals("Study") || type.equals("Sleep") ||
            type.equals("Exercise") || type.equals("Work") || type.equals("Meal")){
        }
        else  // anti task
        {

        }
    }


    // ================================= MAIN WRITE METHODS =================================
    // todo : add write daily
    // todo : add write weekly
    public static void writeMonthlySchedule(int year, int month, String filename) throws IOException, ParseException {
        // get the beginning date of the month range (YYYYMMDD)
        String tempStartDay = "01";
        String tempStartYear = Integer.toString(year);
        String tempStartMonth;
        // update month format
        if (month < 10){
            tempStartMonth = "0" + Integer.toString(month);
        }
        else {
            tempStartMonth = Integer.toString(month);
        }
        // fixed format of the start date range for the month
        int startDate = Integer.parseInt(tempStartYear + tempStartMonth + tempStartDay);

        // get the end date for the month (end of range : (YYYYMMDD)
        YearMonth yearMonthObj = YearMonth.of(year, month);
        int daysInMonth = yearMonthObj.lengthOfMonth();
        String tempdaysInMonth = Integer.toString(daysInMonth);
        int endDate = Integer.parseInt(tempStartYear + tempStartMonth + tempdaysInMonth);


        // iterate hashmap hm, collect all tasks in month
        Map<Float, Task> monthlyTasks = new TreeMap<>();


        for(Map.Entry mapElement : Schedule.hm.entrySet())
        {
            String key = (String) mapElement.getKey();

            // is element a transient task
            if (Schedule.hm.get(key).getTaskType().equals("Transient")) {
                TransientTask tempTransient = (TransientTask) Schedule.hm.get(key);
                if(startDate <= tempTransient.getDate() && tempTransient.getDate() <= endDate){
                    String date = String.valueOf(tempTransient.getDate());
                    String time = String.valueOf(tempTransient.getStartTime());;
                    Float monthlyKey = Float.parseFloat(date + time);
                    monthlyTasks.put(monthlyKey, tempTransient);
                }
            }

            // is element a recurring task
            if (Schedule.hm.get(key).getTaskType().equals("Recurring")) {
                RecurringTask tempRecurring = (RecurringTask) Schedule.hm.get(key);
                ArrayList<Integer> recurringDatesOfTask = returnRecurringDates(startDate, endDate, tempRecurring.getStartDate(), tempRecurring.getEndDate(), tempRecurring.getFrequency());

                for(int i = 0; i <= recurringDatesOfTask.size(); i++) {

                    //task date lies within the given start date and days in month (range for the week)
                    if (startDate <= recurringDatesOfTask.get(i) && recurringDatesOfTask.get(i) <= endDate) {

                        String date, time;
                        Float monthlyKey;

                        String recurAntiName = "anti_" + tempRecurring.getName() + "_" + recurringDatesOfTask.get(i);

                        if(!hm.containsKey(recurAntiName)) {

                            date = Integer.toString(recurringDatesOfTask.get(i));
                            time = String.valueOf(tempRecurring.getStartTime());

                            monthlyKey = Float.parseFloat(date + time);
                            monthlyTasks.put(monthlyKey, tempRecurring);
                        }
                    }
                }
            }
        }
        writeToJSON(filename, monthlyTasks);
    }

    // ================================= HELPER WRITE METHODS =================================

    /*
     * writes monthly tasks to a json file
     * @param filename  the name of the file to write to
     *
     * */
    public static void writeToJSON(String filename, Map<Float, Task> hm2){

        for(Map.Entry mapElement : hm2.entrySet()) {
            Float key = (Float) mapElement.getKey();

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

    public static void writeRecurring(String filename, RecurringTask recurTask, float key){

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

    // ================================= HELPER METHODS =================================

    // returns a list of the recurring occurring in a given range
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

    // increment a date by frequency
    public static String incrementDayCalendar(String date, int numOfDaysToIncre) throws ParseException {

        String endDate;

        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, numOfDaysToIncre);
        endDate = sdf.format(c.getTime());

        return endDate;
    }

}
