package com.bci.exercises.dal.vo;

import com.bci.exercises.dal.entity.PhoneEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhonesVO {

    private Long number;
    
    @JsonProperty("citycode")
    private int cityCode;
    
    @JsonProperty("contrycode")
    private String contryCode;

    public PhonesVO(){
        super();
    }

    public PhonesVO(Long number,int cityCode,String contryCode){
        this.number=number;
        this.cityCode=cityCode;
        this.contryCode=contryCode;
    }

    public static PhonesVO createVO(PhoneEntity phone) {
        return new PhonesVO(phone.getNumber(),phone.getCityCode(),phone.getContryCode());
    }


    public Long getNumber() {
        return this.number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public int getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getContryCode() {
        return this.contryCode;
    }

    public void getContryCode(String contryCode) {
        this.contryCode = contryCode;
    }


}
