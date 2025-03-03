package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoService.class)
public class MedicoServiceTest {

    @Autowired
    private MedicoService service;

    @Autowired
    private TestEntityManager entityManager;

    private final PodamFactory factory = new PodamFactoryImpl();


    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
    }    

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedicoEntity entity = factory.manufacturePojo(MedicoEntity.class);
            entityManager.persist(entity);
        }
    }

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    @Test
    public void createMedicosTest() throws IllegalOperationException {
        MedicoEntity entity = factory.manufacturePojo(MedicoEntity.class);
        entity.setRegistroMedico("RM123");
        MedicoEntity result = service.createMedicos(entity);
        assertNotNull(result);
        MedicoEntity entity2 = entityManager.find(MedicoEntity.class, result.getId());
        assertEquals(entity, entity2);
    }

    @Test
    public void createMedicosExceptionTest() {
        MedicoEntity entity = factory.manufacturePojo(MedicoEntity.class);
        entity.setRegistroMedico(factory.manufacturePojo(String.class));
        assertThrows(IllegalOperationException.class, () -> {
            service.createMedicos(entity);
        });
    }
}
