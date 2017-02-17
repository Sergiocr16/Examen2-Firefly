package com.cenfotec.coffeeshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.coffeeshop.service.TipoService;
import com.cenfotec.coffeeshop.web.rest.util.HeaderUtil;
import com.cenfotec.coffeeshop.web.rest.util.PaginationUtil;
import com.cenfotec.coffeeshop.service.dto.TipoDTO;

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
 * REST controller for managing Tipo.
 */
@RestController
@RequestMapping("/api")
public class TipoResource {

    private final Logger log = LoggerFactory.getLogger(TipoResource.class);
        
    @Inject
    private TipoService tipoService;

    /**
     * POST  /tipos : Create a new tipo.
     *
     * @param tipoDTO the tipoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDTO, or with status 400 (Bad Request) if the tipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipos")
    @Timed
    public ResponseEntity<TipoDTO> createTipo(@Valid @RequestBody TipoDTO tipoDTO) throws URISyntaxException {
        log.debug("REST request to save Tipo : {}", tipoDTO);
        if (tipoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipo", "idexists", "A new tipo cannot already have an ID")).body(null);
        }
        TipoDTO result = tipoService.save(tipoDTO);
        return ResponseEntity.created(new URI("/api/tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipos : Updates an existing tipo.
     *
     * @param tipoDTO the tipoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDTO,
     * or with status 400 (Bad Request) if the tipoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipos")
    @Timed
    public ResponseEntity<TipoDTO> updateTipo(@Valid @RequestBody TipoDTO tipoDTO) throws URISyntaxException {
        log.debug("REST request to update Tipo : {}", tipoDTO);
        if (tipoDTO.getId() == null) {
            return createTipo(tipoDTO);
        }
        TipoDTO result = tipoService.save(tipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipo", tipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipos : get all the tipos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tipos")
    @Timed
    public ResponseEntity<List<TipoDTO>> getAllTipos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tipos");
        Page<TipoDTO> page = tipoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipos/:id : get the "id" tipo.
     *
     * @param id the id of the tipoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipos/{id}")
    @Timed
    public ResponseEntity<TipoDTO> getTipo(@PathVariable Long id) {
        log.debug("REST request to get Tipo : {}", id);
        TipoDTO tipoDTO = tipoService.findOne(id);
        return Optional.ofNullable(tipoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipos/:id : delete the "id" tipo.
     *
     * @param id the id of the tipoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipo(@PathVariable Long id) {
        log.debug("REST request to delete Tipo : {}", id);
        tipoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipo", id.toString())).build();
    }

}
