package com.cenfotec.coffeeshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.coffeeshop.service.EntradaService;
import com.cenfotec.coffeeshop.web.rest.util.HeaderUtil;
import com.cenfotec.coffeeshop.web.rest.util.PaginationUtil;
import com.cenfotec.coffeeshop.service.dto.EntradaDTO;

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
 * REST controller for managing Entrada.
 */
@RestController
@RequestMapping("/api")
public class EntradaResource {

    private final Logger log = LoggerFactory.getLogger(EntradaResource.class);
        
    @Inject
    private EntradaService entradaService;

    /**
     * POST  /entradas : Create a new entrada.
     *
     * @param entradaDTO the entradaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entradaDTO, or with status 400 (Bad Request) if the entrada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entradas")
    @Timed
    public ResponseEntity<EntradaDTO> createEntrada(@Valid @RequestBody EntradaDTO entradaDTO) throws URISyntaxException {
        log.debug("REST request to save Entrada : {}", entradaDTO);
        if (entradaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entrada", "idexists", "A new entrada cannot already have an ID")).body(null);
        }
        EntradaDTO result = entradaService.save(entradaDTO);
        return ResponseEntity.created(new URI("/api/entradas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entrada", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entradas : Updates an existing entrada.
     *
     * @param entradaDTO the entradaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entradaDTO,
     * or with status 400 (Bad Request) if the entradaDTO is not valid,
     * or with status 500 (Internal Server Error) if the entradaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entradas")
    @Timed
    public ResponseEntity<EntradaDTO> updateEntrada(@Valid @RequestBody EntradaDTO entradaDTO) throws URISyntaxException {
        log.debug("REST request to update Entrada : {}", entradaDTO);
        if (entradaDTO.getId() == null) {
            return createEntrada(entradaDTO);
        }
        EntradaDTO result = entradaService.save(entradaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entrada", entradaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entradas : get all the entradas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entradas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/entradas")
    @Timed
    public ResponseEntity<List<EntradaDTO>> getAllEntradas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Entradas");
        Page<EntradaDTO> page = entradaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entradas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entradas/:id : get the "id" entrada.
     *
     * @param id the id of the entradaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entradaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entradas/{id}")
    @Timed
    public ResponseEntity<EntradaDTO> getEntrada(@PathVariable Long id) {
        log.debug("REST request to get Entrada : {}", id);
        EntradaDTO entradaDTO = entradaService.findOne(id);
        return Optional.ofNullable(entradaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entradas/:id : delete the "id" entrada.
     *
     * @param id the id of the entradaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entradas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) {
        log.debug("REST request to delete Entrada : {}", id);
        entradaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entrada", id.toString())).build();
    }

}
