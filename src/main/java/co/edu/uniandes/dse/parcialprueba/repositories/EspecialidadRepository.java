package co.edu.uniandes.dse.parcialprueba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;

public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Long> {

    public EspecialidadEntity findByNombre(String nombre);

    public EspecialidadEntity findByDescripcion(String descripcion);

    public EspecialidadEntity findByNombreAndDescripcion(String nombre, String descripcion);
}
