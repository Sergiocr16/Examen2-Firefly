package com.cenfotec.coffeeshop.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Tipo entity.
 */
public class TipoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    @DecimalMin(value = "0")
    private Double precioUnitario;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoDTO tipoDTO = (TipoDTO) o;

        if ( ! Objects.equals(id, tipoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", precioUnitario='" + precioUnitario + "'" +
            '}';
    }
}
