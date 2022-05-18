package com.bci.exercises.dal.test

import com.bci.exercises.dal.entity.PhoneEntity
import com.bci.exercises.dal.entity.UserEntity
import com.bci.exercises.dal.repository.PhoneRepository
import com.bci.exercises.dal.repository.UserRepository
import com.bci.exercises.dal.util.EncriptaAESUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import spock.lang.Specification

@DataJpaTest
@ComponentScan(basePackages = "com.bci.exercises.dal")
@EntityScan(basePackageClasses  = [UserEntity])
class UserRepositoryTest extends Specification{

    @Autowired
    private UserRepository userRepository
    @Autowired
    EncriptaAESUtil encriptaAESUtil
    String usuario="test"
    String email="aas@gmail.com"
    def "save" () {

        setup: "se configura la lista a insertar y el id de usuario"
        UserEntity user= new UserEntity("aas@gmail.com","test","asadff",true)

        when: "se hace el guardar toda la lista"
        def saveUserEntity  = userRepository.save(user)

        then:"se espera que el listado contegan un tamaño de 3 y el id sea distinto de null"

        saveUserEntity.getId()!=null
    }

    def "valida que no exista el usuario y email ingresado" () {

        setup: "se configura la lista a insertar y el id de usuario"
            String user="test"
            String pass=encriptaAESUtil.encriptar("dsa")
            def entity= new UserEntity("test@gmail.com",user,pass,true)
        when: "se busca el usuario o emial dentro de la tabla user"
        def getUserEntity  = userRepository.findByNameAndMail(usuario,email)

        then:"se espera que el listado contegan un tamaño de 3 y el id sea distinto de null"


        getUserEntity.isEmpty()
    }

    def "invalid user/pass" () {

        setup: "config user and pass"
        String user="test"
        String pass="test"

        when: "busqueda por nombre y pass"
        def findByNameAndPassEntity  = userRepository.findByNameAndPass(user,pass)

        then:"la respuesa debe ser null"

        findByNameAndPassEntity == null
    }

    def "user valid" () {

        setup: "config user and pass"
        String user="test"
        String pass=encriptaAESUtil.encriptar("dsa")
        def entity= new UserEntity("test@gmail.com",user,pass,true)

        when: "busqueda por nombre y pass"
        def entityResponse=userRepository.save(entity)
        def findByNameAndPassEntity  = userRepository.findByNameAndPass(user,pass)

        then:"la respuesa debe ser null"

        findByNameAndPassEntity != null
        entityResponse.getPassword().equalsIgnoreCase(findByNameAndPassEntity.getPassword())
        entityResponse.getName().equalsIgnoreCase(findByNameAndPassEntity.getName())

    }
}
