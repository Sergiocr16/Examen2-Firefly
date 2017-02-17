package com.cenfotec.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull
    @Column(name = "fecha_entrega", nullable = false)
    private ZonedDateTime fechaEntrega;

    @NotNull
    @Column(name = "recursivo", nullable = false)
    private Boolean recursivo;

    @NotNull
    @Column(name = "procesado", nullable = false)
    private Boolean procesado;

    @ManyToOne
    private User usuario;

    @OneToMany(mappedBy = "reserva")
    @JsonIgnore
    private Set<ReservaTipo> reservasTipos = new HashSet<>();

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

    public Boolean isProcesado() {
        return procesado;
    }

    public Reserva recursivo(Boolean recursivo) {
        this.recursivo = recursivo;
        return this;
    }

    public Reserva procesado(Boolean procesado) {
        this.procesado = procesado;
        return this;
    }

    public void setProcesado(Boolean procesado) {
        this.procesado = procesado;
    }

    public void setRecursivo(Boolean recursivo) {
        this.recursivo = recursivo;
    }

    public User getUsuario() {
        return usuario;
    }

    public Reserva usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<ReservaTipo> getReservasTipos() {
        return reservasTipos;
    }

    public Reserva reservasTipos(Set<ReservaTipo> reservaTipos) {
        this.reservasTipos = reservaTipos;
        return this;
    }

    public Reserva addReservasTipos(ReservaTipo reservaTipo) {
        reservasTipos.add(reservaTipo);
        reservaTipo.setReserva(this);
        return this;
    }

    public Reserva removeReservasTipos(ReservaTipo reservaTipo) {
        reservasTipos.remove(reservaTipo);
        reservaTipo.setReserva(null);
        return this;
    }

    public void setReservasTipos(Set<ReservaTipo> reservaTipos) {
        this.reservasTipos = reservaTipos;
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
