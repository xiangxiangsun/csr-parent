package csr.security.entity;

import java.io.Serializable;

public class Result implements Serializable {
    private boolean flag;
    private String message;
    private Object data;

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public static Result success(String message,Object data){
        Result result = new Result(true,message,data);
        return result;
    }

    public static Result success(String message){
        Result result = new Result(true,message);
        return result;
    }

    public static Result error(String message){
        Result result = new Result(false,message);
        return result;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
