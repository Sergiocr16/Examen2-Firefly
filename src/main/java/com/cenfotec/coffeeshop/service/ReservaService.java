package com.cenfotec.coffeeshop.service;

import com.cenfotec.coffeeshop.domain.Reserva;
import com.cenfotec.coffeeshop.repository.ReservaRepository;
import com.cenfotec.coffeeshop.service.dto.ReservaDTO;
import com.cenfotec.coffeeshop.service.mapper.ReservaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Reserva.
 */
@Service
@Transactional
public class ReservaService {

    private final Logger log = LoggerFactory.getLogger(ReservaService.class);

    @Inject
    private ReservaRepository reservaRepository;

    @Inject
    private ReservaMapper reservaMapper;

    /**
     * Save a reserva.
     *
     * @param reservaDTO the entity to save
     * @return the persisted entity
     */
    public ReservaDTO save(ReservaDTO reservaDTO) {
        log.debug("Request to save Reserva : {}", reservaDTO);
        Reserva reserva = reservaMapper.reservaDTOToReserva(reservaDTO);
        reserva.setProcesado(false);
        reserva = reservaRepository.save(reserva);
        ReservaDTO result = reservaMapper.reservaToReservaDTO(reserva);
        return result;
    }
    @Transactional(readOnly = true)
    public Page<ReservaDTO> findrequestsForTomorrow(Pageable pageable) {
        List<Reserva> solicitudesManana = new ArrayList<Reserva>();

        Page<Reserva> result = reservaRepository.findAll(pageable);
        List<Reserva> solicitudes = getFutureDates(result);
        for (int i = 0; i < solicitudes.size(); i++) {
            Reserva reserva = solicitudes.get(i);
            if (reserva.getFechaEntrega().getDayOfYear() == (ZonedDateTime.now().plusDays(1).getDayOfYear())) {
            solicitudesManana.add(reserva);
            }
        }
        return new PageImpl<>(solicitudesManana).map(solicitudesForTomorrow -> reservaMapper.reservaToReservaDTO(solicitudesForTomorrow));

    }

    @Transactional(readOnly = true)
    public Page<ReservaDTO> findByUser(Pageable pageable) {
        List<Reserva> reservas = new ArrayList<Reserva>();

        Page<Reserva> result = reservaRepository.findByUsuarioIsCurrentUser(pageable);
        for (int i = 0; i < result.getContent().size(); i++) {
            Reserva reserva = result.getContent().get(i);
            if (reserva.isProcesado()==false) {
                reservas.add(reserva);
            }

        }
        return new PageImpl<>(reservas).map(futurareserva -> reservaMapper.reservaToReservaDTO(futurareserva));

    }
    @Transactional(readOnly = true)
    public Page<ReservaDTO> findDelivers(Pageable pageable){
        List<Reserva> entregas = new ArrayList<Reserva>();
        Page<Reserva> result = reservaRepository.findByUsuarioIsCurrentUser(pageable);
        for ( int i = 0; i < result.getContent().size(); i ++ ) {
            Reserva reserva = result.getContent().get(i);
            if (reserva.isProcesado()) {
                entregas.add(reserva);
            }

        }
        return new PageImpl<>(entregas).map(entrega -> reservaMapper.reservaToReservaDTO(entrega));
    }
    @Transactional(readOnly = true)
    public Page<ReservaDTO> AllRequests(Pageable pageable) {
        Page<Reserva> result = reservaRepository.findAll(pageable);
        List<Reserva> solicitudes = getFutureDates(result);
        return new PageImpl<>(solicitudes).map(reserva -> reservaMapper.reservaToReservaDTO(reserva));
    }

        protected List<Reserva>  getFutureDates(Page<Reserva> reservas){
            List<Reserva> solicitudes = new ArrayList<Reserva>();
              for ( int i = 0; i < reservas.getContent().size(); i ++ ) {
                Reserva reserva = reservas.getContent().get(i);
                if(reserva.isRecursivo()){
                    int difDias = 0;
                    ZonedDateTime fechaFutura = ZonedDateTime.now();
                    do{fechaFutura = fechaFutura.plusDays(difDias++);}
                    while(fechaFutura.getDayOfWeek().getValue() != reserva.getFechaEntrega().getDayOfWeek().getValue());
                    boolean sal = true;
                    int variable = 0;
                    while(sal){
                        if (fechaFutura.isBefore(reserva.getFechaEntrega())){
                            fechaFutura = fechaFutura.plusWeeks(variable++);
                        } else {
                            sal = false;
                        }
                    }
                    for(int j = 0; j<5;j++){
                        Reserva reservaNueva = new Reserva();
                        reservaNueva.setFechaEntrega(fechaFutura.plusWeeks(j));
                        reservaNueva.setUsuario(reserva.getUsuario());
                        solicitudes.add(reservaNueva);
                    }
                }
            }
            return solicitudes;
        }


    /**
     *  Get all the reservas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReservaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reservas");
        Page<Reserva> result = reservaRepository.findAll(pageable);
        return result.map(reserva -> reservaMapper.reservaToReservaDTO(reserva));
    }

    //                    ZonedDateTime fechaActual = ZonedDateTime.now();
//                      int miDia = reserva.getFechaEntrega().getDayOfWeek().getValue();
//               ZonedDateTime fechaFutura = ZonedDateTime.now().plusWeeks(1);
//                    for(int k = 0; k<5;k++){
//                      int diaSemanaFutura = reserva.getFechaEntrega().getDayOfWeek().getValue();
//
//                        while(diaSemanaFutura > 0){
//                            fechaFutura = fechaFutura.minusDays(1);
//                            diaSemanaFutura--;
//                        }
//                      int diaFechaFutura = fechaFutura.getDayOfWeek().getValue();
//                        for(int j = 0; j<7;j++){
//                            if(diaFechaFutura== miDia){
//                                Reserva reservaNueva = new Reserva();
//                                reservaNueva.setFechaEntrega(fechaFutura);
//                                reservaNueva.setUsuario(reserva.getUsuario());
//                                solicitudes.add(reservaNueva);
//                            }
//                            fechaFutura.plusDays(i);
//                        }
//                    }

    /**
     *  Get one reserva by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ReservaDTO findOne(Long id) {
        log.debug("Request to get Reserva : {}", id);
        Reserva reserva = reservaRepository.findOne(id);
        ReservaDTO reservaDTO = reservaMapper.reservaToReservaDTO(reserva);
        return reservaDTO;
    }


    /**
     *  Delete the  reserva by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reserva : {}", id);
        reservaRepository.delete(id);
    }
}
