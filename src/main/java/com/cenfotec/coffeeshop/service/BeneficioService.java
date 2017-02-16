package com.cenfotec.coffeeshop.service;

import com.cenfotec.coffeeshop.domain.Beneficio;
import com.cenfotec.coffeeshop.repository.BeneficioRepository;
import com.cenfotec.coffeeshop.service.dto.BeneficioDTO;
import com.cenfotec.coffeeshop.service.mapper.BeneficioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Beneficio.
 */
@Service
@Transactional
public class BeneficioService {

    private final Logger log = LoggerFactory.getLogger(BeneficioService.class);
    
    @Inject
    private BeneficioRepository beneficioRepository;

    @Inject
    private BeneficioMapper beneficioMapper;

    /**
     * Save a beneficio.
     *
     * @param beneficioDTO the entity to save
     * @return the persisted entity
     */
    public BeneficioDTO save(BeneficioDTO beneficioDTO) {
        log.debug("Request to save Beneficio : {}", beneficioDTO);
        Beneficio beneficio = beneficioMapper.beneficioDTOToBeneficio(beneficioDTO);
        beneficio = beneficioRepository.save(beneficio);
        BeneficioDTO result = beneficioMapper.beneficioToBeneficioDTO(beneficio);
        return result;
    }

    /**
     *  Get all the beneficios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<BeneficioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Beneficios");
        Page<Beneficio> result = beneficioRepository.findAll(pageable);
        return result.map(beneficio -> beneficioMapper.beneficioToBeneficioDTO(beneficio));
    }

    /**
     *  Get one beneficio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public BeneficioDTO findOne(Long id) {
        log.debug("Request to get Beneficio : {}", id);
        Beneficio beneficio = beneficioRepository.findOne(id);
        BeneficioDTO beneficioDTO = beneficioMapper.beneficioToBeneficioDTO(beneficio);
        return beneficioDTO;
    }

    /**
     *  Delete the  beneficio by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Beneficio : {}", id);
        beneficioRepository.delete(id);
    }
}
