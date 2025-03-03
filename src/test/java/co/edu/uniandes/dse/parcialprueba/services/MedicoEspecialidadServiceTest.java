package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({MedicoEspecialidadService.class, MedicoService.class, EspecialidadService.class})
public class MedicoEspecialidadServiceTest {

    @Autowired
    private MedicoEspecialidadService service;

    @Autowired
    private MedicoService mService;

    @Autowired
    private EspecialidadService eService;

    @Autowired
    private TestEntityManager entityManager;

    private final PodamFactory factory = new PodamFactoryImpl();

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedicoEntity entity = factory.manufacturePojo(MedicoEntity.class);
            EspecialidadEntity entity2 = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(entity);
            entityManager.persist(entity2);
        }
    }

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    @Test
    public void addEspecialidadTest() throws EntityNotFoundException, IllegalOperationException, InterruptedException {
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setRegistroMedico("RM" + factory.manufacturePojo(String.class));
        Thread.sleep(1000);
        mService.createMedicos(medico);



        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        Thread.sleep(1000);
        eService.createEspecialidad(especialidad);
        service.addEspecialidad(medico.getId(), especialidad.getId());
    }

    @Test
    public void addEspecialidadMedicoTest() throws EntityNotFoundException, IllegalOperationException {
        Long medicoId = factory.manufacturePojo(Long.class);
        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        especialidad.setId(factory.manufacturePojo(Long.class));
        eService.createEspecialidad(especialidad);
        assertThrows(EntityNotFoundException.class, () -> {
            service.addEspecialidad(medicoId, especialidad.getId());
        });
    }

    @Test
    public void addEspecialidadEspecialidadTest() throws EntityNotFoundException, IllegalOperationException {
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setId(factory.manufacturePojo(Long.class));
        medico.setRegistroMedico("RM" + factory.manufacturePojo(String.class));
        mService.createMedicos(medico);
        Long especialidadId = factory.manufacturePojo(Long.class);
        assertThrows(EntityNotFoundException.class, () -> {
            service.addEspecialidad(medico.getId(), especialidadId);
        });
    }
}
