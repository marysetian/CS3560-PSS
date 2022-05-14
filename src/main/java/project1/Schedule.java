package project1;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        Map<Float, Task> weeklyTasks = new TreeMap<>();

        for (Map.Entry mapElement : hm.entrySet()) {
            String key = (String) mapElement.getKey();

            //if the taskType is transient
            if (hm.get(key).getTaskType().equals("Transient")){

                //temp transient task
                TransientTask tempTransient = (TransientTask) Schedule.hm.get(key);

                if (givenDate <= tempTransient.getDate() && tempTransient.getDate() <= intEndDate) {
                    String date = String.valueOf(tempTransient.getDate());
                    String time = String.valueOf(tempTransient.getStartTime());

                    Float weeklyKey = Float.parseFloat(date + time);
                    weeklyTasks.put(weeklyKey, tempTransient);
                }

                //if type is recurring
            } else if (hm.get(key).getTaskType().equals("Recurring")) {

                //temp recurring task
                RecurringTask tempRecurring = (RecurringTask) Schedule.hm.get(key);

                //task date lies within the given start date and +7 days (range for the week)
                if (givenDate <= tempRecurring.getStartDate() && tempRecurring.getStartDate() <= intEndDate) {

                        String date = String.valueOf(tempRecurring.getStartDate());
                        String time = String.valueOf(tempRecurring.getStartTime());

                        Float weeklyKey = Float.parseFloat(date + time);
                        weeklyTasks.put(weeklyKey, tempRecurring);
                }
            }
        }

        writeToJSON(filename, weeklyTasks);

        System.out.println("Info added to file: " + filename + "\n");

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

    public static ArrayList<Integer> returnRecurringDates(int taskStartDate, int taskEndDate, int frequency) throws ParseException {
        ArrayList<Integer> recurringDays = new ArrayList<Integer>(); // holds all days a recurring task takes place

        int recurringDate = taskStartDate;

        while(recurringDate <= taskEndDate){
            if (taskStartDate <= recurringDate && recurringDate <= taskEndDate){
                recurringDays.add(recurringDate);
            }
            if (frequency == 1){
                String tempDate = String.valueOf(recurringDate);
                tempDate = incrementDayCalendar(tempDate, 1);
                recurringDate = Integer.parseInt(tempDate);
            }
            if (frequency == 7){
                String tempDate = String.valueOf(recurringDate);
                tempDate = incrementDayCalendar(tempDate, 7);
                recurringDate = Integer.parseInt(tempDate);
            }
        }
        return recurringDays;
    }

    public static void writeToJSON(String filename, Map<Float, Task> hm2) throws ParseException {

        for(Map.Entry mapElement : hm2.entrySet()) {
            Float key = (Float) mapElement.getKey();

            if (hm2.get(key).getTaskType().equals("Transient")) {

                TransientTask tempTransient = (TransientTask) hm2.get(key);

                writeTransient(filename, tempTransient);

            } else if(hm2.get(key).getTaskType().equals("Recurring")){

                RecurringTask tempRecurr = (RecurringTask) hm2.get(key);

                int arrSize = 7;
                ArrayList<Integer> arrList  = new ArrayList<Integer>(arrSize);

                //get the dates of recurring tasks and store into arrayList
                arrList = returnRecurringDates(tempRecurr.getStartDate(), tempRecurr.getEndDate(), tempRecurr.getFrequency());

                writeRecurring(filename, tempRecurr, arrList);

            }
            else if(hm2.get(key).getTaskType().equals("AntiTask")){

            }
        }
    }


    public static void writeTransient(String filename, TransientTask ttTask){

            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();

            //Inserting key-value pairs into the json object
            jsonObject.put(ttTask.getName() + ": ", (ttTask.getName() + ", " + ttTask.getType() + ", " +
                    ttTask.getStartTime() + ", " + ttTask.getDuration() + ", " +
                    ttTask.getTaskType() + ", " + ttTask.getDate()));

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

//
//            try {
//                FileWriter file = new FileWriter( filename + ".json");
//                file.write(jsonObject.toJSONString());
//                file.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            System.out.println("JSON file created: " + jsonObject);
//    }


    public static void writeRecurring(String filename, RecurringTask recurTask, ArrayList arrList){

        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();

        String allDates = "";

        for (int i = 0; i <= 7; i ++) {

            allDates += arrList.get(i) + ", ";
        }


        //Inserting key-value pairs into the json object
        jsonObject.put(recurTask.getName() + ": ", (recurTask.getName() + ", " + recurTask.getType() + ", " +
                recurTask.getStartTime() + ", " + recurTask.getDuration() + ", " +
                recurTask.getStartDate() + ", " + recurTask.getEndDate() + ", " + recurTask.getFrequency() + " RECURRING DATES: " + allDates));


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

    public static void writeAnti(String filename, AntiTask antiTask){

    }

    public static void deleteFile(String filename){

        String fileLoc = "C:\\Users\\thaol\\Desktop\\CS3560 OOP\\CS3560_PSS\\src\\" + filename + ".json";

        File file = new File(fileLoc);

        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }
    }

}
