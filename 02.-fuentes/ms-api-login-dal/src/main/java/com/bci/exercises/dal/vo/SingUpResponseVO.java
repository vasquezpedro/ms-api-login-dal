package com.bci.exercises.dal.vo;

import java.util.Date;
import java.util.UUID;

public class SingUpResponseVO {

    private UUID id;
    private Date created;
    private Date lastLogin;
    private String token;
    private Boolean isActive;

    public SingUpResponseVO(UUID id,Date created,Date lastLogin,String token,Boolean isActive){
        this.id=id;
        this.created=created;
        this.lastLogin=lastLogin;
        this.token=token;
        this.isActive=isActive;
    }
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


}
