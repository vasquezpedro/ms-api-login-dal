package com.bci.exercises.dal.exceptions;

public class AutenticationException extends Exception{

    private String message;
    private Integer code;

    public AutenticationException(String message, Integer code){
        super();
        this.message=message;
        this.code=code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

   
}
