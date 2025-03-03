package co.edu.uniandes.dse.parcialprueba.services;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity createEspecialidad(@Valid @NotNull EspecialidadEntity especialidad) throws IllegalArgumentException {

        if (especialidad.getDescripcion().length() < 10) {
            throw new IllegalArgumentException("La descripción debe tener al menos 10 caracteres.");
        }

        return especialidadRepository.save(especialidad);
    }

}
