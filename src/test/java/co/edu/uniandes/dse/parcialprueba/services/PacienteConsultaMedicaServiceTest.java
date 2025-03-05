package co.edu.uniandes.dse.parcialprueba.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PacienteConsultaMedicaService.class)
public class PacienteConsultaMedicaServiceTest {

    @Autowired
    private PacienteConsultaMedicaService service;

    @Autowired
    private TestEntityManager entityManager;

    private final PodamFactory factory = new PodamFactoryImpl();

    private List<PacienteEntity> pacientes = new ArrayList<>();

    private List<ConsultaMedicaEntity> consultas = new ArrayList<>();

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PacienteEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ConsultaMedicaEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PacienteEntity entity = factory.manufacturePojo(PacienteEntity.class);

            entityManager.persist(entity);
            pacientes.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            ConsultaMedicaEntity entity = factory.manufacturePojo(ConsultaMedicaEntity.class);
            entity.setFecha(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
            entityManager.persist(entity);
            consultas.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            pacientes.get(i).getConsultas().add(consultas.get(i));
        }

    }

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    @Test
    public void addConsultaTest() throws EntityNotFoundException, IllegalOperationException, InterruptedException {
        PacienteEntity paciente = pacientes.get(0);
        ConsultaMedicaEntity consulta = factory.manufacturePojo(ConsultaMedicaEntity.class);
        entityManager.persist(consulta);
        consulta.setFecha(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
        service.addConsulta(paciente.getId(), consulta.getId());
        Thread.sleep(500);
        List<ConsultaMedicaEntity> result = paciente.getConsultas();
        assertEquals(7, result.size());
    }

    @Test
    public void addConsultaExceptionTest() {
        PacienteEntity paciente = pacientes.get(0);
        ConsultaMedicaEntity consulta = consultas.get(0);
        assertThrows(IllegalOperationException.class, () -> {
            service.addConsulta(paciente.getId(), consulta.getId());
        });
    }


    @Test
    public void getConsultasProgramadasTest() throws EntityNotFoundException, IllegalOperationException {
        PacienteEntity paciente = pacientes.get(0);
        List<ConsultaMedicaEntity> result = service.getConsultasProgramadas(paciente.getId());
        assertEquals(1, result.size());
    }

    @Test
    public void getConsultasProgramadasExceptionTest() {
        PacienteEntity paciente = factory.manufacturePojo(PacienteEntity.class);
        paciente.setConsultas(new ArrayList<>());
        entityManager.persist(paciente);

        assertThrows(IllegalOperationException.class, () -> {
            service.getConsultasProgramadas(paciente.getId());
        });
    }
}
