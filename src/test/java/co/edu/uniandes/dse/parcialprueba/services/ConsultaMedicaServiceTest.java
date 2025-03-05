package co.edu.uniandes.dse.parcialprueba.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ConsultaMedicaService.class)
public class ConsultaMedicaServiceTest {

    @Autowired
    private ConsultaMedicaService service;

    @Autowired
    private TestEntityManager entityManager;

    private final PodamFactory factory = new PodamFactoryImpl();

    private List<ConsultaMedicaEntity> data = new ArrayList<>();

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ConsultaMedicaEntity").executeUpdate();
        data.clear();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ConsultaMedicaEntity entity = factory.manufacturePojo(ConsultaMedicaEntity.class);
            entityManager.persist(entity);
            data.add(entity);
        }
    }

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    @Test
    public void createConsultaTest() throws IllegalOperationException {
        ConsultaMedicaEntity entity = factory.manufacturePojo(ConsultaMedicaEntity.class);
        Date manana = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        entity.setFecha(manana);
        ConsultaMedicaEntity result = service.createConsulta(entity);
        assertNotNull(result);
    }
}
