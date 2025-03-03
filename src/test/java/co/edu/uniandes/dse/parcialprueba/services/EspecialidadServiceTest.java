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

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EspecialidadService.class)
public class EspecialidadServiceTest {

    @Autowired
    private EspecialidadService service;

    @Autowired
    private TestEntityManager entityManager;

    private final PodamFactory factory = new PodamFactoryImpl();

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(entity);
        }
    }

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }
    
    @Test
    public void createEspecialidadTest() {
        EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
        entity.setDescripcion("Descripcion de la especialidad");
        EspecialidadEntity result = service.createEspecialidad(entity);
        assertNotNull(result);
        EspecialidadEntity entity2 = entityManager.find(EspecialidadEntity.class, result.getId());
        assertEquals(entity, entity2);
    }

    @Test
    public void createEspecialidadExceptionTest() {
        EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
        entity.setDescripcion("Desc");
        assertThrows(IllegalArgumentException.class, () -> {
            service.createEspecialidad(entity);
        });
    }
}
