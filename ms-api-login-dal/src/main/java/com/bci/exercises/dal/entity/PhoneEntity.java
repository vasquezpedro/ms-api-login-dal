package com.bci.exercises.dal.entity;


import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bci.exercises.dal.vo.PhonesVO;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "dom_phones", schema = "PUBLIC")
public class PhoneEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PHONE_ID_GENERATOR" )
    @SequenceGenerator(name = "PHONE_ID_GENERATOR", sequenceName = "PUBLIC.seq_id_phone", allocationSize = 1, initialValue = 1)
    private Long id;
    
    @Column(name="number")
    private Long number;

    @Column(name="city_code")
    private int cityCode;

    @Column(name="contry_code",length = 4)
    private String contryCode;
    
    @Column(name="id_user")
    @Type(type = "uuid-char")
    private UUID idUser;

    public PhoneEntity(){
        super();
    }

    public PhoneEntity(Long id,UUID idUser,Long number,int cityCode,String contryCode){
        this.id=id;
        this.idUser=idUser;
        this.number=number;
        this.cityCode=cityCode;
        this.contryCode=contryCode;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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



    public UUID getIdUser() {
        return this.idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }
    

    public String getContryCode() {
        return this.contryCode;
    }

    public void setContryCode(String contryCode) {
        this.contryCode = contryCode;
    }

    public void toEntity(PhonesVO phone,UUID idUserUUID) {
        this.idUser=idUserUUID;
        this.number=phone.getNumber();
        this.cityCode=phone.getCityCode();
        this.contryCode=phone.getContryCode();
    }
}
