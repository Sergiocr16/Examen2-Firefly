package com.cenfotec.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tipo.
 */
@Entity
@Table(name = "tipo")
public class Tipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio_unitario")
    private Float precioUnitario;

    @OneToMany(mappedBy = "tipo")
    @JsonIgnore
    private Set<ReservaTipo> tipos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Tipo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public Tipo precioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
        return this;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Set<ReservaTipo> getTipos() {
        return tipos;
    }

    public Tipo tipos(Set<ReservaTipo> reservaTipos) {
        this.tipos = reservaTipos;
        return this;
    }

    public Tipo addTipo(ReservaTipo reservaTipo) {
        tipos.add(reservaTipo);
        reservaTipo.setTipo(this);
        return this;
    }

    public Tipo removeTipo(ReservaTipo reservaTipo) {
        tipos.remove(reservaTipo);
        reservaTipo.setTipo(null);
        return this;
    }

    public void setTipos(Set<ReservaTipo> reservaTipos) {
        this.tipos = reservaTipos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tipo tipo = (Tipo) o;
        if (tipo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tipo{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", precioUnitario='" + precioUnitario + "'" +
            '}';
    }
}
