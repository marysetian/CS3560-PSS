package project1;


import javax.swing.plaf.synth.SynthCheckBoxUI;
import java.util.Scanner;

public class testingTasks {
    public static void main(String[] args) {

        //System.out.println(Main.verifyDate(20220701));

        //createRecurringTask();

        createTransientTask();
        //createTransientTask();

        //createAntiTask();

    }

    private static void createRecurringTask()
    {
        RecurringTask rec = new RecurringTask("cs356", "Class",17,1,20220505,20220522,1);
        //rec.create();
        rec.view();

        Schedule.hm.put(rec.getName(), rec);
        Schedule.recurringTaskMap.put(rec.getName(), rec);


    }

    private static void createAntiTask()
    {
        AntiTask test1 = new AntiTask();
        test1.create();
        test1.view();
        Schedule.hm.put(test1.getName(), test1);



    }

    private static void createTransientTask()
    {
        TransientTask create = new TransientTask();
        create.create();
        Schedule.hm.put(create.getName(), create);
        create.view();
        Main.viewSchedule();
        //create.edit();


    }

}
