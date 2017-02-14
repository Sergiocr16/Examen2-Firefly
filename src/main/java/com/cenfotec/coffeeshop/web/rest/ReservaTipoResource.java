package com.cenfotec.coffeeshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.coffeeshop.domain.ReservaTipo;

import com.cenfotec.coffeeshop.repository.ReservaTipoRepository;
import com.cenfotec.coffeeshop.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReservaTipo.
 */
@RestController
@RequestMapping("/api")
public class ReservaTipoResource {

    private final Logger log = LoggerFactory.getLogger(ReservaTipoResource.class);
        
    @Inject
    private ReservaTipoRepository reservaTipoRepository;

    /**
     * POST  /reserva-tipos : Create a new reservaTipo.
     *
     * @param reservaTipo the reservaTipo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reservaTipo, or with status 400 (Bad Request) if the reservaTipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reserva-tipos")
    @Timed
    public ResponseEntity<ReservaTipo> createReservaTipo(@RequestBody ReservaTipo reservaTipo) throws URISyntaxException {
        log.debug("REST request to save ReservaTipo : {}", reservaTipo);
        if (reservaTipo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reservaTipo", "idexists", "A new reservaTipo cannot already have an ID")).body(null);
        }
        ReservaTipo result = reservaTipoRepository.save(reservaTipo);
        return ResponseEntity.created(new URI("/api/reserva-tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reservaTipo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reserva-tipos : Updates an existing reservaTipo.
     *
     * @param reservaTipo the reservaTipo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservaTipo,
     * or with status 400 (Bad Request) if the reservaTipo is not valid,
     * or with status 500 (Internal Server Error) if the reservaTipo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reserva-tipos")
    @Timed
    public ResponseEntity<ReservaTipo> updateReservaTipo(@RequestBody ReservaTipo reservaTipo) throws URISyntaxException {
        log.debug("REST request to update ReservaTipo : {}", reservaTipo);
        if (reservaTipo.getId() == null) {
            return createReservaTipo(reservaTipo);
        }
        ReservaTipo result = reservaTipoRepository.save(reservaTipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reservaTipo", reservaTipo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reserva-tipos : get all the reservaTipos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reservaTipos in body
     */
    @GetMapping("/reserva-tipos")
    @Timed
    public List<ReservaTipo> getAllReservaTipos() {
        log.debug("REST request to get all ReservaTipos");
        List<ReservaTipo> reservaTipos = reservaTipoRepository.findAll();
        return reservaTipos;
    }

    /**
     * GET  /reserva-tipos/:id : get the "id" reservaTipo.
     *
     * @param id the id of the reservaTipo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservaTipo, or with status 404 (Not Found)
     */
    @GetMapping("/reserva-tipos/{id}")
    @Timed
    public ResponseEntity<ReservaTipo> getReservaTipo(@PathVariable Long id) {
        log.debug("REST request to get ReservaTipo : {}", id);
        ReservaTipo reservaTipo = reservaTipoRepository.findOne(id);
        return Optional.ofNullable(reservaTipo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reserva-tipos/:id : delete the "id" reservaTipo.
     *
     * @param id the id of the reservaTipo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reserva-tipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteReservaTipo(@PathVariable Long id) {
        log.debug("REST request to delete ReservaTipo : {}", id);
        reservaTipoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reservaTipo", id.toString())).build();
    }

}
