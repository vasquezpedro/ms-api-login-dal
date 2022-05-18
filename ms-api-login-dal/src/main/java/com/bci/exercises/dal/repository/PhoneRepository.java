package com.bci.exercises.dal.repository;

import com.bci.exercises.dal.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity,Long> { 
    
}
