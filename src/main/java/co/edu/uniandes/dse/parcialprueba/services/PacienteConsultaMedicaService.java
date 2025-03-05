package co.edu.uniandes.dse.parcialprueba.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PacienteConsultaMedicaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaMedicaRepository consultaRepository;

    @Transactional
    public void addConsulta(@Valid @NotNull Long pacienteId, @Valid @NotNull Long consultaId) throws EntityNotFoundException, IllegalOperationException {
        PacienteEntity paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new EntityNotFoundException("No se encontro el paciente con id " + pacienteId));
        ConsultaMedicaEntity consulta = consultaRepository.findById(consultaId).orElseThrow(() -> new EntityNotFoundException("No se encontro la consulta con id " + consultaId));

        List<ConsultaMedicaEntity> consultas = paciente.getConsultas();

        for (ConsultaMedicaEntity c : consultas) {
            if (c.getFecha().equals(consulta.getFecha())) {
                throw new IllegalOperationException("El paciente no puede tener, dentro de sus consultas asignadas, consultas cuyas fechas coincidan");
            }
        }

        paciente.getConsultas().add(consulta);
        consulta.setPaciente(paciente);
    }

    @Transactional
    public List<ConsultaMedicaEntity> getConsultasProgramadas(@Valid @NotNull Long pacienteId) throws EntityNotFoundException, IllegalOperationException {
        PacienteEntity paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new EntityNotFoundException("No se encontro el paciente con el id " + pacienteId));

        List<ConsultaMedicaEntity> consultas = paciente.getConsultas();
        List<ConsultaMedicaEntity> consultasProgramadas = new ArrayList<>();

        if (consultas.isEmpty()) {
            throw new IllegalOperationException("El paciente no tiene consultas programadas");
        }

        for (ConsultaMedicaEntity c : consultas) {
            if (c.getFecha().after(new Date())) {
                consultasProgramadas.add(c);
            }
        }
        return consultasProgramadas;

    }

}
