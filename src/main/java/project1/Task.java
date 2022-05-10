package project1;

public class Task {

    protected String name;
    protected String type;
    protected float startTime;
    protected float duration;
    private String taskType;


    //    // TODO: does Task need a constructor ?
    public Task(String name, String type, float startTime, float duration, String taskType){
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
        this.taskType = taskType;
    }

    public Task() {}

    public boolean checkDuplicate(String name, float startTime, float duration) {
        return true;
    }

    public void view(){}

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setStartTime(float startTime)
    {
        this.startTime = startTime;
    }

    public void setDuration(float duration)
    {
        this.duration = duration;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public float getStartTime()
    {
        return startTime;
    }

    public float getDuration()
    {
        return duration;
    }

    public void setTaskType(String taskType){this.taskType = taskType;};
    public String getTaskType() {return taskType;};

}
