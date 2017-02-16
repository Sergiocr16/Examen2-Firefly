package com.cenfotec.coffeeshop.service.mapper;

import com.cenfotec.coffeeshop.domain.*;
import com.cenfotec.coffeeshop.service.dto.ReservaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Reserva and its DTO ReservaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ReservaMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.login", target = "usuarioLogin")
    ReservaDTO reservaToReservaDTO(Reserva reserva);

    List<ReservaDTO> reservasToReservaDTOs(List<Reserva> reservas);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(target = "reservasTipos", ignore = true)
    Reserva reservaDTOToReserva(ReservaDTO reservaDTO);

    List<Reserva> reservaDTOsToReservas(List<ReservaDTO> reservaDTOs);
}
