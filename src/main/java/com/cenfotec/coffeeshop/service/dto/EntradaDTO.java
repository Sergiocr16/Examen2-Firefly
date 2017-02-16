package com.cenfotec.coffeeshop.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Entrada entity.
 */
public class EntradaDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer kg;

    @NotNull
    private ZonedDateTime fecha;


    private Long tipoId;
    

    private String tipoNombre;

    private Long beneficioId;
    

    private String beneficioNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getKg() {
        return kg;
    }

    public void setKg(Integer kg) {
        this.kg = kg;
    }
    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }


    public String getTipoNombre() {
        return tipoNombre;
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    public Long getBeneficioId() {
        return beneficioId;
    }

    public void setBeneficioId(Long beneficioId) {
        this.beneficioId = beneficioId;
    }


    public String getBeneficioNombre() {
        return beneficioNombre;
    }

    public void setBeneficioNombre(String beneficioNombre) {
        this.beneficioNombre = beneficioNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntradaDTO entradaDTO = (EntradaDTO) o;

        if ( ! Objects.equals(id, entradaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EntradaDTO{" +
            "id=" + id +
            ", kg='" + kg + "'" +
            ", fecha='" + fecha + "'" +
            '}';
    }
}
