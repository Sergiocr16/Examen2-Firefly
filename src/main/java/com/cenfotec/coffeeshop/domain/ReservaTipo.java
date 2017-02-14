package com.cenfotec.coffeeshop.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ReservaTipo.
 */
@Entity
@Table(name = "reserva_tipo")
public class ReservaTipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    private Tipo tipo;

    @ManyToOne
    private Reserva reserva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public ReservaTipo cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public ReservaTipo tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public ReservaTipo reserva(Reserva reserva) {
        this.reserva = reserva;
        return this;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservaTipo reservaTipo = (ReservaTipo) o;
        if (reservaTipo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reservaTipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReservaTipo{" +
            "id=" + id +
            ", cantidad='" + cantidad + "'" +
            '}';
    }
}
