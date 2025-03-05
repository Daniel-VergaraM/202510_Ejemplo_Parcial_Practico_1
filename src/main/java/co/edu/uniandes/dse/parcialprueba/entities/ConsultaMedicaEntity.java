package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.Date;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaMedicaEntity extends BaseEntity {

    @NotNull
    public String causa;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    public Date fecha;

    @ManyToOne
    public PacienteEntity paciente;
}
