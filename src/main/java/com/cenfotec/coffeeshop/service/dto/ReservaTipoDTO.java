package com.cenfotec.coffeeshop.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ReservaTipo entity.
 */
public class ReservaTipoDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer cantidad;


    private Long tipoId;
    

    private String tipoNombre;

    private Long reservaId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReservaTipoDTO reservaTipoDTO = (ReservaTipoDTO) o;

        if ( ! Objects.equals(id, reservaTipoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReservaTipoDTO{" +
            "id=" + id +
            ", cantidad='" + cantidad + "'" +
            '}';
    }
}
