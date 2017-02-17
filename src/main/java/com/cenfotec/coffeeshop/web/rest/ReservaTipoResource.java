package com.cenfotec.coffeeshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.coffeeshop.service.ReservaTipoService;
import com.cenfotec.coffeeshop.web.rest.util.HeaderUtil;
import com.cenfotec.coffeeshop.web.rest.util.PaginationUtil;
import com.cenfotec.coffeeshop.service.dto.ReservaTipoDTO;

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
 * REST controller for managing ReservaTipo.
 */
@RestController
@RequestMapping("/api")
public class ReservaTipoResource {

    private final Logger log = LoggerFactory.getLogger(ReservaTipoResource.class);
        
    @Inject
    private ReservaTipoService reservaTipoService;

    /**
     * POST  /reserva-tipos : Create a new reservaTipo.
     *
     * @param reservaTipoDTO the reservaTipoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reservaTipoDTO, or with status 400 (Bad Request) if the reservaTipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reserva-tipos")
    @Timed
    public ResponseEntity<ReservaTipoDTO> createReservaTipo(@Valid @RequestBody ReservaTipoDTO reservaTipoDTO) throws URISyntaxException {
        log.debug("REST request to save ReservaTipo : {}", reservaTipoDTO);
        if (reservaTipoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reservaTipo", "idexists", "A new reservaTipo cannot already have an ID")).body(null);
        }
        ReservaTipoDTO result = reservaTipoService.save(reservaTipoDTO);
        return ResponseEntity.created(new URI("/api/reserva-tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reservaTipo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reserva-tipos : Updates an existing reservaTipo.
     *
     * @param reservaTipoDTO the reservaTipoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservaTipoDTO,
     * or with status 400 (Bad Request) if the reservaTipoDTO is not valid,
     * or with status 500 (Internal Server Error) if the reservaTipoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reserva-tipos")
    @Timed
    public ResponseEntity<ReservaTipoDTO> updateReservaTipo(@Valid @RequestBody ReservaTipoDTO reservaTipoDTO) throws URISyntaxException {
        log.debug("REST request to update ReservaTipo : {}", reservaTipoDTO);
        if (reservaTipoDTO.getId() == null) {
            return createReservaTipo(reservaTipoDTO);
        }
        ReservaTipoDTO result = reservaTipoService.save(reservaTipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reservaTipo", reservaTipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reserva-tipos : get all the reservaTipos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reservaTipos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/reserva-tipos")
    @Timed
    public ResponseEntity<List<ReservaTipoDTO>> getAllReservaTipos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ReservaTipos");
        Page<ReservaTipoDTO> page = reservaTipoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reserva-tipos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reserva-tipos/:id : get the "id" reservaTipo.
     *
     * @param id the id of the reservaTipoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservaTipoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reserva-tipos/{id}")
    @Timed
    public ResponseEntity<ReservaTipoDTO> getReservaTipo(@PathVariable Long id) {
        log.debug("REST request to get ReservaTipo : {}", id);
        ReservaTipoDTO reservaTipoDTO = reservaTipoService.findOne(id);
        return Optional.ofNullable(reservaTipoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reserva-tipos/:id : delete the "id" reservaTipo.
     *
     * @param id the id of the reservaTipoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reserva-tipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteReservaTipo(@PathVariable Long id) {
        log.debug("REST request to delete ReservaTipo : {}", id);
        reservaTipoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reservaTipo", id.toString())).build();
    }

}
