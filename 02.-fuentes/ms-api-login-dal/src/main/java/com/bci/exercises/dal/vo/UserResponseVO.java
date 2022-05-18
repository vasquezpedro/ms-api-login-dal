package com.bci.exercises.dal.vo;

import com.bci.exercises.dal.entity.PhoneEntity;
import com.bci.exercises.dal.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserResponseVO {

    private UUID id;
    private Date created;
    private Date lastLogin;
    private String token;
    private Boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<PhonesVO> phones;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhonesVO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhonesVO> phones) {
        this.phones = phones;
    }

    public void createdVO(UserEntity userEntity) {
        this.id=userEntity.getId();
        this.created=userEntity.getCreated();
        this.lastLogin=userEntity.getLastLogin();
        this.token=userEntity.getToken();
        this.isActive=userEntity.getIsActive();
        this.name=userEntity.getName();
        this.email=userEntity.getEmail();
        this.password=userEntity.getPassword();
        if(null!=userEntity.getPhones()){
            List<PhonesVO> list= new ArrayList<>();
            userEntity.getPhones().forEach((phone)->{
                list.add(PhonesVO.createVO(phone));
            });
            this.phones=list;
        }

    }
}
