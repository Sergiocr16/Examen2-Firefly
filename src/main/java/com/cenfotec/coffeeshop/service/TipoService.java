package com.cenfotec.coffeeshop.service;

import com.cenfotec.coffeeshop.domain.Tipo;
import com.cenfotec.coffeeshop.repository.TipoRepository;
import com.cenfotec.coffeeshop.service.dto.TipoDTO;
import com.cenfotec.coffeeshop.service.mapper.TipoMapper;
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
 * Service Implementation for managing Tipo.
 */
@Service
@Transactional
public class TipoService {

    private final Logger log = LoggerFactory.getLogger(TipoService.class);
    
    @Inject
    private TipoRepository tipoRepository;

    @Inject
    private TipoMapper tipoMapper;

    /**
     * Save a tipo.
     *
     * @param tipoDTO the entity to save
     * @return the persisted entity
     */
    public TipoDTO save(TipoDTO tipoDTO) {
        log.debug("Request to save Tipo : {}", tipoDTO);
        Tipo tipo = tipoMapper.tipoDTOToTipo(tipoDTO);
        tipo = tipoRepository.save(tipo);
        TipoDTO result = tipoMapper.tipoToTipoDTO(tipo);
        return result;
    }

    /**
     *  Get all the tipos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tipos");
        Page<Tipo> result = tipoRepository.findAll(pageable);
        return result.map(tipo -> tipoMapper.tipoToTipoDTO(tipo));
    }

    /**
     *  Get one tipo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TipoDTO findOne(Long id) {
        log.debug("Request to get Tipo : {}", id);
        Tipo tipo = tipoRepository.findOne(id);
        TipoDTO tipoDTO = tipoMapper.tipoToTipoDTO(tipo);
        return tipoDTO;
    }

    /**
     *  Delete the  tipo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tipo : {}", id);
        tipoRepository.delete(id);
    }
}
