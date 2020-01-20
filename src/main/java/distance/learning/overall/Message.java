package distance.learning.overall;

import java.io.Serializable;

/**
 * Created by maxim_anatolevich on 22.05.16.
 */
public class Message implements Serializable{
    public static int NAME = 0;
    public static int PASSWORD = 1;
    public static int MESSAGE = 2;
    public static int REPLY = 3;
    public static int COMMAND = 4;
    private int type;
    private String data;
    private boolean success;

    public Message(int type, String data, boolean success){
        this.type = type;
        this.data = data;
        this.success = success;
    }

    public void setType(int type){
        this.type = type;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public int getType(){
        return type;
    }

    public String getData(){
        return data;
    }

    public boolean getSuccess(){
        return success;
    }
}
