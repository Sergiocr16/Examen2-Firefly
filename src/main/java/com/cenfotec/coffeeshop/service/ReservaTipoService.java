package com.cenfotec.coffeeshop.service;

import com.cenfotec.coffeeshop.domain.ReservaTipo;
import com.cenfotec.coffeeshop.repository.ReservaTipoRepository;
import com.cenfotec.coffeeshop.service.dto.ReservaDTO;
import com.cenfotec.coffeeshop.service.dto.ReservaTipoDTO;
import com.cenfotec.coffeeshop.service.mapper.ReservaTipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ReservaTipo.
 */
@Service
@Transactional
public class ReservaTipoService {

    private final Logger log = LoggerFactory.getLogger(ReservaTipoService.class);

    @Inject
    private ReservaTipoRepository reservaTipoRepository;

    @Inject
    private ReservaTipoMapper reservaTipoMapper;

    /**
     * Save a reservaTipo.
     *
     * @param reservaTipoDTO the entity to save
     * @return the persisted entity
     */
    public ReservaTipoDTO save(ReservaTipoDTO reservaTipoDTO) {
        log.debug("Request to save ReservaTipo : {}", reservaTipoDTO);
        ReservaTipo reservaTipo = reservaTipoMapper.reservaTipoDTOToReservaTipo(reservaTipoDTO);
        reservaTipo = reservaTipoRepository.save(reservaTipo);
        ReservaTipoDTO result = reservaTipoMapper.reservaTipoToReservaTipoDTO(reservaTipo);
        return result;
    }

    /**
     *  Get all the reservaTipos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReservaTipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReservaTipos");
        Page<ReservaTipo> result = reservaTipoRepository.findAll(pageable);
        return result.map(reservaTipo -> reservaTipoMapper.reservaTipoToReservaTipoDTO(reservaTipo));
    }

    /**
     *  Get one reservaTipo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ReservaTipoDTO findOne(Long id) {
        log.debug("Request to get ReservaTipo : {}", id);
        ReservaTipo reservaTipo = reservaTipoRepository.findOne(id);
        ReservaTipoDTO reservaTipoDTO = reservaTipoMapper.reservaTipoToReservaTipoDTO(reservaTipo);
        return reservaTipoDTO;
    }
    @Transactional(readOnly = true)
    public Page<ReservaTipoDTO> getTiposByReserva(Long id) {
        log.debug("Request to get all ReservaTipos");
        List<ReservaTipo> result = reservaTipoRepository.findByReservaId(id);
        return new PageImpl<>(result).map(reservaTipo -> reservaTipoMapper.reservaTipoToReservaTipoDTO(reservaTipo));
    }
    /**
     *  Delete the  reservaTipo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReservaTipo : {}", id);
        reservaTipoRepository.delete(id);
    }
}
