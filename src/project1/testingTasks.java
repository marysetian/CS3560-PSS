package project1;


import java.util.Scanner;

public class testingTasks {
    public static void main(String[] args) {

        //createTransientTask();
        int date = 20220510;
        System.out.println(Main.verifyDate(date));
        //createAntiTask();
    }

    private static void createAntiTask()
    {

        AntiTask prac = new AntiTask();
        prac.create();
        prac.view();
        prac.edit();
    }

    private static void createTransientTask()
    {
        TransientTask create = new TransientTask();
        create.create();
        create.view();
        create.edit();
    }


}
