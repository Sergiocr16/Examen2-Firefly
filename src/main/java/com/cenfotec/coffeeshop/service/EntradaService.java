package com.cenfotec.coffeeshop.service;

import com.cenfotec.coffeeshop.domain.Entrada;
import com.cenfotec.coffeeshop.repository.EntradaRepository;
import com.cenfotec.coffeeshop.service.dto.EntradaDTO;
import com.cenfotec.coffeeshop.service.mapper.EntradaMapper;
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
 * Service Implementation for managing Entrada.
 */
@Service
@Transactional
public class EntradaService {

    private final Logger log = LoggerFactory.getLogger(EntradaService.class);
    
    @Inject
    private EntradaRepository entradaRepository;

    @Inject
    private EntradaMapper entradaMapper;

    /**
     * Save a entrada.
     *
     * @param entradaDTO the entity to save
     * @return the persisted entity
     */
    public EntradaDTO save(EntradaDTO entradaDTO) {
        log.debug("Request to save Entrada : {}", entradaDTO);
        Entrada entrada = entradaMapper.entradaDTOToEntrada(entradaDTO);
        entrada = entradaRepository.save(entrada);
        EntradaDTO result = entradaMapper.entradaToEntradaDTO(entrada);
        return result;
    }

    /**
     *  Get all the entradas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EntradaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entradas");
        Page<Entrada> result = entradaRepository.findAll(pageable);
        return result.map(entrada -> entradaMapper.entradaToEntradaDTO(entrada));
    }

    /**
     *  Get one entrada by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EntradaDTO findOne(Long id) {
        log.debug("Request to get Entrada : {}", id);
        Entrada entrada = entradaRepository.findOne(id);
        EntradaDTO entradaDTO = entradaMapper.entradaToEntradaDTO(entrada);
        return entradaDTO;
    }

    /**
     *  Delete the  entrada by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entrada : {}", id);
        entradaRepository.delete(id);
    }
}
