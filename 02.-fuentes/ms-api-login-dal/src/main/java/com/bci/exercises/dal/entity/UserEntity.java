package com.bci.exercises.dal.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "dom_usuario" , schema = "PUBLIC")
public class UserEntity {
    
    public UserEntity(){
       super(); 
    }

    public UserEntity(String email,String name,String password,Boolean isActive){
        this.email=email;
        this.name=name;
        this.password=password;
        this.isActive=isActive;
    }
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
    private UUID id;
    
    @Column(name="created" ,nullable = false, updatable = false)
    @CreationTimestamp
    private Date created;

    @Column(name="last_login")
    private Date lastLogin;
    
    private String token;
    
    @Column(name="active")
    private Boolean isActive;
    
    @Column(name="name",length = 100)
    private String name;
    
    @Column(name="email")
    private String email;
    
    @Column(name="password")
    private String password;

    @OneToMany(mappedBy="idUser")
    private List<PhoneEntity> phones;

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

    public List<PhoneEntity> getPhones() {
        return this.phones;
    }

    public void setPhones(List<PhoneEntity> phones) {
        this.phones = phones;
    }


   
}
