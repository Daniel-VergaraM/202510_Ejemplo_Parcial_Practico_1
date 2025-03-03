package co.edu.uniandes.dse.parcialprueba.services;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public void addEspecialidad(@Valid @NotNull Long medicoId, @Valid @NotNull Long especialidadId) throws EntityNotFoundException, IllegalOperationException {
        MedicoEntity medico = medicoRepository.findById(medicoId).orElseThrow(() -> new EntityNotFoundException("No se encontro el medico con id " + medicoId));
        EspecialidadEntity especialidad = especialidadRepository.findById(especialidadId).orElseThrow(() -> new EntityNotFoundException("No se encontro la especialidad con id " + especialidadId));

        if (medico.getEspecialidades().contains(especialidad)) {
            throw new IllegalOperationException("El medico ya tiene la especialidad");
        }

        if (especialidad.getMedicos().contains(medico)) {
            throw new IllegalOperationException("La especialidad ya tiene el medico");
        }

        medico.getEspecialidades().add(especialidad);
        especialidad.getMedicos().add(medico);
    }
}
