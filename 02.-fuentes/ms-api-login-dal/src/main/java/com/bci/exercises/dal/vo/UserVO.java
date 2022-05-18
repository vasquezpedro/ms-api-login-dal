package com.bci.exercises.dal.vo;

import java.util.List;


public class UserVO {

    private String name;
    private String email;
    private String password;
    private List<PhonesVO> phones;

    

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhonesVO> getPhones() {
        return this.phones;
    }

    public void setPhones(List<PhonesVO> phones) {
        this.phones = phones;
    }

}
