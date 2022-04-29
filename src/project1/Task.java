package project1;

public class Task {

    protected String name;
    protected String type;
    protected float startTime;
    protected float duration;


//    // TODO: does Task need a constructor ?
    public Task(String name, String type, float startTime, float duration){
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
    }

    public boolean checkDuplicate(String name, float startTime, float duration) {
    	return true;
    }




}
