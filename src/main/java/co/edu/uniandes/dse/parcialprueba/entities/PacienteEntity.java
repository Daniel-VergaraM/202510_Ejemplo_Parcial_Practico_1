package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.List;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PacienteEntity extends BaseEntity {

    @NotNull
    public String edad;

    @NotNull
    public String nombre;

    @NotNull
    public Integer celular;

    @NotNull
    public String correo;

    @OneToMany(mappedBy = "paciente")
    public List<ConsultaMedicaEntity> consultas;
}
