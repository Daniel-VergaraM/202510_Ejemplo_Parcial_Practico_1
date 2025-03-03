package co.edu.uniandes.dse.parcialprueba.services;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedicos(@Valid @NotNull MedicoEntity medico) throws IllegalOperationException {
        if (!medico.getRegistroMedico().startsWith("RM")) {
            throw new IllegalOperationException("El registro medico debe empezar con RM");
        }

        if (medicoRepository.findByRegistroMedico(medico.getRegistroMedico()) != null) {
            throw new IllegalOperationException("Ya existe un medico con ese registro medico");
        }

        return medicoRepository.save(medico);

    }
}
