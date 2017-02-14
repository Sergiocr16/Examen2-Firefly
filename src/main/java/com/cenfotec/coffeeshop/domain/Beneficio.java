package com.cenfotec.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Beneficio.
 */
@Entity
@Table(name = "beneficio")
public class Beneficio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "localizacion")
    private String localizacion;

    @OneToMany(mappedBy = "beneficio")
    @JsonIgnore
    private Set<Entrada> beneficios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Beneficio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Beneficio descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public Beneficio localizacion(String localizacion) {
        this.localizacion = localizacion;
        return this;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Set<Entrada> getBeneficios() {
        return beneficios;
    }

    public Beneficio beneficios(Set<Entrada> entradas) {
        this.beneficios = entradas;
        return this;
    }

    public Beneficio addBeneficio(Entrada entrada) {
        beneficios.add(entrada);
        entrada.setBeneficio(this);
        return this;
    }

    public Beneficio removeBeneficio(Entrada entrada) {
        beneficios.remove(entrada);
        entrada.setBeneficio(null);
        return this;
    }

    public void setBeneficios(Set<Entrada> entradas) {
        this.beneficios = entradas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Beneficio beneficio = (Beneficio) o;
        if (beneficio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, beneficio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Beneficio{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", localizacion='" + localizacion + "'" +
            '}';
    }
}
