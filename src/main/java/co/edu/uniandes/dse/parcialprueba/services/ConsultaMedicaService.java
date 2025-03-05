package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsultaMedicaService {

    @Autowired
    private ConsultaMedicaRepository repository;

    @Transactional
    public ConsultaMedicaEntity createConsulta(ConsultaMedicaEntity consulta) throws IllegalOperationException {
        if (consulta.getFecha().after(new Date())) {
            return repository.save(consulta);
        } else {
            throw new IllegalOperationException("La fecha de la consulta ya sucedió");
        }
    }

}
