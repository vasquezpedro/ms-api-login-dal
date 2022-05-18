package com.bci.exercises.dal.test

import com.bci.exercises.dal.entity.PhoneEntity
import com.bci.exercises.dal.entity.UserEntity
import com.bci.exercises.dal.repository.PhoneRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import spock.lang.Specification

@DataJpaTest
@ComponentScan(basePackages = "com.bci.exercises.dal")
@EntityScan(basePackageClasses  = [PhoneEntity])
class PhoneRepositoryTest extends Specification{

    @Autowired
    private PhoneRepository phoneRepository

    def "guardar una lista de numeros" () {

        setup: "se configura la lista a insertar y el id de usuario"
        def UUID uuID=UUID.randomUUID()
        def numbers = [
                new PhoneEntity(null,uuID,987456123,56,"CL"),
                new PhoneEntity(null,uuID,987454523,56,"CL"),
                new PhoneEntity(null,uuID,961238745,56,"CL")
        ]


        when: "se hace el guardar toda la lista"
        def saveListPhoneEntity  = phoneRepository.saveAll(numbers)

        then:"se espera que el listado contegan un tamaño de 3 y el id sea distinto de null"

        saveListPhoneEntity.size() == 3
        saveListPhoneEntity.get(0).getId() != null
        saveListPhoneEntity.get(1).getId() != null
        saveListPhoneEntity.get(2).getId() != null
    }
}
