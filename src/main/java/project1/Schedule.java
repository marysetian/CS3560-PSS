package project1;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Schedule {

    /**
     * Storing tasks into HashMap
     */
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


    /**
     * Write to JSON file for Transient Task
     * @param filenameDate name of file, which will be based on the date entered
     */
    public static void writeTransient(String filename, Task transientTask){

        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();

        //Inserting key-value pairs into the json object via hashMap 
        jsonObject.put(transientTask.getName(), transientTask);

        String fileLoc = "C:\\Users\\thaol\\Desktop\\CS3560 OOP\\CS3560_PSS\\src\\" + filename + ".json";

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


    public static void testPrint(){

        System.out.println("THIS IS A TEST OF THE PRINT METHOD FOR THE HASMAP: ");

        for (Map.Entry<String, Task> taskEntry: hm.entrySet()){

            Task tempTask =  taskEntry.getValue();

            System.out.println(taskEntry.getKey()+ " = "+ tempTask.getName());
        }
    }


}
