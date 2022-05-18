package com.bci.exercises.dal.exceptions;

public class BusinessException extends Exception{

    private Integer code;
    private String message;
    
    public BusinessException(Integer code,String message){
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
