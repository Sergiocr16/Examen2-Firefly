package com.cenfotec.coffeeshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.coffeeshop.domain.Reserva;

import com.cenfotec.coffeeshop.repository.ReservaRepository;
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
 * REST controller for managing Reserva.
 */
@RestController
@RequestMapping("/api")
public class ReservaResource {

    private final Logger log = LoggerFactory.getLogger(ReservaResource.class);
        
    @Inject
    private ReservaRepository reservaRepository;

    /**
     * POST  /reservas : Create a new reserva.
     *
     * @param reserva the reserva to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reserva, or with status 400 (Bad Request) if the reserva has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reservas")
    @Timed
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) throws URISyntaxException {
        log.debug("REST request to save Reserva : {}", reserva);
        if (reserva.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reserva", "idexists", "A new reserva cannot already have an ID")).body(null);
        }
        Reserva result = reservaRepository.save(reserva);
        return ResponseEntity.created(new URI("/api/reservas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reserva", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reservas : Updates an existing reserva.
     *
     * @param reserva the reserva to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reserva,
     * or with status 400 (Bad Request) if the reserva is not valid,
     * or with status 500 (Internal Server Error) if the reserva couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reservas")
    @Timed
    public ResponseEntity<Reserva> updateReserva(@RequestBody Reserva reserva) throws URISyntaxException {
        log.debug("REST request to update Reserva : {}", reserva);
        if (reserva.getId() == null) {
            return createReserva(reserva);
        }
        Reserva result = reservaRepository.save(reserva);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reserva", reserva.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reservas : get all the reservas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reservas in body
     */
    @GetMapping("/reservas")
    @Timed
    public List<Reserva> getAllReservas() {
        log.debug("REST request to get all Reservas");
        List<Reserva> reservas = reservaRepository.findAll();
        return reservas;
    }

    /**
     * GET  /reservas/:id : get the "id" reserva.
     *
     * @param id the id of the reserva to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reserva, or with status 404 (Not Found)
     */
    @GetMapping("/reservas/{id}")
    @Timed
    public ResponseEntity<Reserva> getReserva(@PathVariable Long id) {
        log.debug("REST request to get Reserva : {}", id);
        Reserva reserva = reservaRepository.findOne(id);
        return Optional.ofNullable(reserva)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reservas/:id : delete the "id" reserva.
     *
     * @param id the id of the reserva to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reservas/{id}")
    @Timed
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        log.debug("REST request to delete Reserva : {}", id);
        reservaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reserva", id.toString())).build();
    }

}
