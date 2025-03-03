package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.List;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadEntity extends BaseEntity {

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @ManyToMany(mappedBy = "especialidades")
    private List<MedicoEntity> medicos;

    public EspecialidadEntity(EspecialidadEntity esp) {
        super();
        this.nombre = esp.getNombre();
        this.descripcion = esp.getDescripcion();
        this.medicos = esp.getMedicos();
    }
}
