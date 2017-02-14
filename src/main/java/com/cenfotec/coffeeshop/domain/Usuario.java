package com.cenfotec.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Reserva> usuarios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Reserva> getUsuarios() {
        return usuarios;
    }

    public Usuario usuarios(Set<Reserva> reservas) {
        this.usuarios = reservas;
        return this;
    }

    public Usuario addUsuario(Reserva reserva) {
        usuarios.add(reserva);
        reserva.setUsuario(this);
        return this;
    }

    public Usuario removeUsuario(Reserva reserva) {
        usuarios.remove(reserva);
        reserva.setUsuario(null);
        return this;
    }

    public void setUsuarios(Set<Reserva> reservas) {
        this.usuarios = reservas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        if (usuario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + id +
            '}';
    }
}
