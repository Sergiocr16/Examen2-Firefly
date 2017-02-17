package com.cenfotec.coffeeshop.service.mapper;

import com.cenfotec.coffeeshop.domain.*;
import com.cenfotec.coffeeshop.service.dto.BeneficioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Beneficio and its DTO BeneficioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BeneficioMapper {

    BeneficioDTO beneficioToBeneficioDTO(Beneficio beneficio);

    List<BeneficioDTO> beneficiosToBeneficioDTOs(List<Beneficio> beneficios);

    Beneficio beneficioDTOToBeneficio(BeneficioDTO beneficioDTO);

    List<Beneficio> beneficioDTOsToBeneficios(List<BeneficioDTO> beneficioDTOs);
}
