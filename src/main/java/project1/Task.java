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

    public Task() {}

    public boolean checkDuplicate(String name, float startTime, float duration) {
    	return true;
    }


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

}
