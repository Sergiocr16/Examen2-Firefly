package com.cenfotec.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reserva.
 */
@Entity
@Table(name = "reserva")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fecha_entrega")
    private ZonedDateTime fechaEntrega;

    @Column(name = "recursivo")
    private Boolean recursivo;

    @OneToMany(mappedBy = "reserva")
    @JsonIgnore
    private Set<ReservaTipo> reservas = new HashSet<>();

    @ManyToOne
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public Reserva fechaEntrega(ZonedDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public void setFechaEntrega(ZonedDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Boolean isRecursivo() {
        return recursivo;
    }

    public Reserva recursivo(Boolean recursivo) {
        this.recursivo = recursivo;
        return this;
    }

    public void setRecursivo(Boolean recursivo) {
        this.recursivo = recursivo;
    }

    public Set<ReservaTipo> getReservas() {
        return reservas;
    }

    public Reserva reservas(Set<ReservaTipo> reservaTipos) {
        this.reservas = reservaTipos;
        return this;
    }

    public Reserva addReserva(ReservaTipo reservaTipo) {
        reservas.add(reservaTipo);
        reservaTipo.setReserva(this);
        return this;
    }

    public Reserva removeReserva(ReservaTipo reservaTipo) {
        reservas.remove(reservaTipo);
        reservaTipo.setReserva(null);
        return this;
    }

    public void setReservas(Set<ReservaTipo> reservaTipos) {
        this.reservas = reservaTipos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Reserva usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reserva reserva = (Reserva) o;
        if (reserva.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reserva{" +
            "id=" + id +
            ", fechaEntrega='" + fechaEntrega + "'" +
            ", recursivo='" + recursivo + "'" +
            '}';
    }
}
