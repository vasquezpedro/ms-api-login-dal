package com.bci.exercises.dal.repository;

import java.util.List;

import com.bci.exercises.dal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query("select c from UserEntity c  where c.name = :name and c.password= :password")
    UserEntity findByNameAndPass(@Param("name") String name, @Param("password") String password);
    
    
    @Query("select c from UserEntity c  where UPPER(c.name) = UPPER(:name) or UPPER(c.email)= UPPER(:email)")
    List<UserEntity> findByNameAndMail(@Param("name") String name, @Param("email") String email);



}
