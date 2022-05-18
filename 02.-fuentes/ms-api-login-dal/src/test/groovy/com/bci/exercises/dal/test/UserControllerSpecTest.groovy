package com.bci.exercises.dal.test

import com.bci.exercises.dal.MainApplication
import com.bci.exercises.dal.entity.PhoneEntity
import com.bci.exercises.dal.entity.UserEntity
import com.bci.exercises.dal.repository.PhoneRepository
import com.bci.exercises.dal.repository.UserRepository
import com.bci.exercises.dal.util.Constantes
import com.bci.exercises.dal.util.EncriptaAESUtil
import com.bci.exercises.dal.vo.ErrorResponseVO
import com.bci.exercises.dal.vo.LoginVO
import com.bci.exercises.dal.vo.PhonesVO
import com.bci.exercises.dal.vo.UserResponseVO
import com.bci.exercises.dal.vo.UserVO
import com.bci.exercises.dal.controller.UserController
import com.google.gson.Gson
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.bind.MissingRequestHeaderException
import spock.lang.Shared
import spock.lang.Specification




@WebMvcTest(controllers = [UserController])
@ComponentScan(basePackages = "com.bci.exercises.dal")
@EnableJpaRepositories(basePackageClasses  = [PhoneRepository, UserRepository])
@ContextConfiguration(classes = MainApplication.class)

class UserControllerSpecTest extends  Specification{

    @Autowired
    private  MockMvc mvcController
    private  String baseUrl="/auth"
    private  String urlLogin=baseUrl+"/login"
    private  String urlSingUp=baseUrl+"/sign-up"
    private  Gson gson = new Gson();
    @MockBean
    private PhoneRepository phoneRepository

    @MockBean
    private UserRepository userRepository

    @Shared  LoginVO loginVO= new LoginVO()
    @Shared  UserVO userVO= new UserVO()
    @Shared String tokenValid=""
    @Shared UserEntity userEntity
    @Autowired
    EncriptaAESUtil encriptaAESUtil


    def "/sing-up - 400 HTTP - email null"(){
        given:
        userVO.setEmail()
        userVO.setName("test")
        userVO.setPassword("124521354")
        userVO.setPhones(Arrays.asList(new PhonesVO(975391421,45,"+56"),
                new PhonesVO(975391421,46,"+56"),
                new PhonesVO(975391421,46,"+56")))
        String jsonRequest=gson.toJson(userVO)
        when:

        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        then:

        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.EMAIL_MSG_FORMAT_ERROR)

    }
    def "/sing-up - 400 HTTP - invalid email format."(){
        given:
        userVO.setEmail("t-gmail.com")
        userVO.setName("test")
        userVO.setPassword("124521354")
        userVO.setPhones(Arrays.asList(new PhonesVO(975391421,45,"+56"),
                new PhonesVO(975391421,46,"+56"),
                new PhonesVO(975391421,46,"+56")))
        String jsonRequest=gson.toJson(userVO)
        when:

        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        then:

        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.EMAIL_MSG_FORMAT_ERROR)

    }

    def "/sing-up - 400 HTTP - invalid password format. length > 12"(){
        given:
        userVO.setEmail("test1@gmail.com")
        userVO.setName("test")
        userVO.setPassword("a2asfGfdfdf4ds")
        userVO.setPhones(Arrays.asList(new PhonesVO(975391421,45,"+56"),
                new PhonesVO(975391421,46,"+56"),
                new PhonesVO(975391421,46,"+56")))
        String jsonRequest=gson.toJson(userVO)
        when:

        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        then:

        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.PASS_MSG_FORMAT_ERROR)

    }

    def "/sing-up - 400 HTTP - invalid password format. length < 8"(){
        given:
        userVO.setEmail("test1@gmail.com")
        userVO.setName("test")
        userVO.setPassword("a2afGfd")

        String jsonRequest=gson.toJson(userVO)
        when:

        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        then:

        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.PASS_MSG_FORMAT_ERROR)

    }

    def "/sing-up - 400 HTTP - invalid password format. mayusculas >1 "(){
        given:
        userVO.setEmail("test1@gmail.com")
        userVO.setName("test")
        userVO.setPassword("A2afGfdfdf4")
        userVO.setPhones(Arrays.asList(new PhonesVO(975391421,45,"+56"),
                new PhonesVO(975391421,46,"+56"),
                new PhonesVO(975391421,46,"+56")))
        String jsonRequest=gson.toJson(userVO)
        when:

        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        then:

        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.PASS_MSG_FORMAT_ERROR)

    }

    def "/sing-up - 400 HTTP - invalid password format. count numbers >2"(){
        given:
        userVO.setEmail("test1@gmail.com")
        userVO.setName("test")
        userVO.setPassword("22sfGfdfdf4")

        String jsonRequest=gson.toJson(userVO)
        when:

        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        then:

        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.PASS_MSG_FORMAT_ERROR)

    }



    def "/sing-up - 400 HTTP - Name null"(){
        given:
        userVO.setEmail("test1@gmail.com")
        userVO.setPassword("dAsssas21")
        userVO.setName(null)

        userVO.setPhones(Arrays.asList(new PhonesVO(975391421,45,"+56"),
                new PhonesVO(975391421,46,"+56"),
                new PhonesVO(975391421,46,"+56")))
        String jsonRequest=gson.toJson(userVO)

        when:
        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn()
        then:
        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.NAME_MSG_ERROR_NULL_EMPTY)

    }

    def "/sing-up - 400 HTTP - Password null o empty"(){
        given:
        userVO.setEmail("test1@gmail.com")
        userVO.setName("test")
        userVO.setPassword(null)
        userVO.setPhones(Arrays.asList(new PhonesVO(975391421,45,"+56"),
                new PhonesVO(975391421,46,"+56"),
                new PhonesVO(975391421,46,"+56")))
        String jsonRequest=gson.toJson(userVO)

        when:
        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn()
        then:
        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.PASS_MSG_FORMAT_ERROR)

    }

    def "/sing-up - 201 HTTP - created "(){
        given:"se prepara la data a consultar"
        userVO.setEmail("test1@gmail.com")
        userVO.setName("test")
        userVO.setPassword("a2asfGfdfdf4")
        userEntity=new UserEntity(userVO.getEmail(),userVO.getName(),encriptaAESUtil.encriptar(userVO.getPassword()),true)
        userEntity.setId(UUID.randomUUID())
        userEntity.setCreated(Calendar.getInstance().getTime())


        userVO.setPhones(Arrays.asList(new PhonesVO(975391421,45,"+56"),
                new PhonesVO(975391421,46,"+56"),
                new PhonesVO(975391421,46,"+56")))
        String jsonRequest=gson.toJson(userVO)

        when:"se consulta el endpoint login"

            Mockito.when(userRepository.save(Mockito.any(UserEntity))).thenReturn(userEntity);

            MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlSingUp).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                        .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()

            UserResponseVO userResponseVO=gson.fromJson(result.response.getContentAsString(),UserResponseVO);
        this.loginVO.setPassword(userVO.getPassword())
        this.loginVO.setName(userVO.getName())
        this.tokenValid = userResponseVO.getToken()
        userEntity.setLastLogin(userResponseVO.getLastLogin())
        then: "validamos la respuesta que no sea null"
            userResponseVO.getId() != null


    }
    //------------------------------------------------------------------------

    def "/login - 200 HTTP - authenticate"(){

        given:"se prepara la data a consultar"
            String jsonRequest=gson.toJson(this.loginVO)
            String token="Bearer "+this.tokenValid
            userEntity.setPhones(Arrays.asList(
                    new PhoneEntity(1,userEntity.getId(),975391421,45,"CL"),
                    new PhoneEntity(2,userEntity.getId(),975391421,45,"CL"),
                    new PhoneEntity(3,userEntity.getId(),975391421,45,"CL")))

        when:"se consulta el endpoint login"

            Mockito.when(userRepository.findByNameAndPass(Mockito.eq(loginVO.getName()),
                    Mockito.eq(encriptaAESUtil.encriptar(loginVO.getPassword()))))
                    .thenReturn(userEntity);

            Mockito.when(userRepository.save(Mockito.any(UserEntity))).thenReturn(userEntity);

            MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlLogin).header("Authorization",token).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        then: "validamos la respuesta que no sea null"
            UserResponseVO userResponseVO=gson.fromJson(result.response.getContentAsString(),UserResponseVO);
            userResponseVO.getId() != null


    }

    def "/login - 400 HTTP - Required request header 'Authorization'"(){
        given:
            String jsonRequest=gson.toJson(this.loginVO)
        when:

            MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlLogin).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn();
        then:

            Optional<MissingRequestHeaderException> someException = Optional.ofNullable((MissingRequestHeaderException) result.getResolvedException());
            someException.isPresent()
            someException.get().message=="Required request header 'Authorization' for method parameter type String is not present"


    }

    def "/login - 401 HTTP - Malformed Token."(){
        given:

        loginVO.setName("test-3")
        loginVO.setPassword("dAss1s4as21e2323231");
            String jsonRequest=gson.toJson(loginVO)
            String token="Bearer tedfdklsdksdks"
        when:
        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlLogin).header("Authorization",token).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andReturn()
        then:

        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.TOKEN_CODE_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.TOKEN_MSG_MALFORMED)


    }

    def "/login - 401 HTTP - Expired Token."(){
        given:

        loginVO.setName("test-3")
        loginVO.setPassword("dAss1s4as21e2323231");
        String jsonRequest=gson.toJson(loginVO)
        String token="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlZTQzOTA1Ny1jN2JhLTQ3MGQtYWRhOS01NjhkNjAxNDIyMDYiLCJpYXQiOjE2NTEyMDEwMTAsImV4cCI6MTY1MTIxMTAxMH0.egcRNn4pqGneHCN2nGQXtJnvXFtRvXMwTjKmPZTt_u6Ac3KeXs_oLZOtWB_LM4jRjDOyT5tPyRmrQ34-4d5OlA"
        when:
        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlLogin).header("Authorization",token).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn()
        then:

        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.TOKEN_CODE_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.TOKEN_MSG_EXPIRED)


    }

    def "/login - 400 HTTP - Name null o empty"(){
        given:
        loginVO.setName("")
        String jsonRequest=gson.toJson(loginVO)
        String token="Bearer "+tokenValid
        when:
        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlLogin).header("Authorization",token).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn()
        then:
        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.NAME_MSG_ERROR_NULL_EMPTY)

    }

    def "/login - 400 HTTP - Password null o empty"(){
        given:
        loginVO.setName(userVO.getName())
        loginVO.setPassword(null)
            String jsonRequest=gson.toJson(loginVO)
            String token="Bearer "+tokenValid
        when:
        MvcResult result=mvcController.perform(MockMvcRequestBuilders.post(urlLogin).header("Authorization",token).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn()
        then:
        ErrorResponseVO errorVO=gson.fromJson(result.response.getContentAsString(),ErrorResponseVO);
        errorVO.getError().get(0).getCodigo()==Constantes.PARAM_COD_ERROR
        errorVO.getError().get(0).getDetail().equalsIgnoreCase(Constantes.PASS_MSG_ERROR_NULL_EMPTY)

    }







}
