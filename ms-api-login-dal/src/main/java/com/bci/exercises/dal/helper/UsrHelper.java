package com.bci.exercises.dal.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bci.exercises.dal.exceptions.BusinessException;

import com.bci.exercises.dal.util.Constantes;
import org.springframework.stereotype.Component;



@Component
public class UsrHelper {


    public void validaExpresion(String inputStr, String regex, String message,Integer code) throws BusinessException {

        if(null==inputStr || !Pattern.matches(regex, inputStr)){
            throw new BusinessException(code,message);
        }

    }

    public void validaExpresion(String inputStr, String regex, String message,Integer code,String regex2) throws BusinessException {
            isNull(inputStr,message,code);
            int count =inputStr.replaceAll(regex2,Constantes.sEMPTY).length();

        if( !Pattern.matches(regex, inputStr) || count>2|| count<1){
            throw new BusinessException(code,message);
        }

    }

    public void nullOrEmpty(String inputStr, String message,Integer code) throws BusinessException {

        if(null==inputStr || inputStr.isEmpty()){
            throw new BusinessException(code,message);
        }

    }

    public void isNull(String inputStr, String message,Integer code) throws BusinessException {

        if(null==inputStr){
            throw new BusinessException(code,message);
        }

    }
    




}
