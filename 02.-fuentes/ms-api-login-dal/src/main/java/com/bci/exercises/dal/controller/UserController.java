package com.bci.exercises.dal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.bci.exercises.dal.entity.PhoneEntity;
import com.bci.exercises.dal.entity.UserEntity;
import com.bci.exercises.dal.exceptions.AutenticationException;
import com.bci.exercises.dal.exceptions.BusinessException;
import com.bci.exercises.dal.helper.UsrHelper;
import com.bci.exercises.dal.repository.PhoneRepository;
import com.bci.exercises.dal.repository.UserRepository;
import com.bci.exercises.dal.util.Constantes;
import com.bci.exercises.dal.util.EncriptaAESUtil;
import com.bci.exercises.dal.util.JwtTokenUtil;
import com.bci.exercises.dal.vo.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*  */
@RestController
@RequestMapping("/auth")
public class UserController {
    

    @Autowired
    UsrHelper usrHelper;

    @Autowired
    EncriptaAESUtil encriptaAESUtil;
    
    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    private static Logger log = LogManager.getLogger(UserController.class);
    
     @PostMapping(
        path="/sign-up",
        produces = MediaType.APPLICATION_JSON_VALUE, 
        consumes = MediaType.APPLICATION_JSON_VALUE
        )
    public ResponseEntity<?> signUp(@RequestBody UserVO userVO){
        log.info("-------------------------");
        log.info("auth/sign-up");
        ResponseEntity<?> response= null;
        try {

            usrHelper.validaExpresion(userVO.getEmail(), Constantes.EMAIL_REGEX,Constantes.EMAIL_MSG_FORMAT_ERROR,Constantes.PARAM_COD_ERROR);
            usrHelper.validaExpresion(userVO.getPassword(),Constantes.PASS_REGEX,Constantes.PASS_MSG_FORMAT_ERROR,Constantes.PARAM_COD_ERROR,Constantes.PASS_NUMBER);
            usrHelper.isNull(userVO.getName(),Constantes.NAME_MSG_ERROR_NULL_EMPTY,Constantes.PARAM_COD_ERROR);

           
            List<UserEntity> userList = userRepository.findByNameAndMail(userVO.getName(),userVO.getEmail());
            if(null==userList || userList.isEmpty()){

                UserEntity userEntity=new UserEntity(
                        userVO.getEmail(),
                        userVO.getName(),
                    encriptaAESUtil.encriptar(userVO.getPassword()),
                    Boolean.TRUE);

                final UserEntity userEntitys = userRepository.save(userEntity);
           
                List<PhoneEntity> listPhone = new ArrayList<>();
            
                /* se recorre la lista de phones y se traspasa el objeto vo a entity */
                if(null!=userVO.getPhones() && !userVO.getPhones().isEmpty()){
                    userVO.getPhones().forEach((p)->{
                        PhoneEntity phoneEntity= new PhoneEntity();
                        phoneEntity.toEntity(p,userEntitys.getId());
                        listPhone.add(phoneEntity);
                    });
                    /* eliminar objeto que tengan el mismo numero */
                    Set<PhoneEntity> phoneSet = new TreeSet<>((o1, o2) -> o1.getNumber().compareTo(o2.getNumber()));
                    phoneSet.addAll(listPhone);
                    phoneRepository.saveAll(phoneSet);
                }

                SingUpResponseVO sgnResponseVO= new SingUpResponseVO(userEntitys.getId(),
                userEntitys.getCreated(),
                userEntitys.getLastLogin(),
                jwtTokenUtil.generateJwtToken(userEntitys.getId().toString()),
                userEntitys.getIsActive());
                response= new ResponseEntity<>(sgnResponseVO,HttpStatus.CREATED);

            }else{

                List<ErrorVO> errList= new ArrayList<>();
                userList.forEach((u)->{

                    if(u.getEmail().equalsIgnoreCase(userVO.getEmail())){
                        errList.add(new ErrorVO(1,Constantes.EMAIL_MSG_ERROR_EXIST));
                    }if(u.getName().equalsIgnoreCase(userVO.getName())){
                        errList.add(new ErrorVO(1,Constantes.NAME_MSG_ERROR_EXIST));

                    }
                });
                response=  new ResponseEntity<>(new ErrorResponseVO(errList),HttpStatus.BAD_REQUEST);
                
            }

        }catch (BusinessException e) {
            log.error("Error  controlled.",e);
            response=new ResponseEntity<>(new ErrorResponseVO(Arrays.asList(new ErrorVO( e.getCode(),e.getMessage()))), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            log.error("Error not controlled.",e);
            response=new ResponseEntity<>(new ErrorResponseVO(Arrays.asList(new ErrorVO( Constantes.ERROR_CODE_INTERNAL, Constantes.ERROR_MSG_INTERNAL))), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
   
    


    @PostMapping(
        path="/login",
        produces = MediaType.APPLICATION_JSON_VALUE, 
        consumes = MediaType.APPLICATION_JSON_VALUE
        )
    public ResponseEntity<?> login(@RequestHeader("Authorization") String token, @RequestBody LoginVO loginVO) {
        
        log.info("-------------------------");
        log.info("auth/login");
        ResponseEntity<?> responseEntity=null;
        try {
           
            String strTkn=token.replace("Bearer ", "");
       
                jwtTokenUtil.validateToken(strTkn);
                usrHelper.nullOrEmpty(loginVO.getName(),Constantes.NAME_MSG_ERROR_NULL_EMPTY,Constantes.PARAM_COD_ERROR);
                usrHelper.nullOrEmpty(loginVO.getPassword(),Constantes.PASS_MSG_ERROR_NULL_EMPTY,Constantes.PARAM_COD_ERROR);
                 /* se obtiene el usuario por name y pass  */   
            UserEntity userEntity = userRepository.findByNameAndPass(loginVO.getName(), encriptaAESUtil.encriptar(loginVO.getPassword()));
               
            if(null==userEntity){
                    responseEntity=new ResponseEntity<>(new ErrorResponseVO(Arrays.asList(new ErrorVO( Constantes.USR_CODE_ERROR, Constantes.USR_MSG_ERROR))), HttpStatus.BAD_REQUEST);
                }else{
                    /* se valida que el token corresponda al id del usuario */
                    jwtTokenUtil.validateToken(strTkn,userEntity.getId().toString());
                    userEntity.setLastLogin(Calendar.getInstance().getTime());

                    userEntity=userRepository.save(userEntity);
                    UserResponseVO userResponse= new UserResponseVO();
                    userResponse.createdVO(userEntity);
                    responseEntity= new ResponseEntity<>(userResponse,HttpStatus.OK);
                }
                
        }catch (AutenticationException e) {
            log.error("Error auth user.",e);
            responseEntity=new ResponseEntity<>(new ErrorResponseVO(Arrays.asList(new ErrorVO( e.getCode(), e.getMessage()))), HttpStatus.UNAUTHORIZED);
        }catch (BusinessException e) {
            log.error("Error  controlled.",e);
            responseEntity=new ResponseEntity<>(new ErrorResponseVO(Arrays.asList(new ErrorVO( e.getCode(), e.getMessage()))), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            log.error("Error not controlled.",e);
            responseEntity=new ResponseEntity<>(new ErrorResponseVO(Arrays.asList(new ErrorVO( Constantes.ERROR_CODE_INTERNAL, Constantes.ERROR_MSG_INTERNAL))), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return responseEntity;
    }




}
