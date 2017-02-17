package com.cenfotec.coffeeshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.coffeeshop.service.ReservaService;
import com.cenfotec.coffeeshop.web.rest.util.HeaderUtil;
import com.cenfotec.coffeeshop.web.rest.util.PaginationUtil;
import com.cenfotec.coffeeshop.service.dto.ReservaDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Reserva.
 */
@RestController
@RequestMapping("/api")
public class ReservaResource {

    private final Logger log = LoggerFactory.getLogger(ReservaResource.class);

    @Inject
    private ReservaService reservaService;

    /**
     * POST  /reservas : Create a new reserva.
     *
     * @param reservaDTO the reservaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reservaDTO, or with status 400 (Bad Request) if the reserva has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reservas")
    @Timed
    public ResponseEntity<ReservaDTO> createReserva(@Valid @RequestBody ReservaDTO reservaDTO) throws URISyntaxException {
        log.debug("REST request to save Reserva : {}", reservaDTO);
        if (reservaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reserva", "idexists", "A new reserva cannot already have an ID")).body(null);
        }
        ReservaDTO result = reservaService.save(reservaDTO);
        return ResponseEntity.created(new URI("/api/reservas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reserva", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reservas : Updates an existing reserva.
     *
     * @param reservaDTO the reservaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservaDTO,
     * or with status 400 (Bad Request) if the reservaDTO is not valid,
     * or with status 500 (Internal Server Error) if the reservaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reservas")
    @Timed
    public ResponseEntity<ReservaDTO> updateReserva(@Valid @RequestBody ReservaDTO reservaDTO) throws URISyntaxException {
        log.debug("REST request to update Reserva : {}", reservaDTO);
        if (reservaDTO.getId() == null) {
            return createReserva(reservaDTO);
        }
        ReservaDTO result = reservaService.save(reservaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reserva", reservaDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/delivers")
    @Timed
    public ResponseEntity<List<ReservaDTO>> findDelievers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Reservas");
        Page<ReservaDTO> page = reservaService.findDelivers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/delivers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/requestsForTomorrow")
    @Timed
    public ResponseEntity<List<ReservaDTO>> findrequestsForTomorrow(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Reservas");
        Page<ReservaDTO> page = reservaService.findrequestsForTomorrow(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/requestsForTomorrow");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/reservasByCurrentUser")
    @Timed
    public ResponseEntity<List<ReservaDTO>> getReservasByUser(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Reservas");
        Page<ReservaDTO> page = reservaService.findByUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservasbyuser");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /reservas : get all the reservas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reservas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/reservas")
    @Timed
    public ResponseEntity<List<ReservaDTO>> getAllReservas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Reservas");
        Page<ReservaDTO> page = reservaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reservas/:id : get the "id" reserva.
     *
     * @param id the id of the reservaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reservas/{id}")
    @Timed
    public ResponseEntity<ReservaDTO> getReserva(@PathVariable Long id) {
        log.debug("REST request to get Reserva : {}", id);
        ReservaDTO reservaDTO = reservaService.findOne(id);
        return Optional.ofNullable(reservaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reservas/:id : delete the "id" reserva.
     *
     * @param id the id of the reservaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reservas/{id}")
    @Timed
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        log.debug("REST request to delete Reserva : {}", id);
        reservaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reserva", id.toString())).build();
    }

}
