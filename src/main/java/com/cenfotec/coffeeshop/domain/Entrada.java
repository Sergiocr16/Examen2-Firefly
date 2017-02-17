package com.cenfotec.coffeeshop.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Entrada.
 */
@Entity
@Table(name = "entrada")
public class Entrada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "kg", nullable = false)
    private Integer kg;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private ZonedDateTime fecha;

    @ManyToOne
    private Tipo tipo;

    @ManyToOne
    private Beneficio beneficio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKg() {
        return kg;
    }

    public Entrada kg(Integer kg) {
        this.kg = kg;
        return this;
    }

    public void setKg(Integer kg) {
        this.kg = kg;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Entrada fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Entrada tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Beneficio getBeneficio() {
        return beneficio;
    }

    public Entrada beneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
        return this;
    }

    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entrada entrada = (Entrada) o;
        if (entrada.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entrada.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entrada{" +
            "id=" + id +
            ", kg='" + kg + "'" +
            ", fecha='" + fecha + "'" +
            '}';
    }
}
