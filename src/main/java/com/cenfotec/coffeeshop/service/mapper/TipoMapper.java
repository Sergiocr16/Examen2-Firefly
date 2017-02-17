package com.cenfotec.coffeeshop.service.mapper;

import com.cenfotec.coffeeshop.domain.*;
import com.cenfotec.coffeeshop.service.dto.TipoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Tipo and its DTO TipoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoMapper {

    TipoDTO tipoToTipoDTO(Tipo tipo);

    List<TipoDTO> tiposToTipoDTOs(List<Tipo> tipos);

    Tipo tipoDTOToTipo(TipoDTO tipoDTO);

    List<Tipo> tipoDTOsToTipos(List<TipoDTO> tipoDTOs);
}
