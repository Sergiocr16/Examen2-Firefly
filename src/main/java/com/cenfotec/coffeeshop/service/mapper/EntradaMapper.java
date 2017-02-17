package com.cenfotec.coffeeshop.service.mapper;

import com.cenfotec.coffeeshop.domain.*;
import com.cenfotec.coffeeshop.service.dto.EntradaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Entrada and its DTO EntradaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntradaMapper {

    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "tipo.nombre", target = "tipoNombre")
    @Mapping(source = "beneficio.id", target = "beneficioId")
    @Mapping(source = "beneficio.nombre", target = "beneficioNombre")
    EntradaDTO entradaToEntradaDTO(Entrada entrada);

    List<EntradaDTO> entradasToEntradaDTOs(List<Entrada> entradas);

    @Mapping(source = "tipoId", target = "tipo")
    @Mapping(source = "beneficioId", target = "beneficio")
    Entrada entradaDTOToEntrada(EntradaDTO entradaDTO);

    List<Entrada> entradaDTOsToEntradas(List<EntradaDTO> entradaDTOs);

    default Tipo tipoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Tipo tipo = new Tipo();
        tipo.setId(id);
        return tipo;
    }

    default Beneficio beneficioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Beneficio beneficio = new Beneficio();
        beneficio.setId(id);
        return beneficio;
    }
}
