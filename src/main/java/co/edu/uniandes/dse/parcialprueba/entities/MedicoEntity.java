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
public class MedicoEntity extends BaseEntity {

    @NotNull
    private String nombre;
    @NotNull
    private String apellido;

    @NotNull
    private String registroMedico;

    @ManyToMany
    private List<EspecialidadEntity> especialidades;

    public MedicoEntity(MedicoEntity med) {
        super();
        this.nombre = med.getNombre();
        this.apellido = med.getApellido();
        this.registroMedico = med.getRegistroMedico();
        this.especialidades = med.getEspecialidades();
    }
}
