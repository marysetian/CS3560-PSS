package project1;


public class testingTasks {
    public static void main(String[] args) {

        createTransientTask();

        createAntiTask();
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
