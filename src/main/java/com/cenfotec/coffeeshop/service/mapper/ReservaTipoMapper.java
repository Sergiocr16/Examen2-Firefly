package com.cenfotec.coffeeshop.service.mapper;

import com.cenfotec.coffeeshop.domain.*;
import com.cenfotec.coffeeshop.service.dto.ReservaTipoDTO;

import org.mapstruct.*;
import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity ReservaTipo and its DTO ReservaTipoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReservaTipoMapper {

    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.nombre", target = "tipoNombre")
    @Mapping(source = "reserva.id", target = "reservaId")
    ReservaTipoDTO reservaTipoToReservaTipoDTO(ReservaTipo reservaTipo);

    List<ReservaTipoDTO> reservaTiposToReservaTipoDTOs(List<ReservaTipo> reservaTipos);
    Set<ReservaTipoDTO> reservaTiposToReservaTipoDTOs(Set<ReservaTipo> reservaTipos);

    @Mapping(source = "tipoId", target = "tipo")
    @Mapping(source = "reservaId", target = "reserva")
    ReservaTipo reservaTipoDTOToReservaTipo(ReservaTipoDTO reservaTipoDTO);

    List<ReservaTipo> reservaTipoDTOsToReservaTipos(List<ReservaTipoDTO> reservaTipoDTOs);

    Set<ReservaTipo> reservaTipoDTOsToReservaTipos(Set<ReservaTipoDTO> reservaTipoDTOs);
    default Tipo tipoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Tipo tipo = new Tipo();
        tipo.setId(id);
        return tipo;
    }

    default Reserva reservaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Reserva reserva = new Reserva();
        reserva.setId(id);
        return reserva;
    }
}
