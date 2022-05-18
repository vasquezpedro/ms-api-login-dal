package com.bci.exercises.dal.vo;

import java.util.Calendar;
import java.util.Date;

public class ErrorVO {

    private Date timestamp;
    private int codigo;
    private String detail;

    public ErrorVO(int codigo,String detail){
        this.timestamp=Calendar.getInstance().getTime();
        this.codigo=codigo;
        this.detail=detail;
    }
    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
