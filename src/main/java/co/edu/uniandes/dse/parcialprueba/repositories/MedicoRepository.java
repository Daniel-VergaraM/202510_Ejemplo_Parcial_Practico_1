package co.edu.uniandes.dse.parcialprueba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;

public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {

    public MedicoEntity findByNombre(String nombre);

    public MedicoEntity findByApellido(String apellido);

    public MedicoEntity findByNombreAndApellido(String nombre, String apellido);

    public MedicoEntity findByRegistroMedico(String registroMedico);

}
