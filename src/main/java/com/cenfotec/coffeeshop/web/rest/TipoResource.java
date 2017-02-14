package com.cenfotec.coffeeshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.coffeeshop.domain.Tipo;

import com.cenfotec.coffeeshop.repository.TipoRepository;
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
 * REST controller for managing Tipo.
 */
@RestController
@RequestMapping("/api")
public class TipoResource {

    private final Logger log = LoggerFactory.getLogger(TipoResource.class);
        
    @Inject
    private TipoRepository tipoRepository;

    /**
     * POST  /tipos : Create a new tipo.
     *
     * @param tipo the tipo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipo, or with status 400 (Bad Request) if the tipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipos")
    @Timed
    public ResponseEntity<Tipo> createTipo(@RequestBody Tipo tipo) throws URISyntaxException {
        log.debug("REST request to save Tipo : {}", tipo);
        if (tipo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipo", "idexists", "A new tipo cannot already have an ID")).body(null);
        }
        Tipo result = tipoRepository.save(tipo);
        return ResponseEntity.created(new URI("/api/tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipos : Updates an existing tipo.
     *
     * @param tipo the tipo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipo,
     * or with status 400 (Bad Request) if the tipo is not valid,
     * or with status 500 (Internal Server Error) if the tipo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipos")
    @Timed
    public ResponseEntity<Tipo> updateTipo(@RequestBody Tipo tipo) throws URISyntaxException {
        log.debug("REST request to update Tipo : {}", tipo);
        if (tipo.getId() == null) {
            return createTipo(tipo);
        }
        Tipo result = tipoRepository.save(tipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipo", tipo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipos : get all the tipos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipos in body
     */
    @GetMapping("/tipos")
    @Timed
    public List<Tipo> getAllTipos() {
        log.debug("REST request to get all Tipos");
        List<Tipo> tipos = tipoRepository.findAll();
        return tipos;
    }

    /**
     * GET  /tipos/:id : get the "id" tipo.
     *
     * @param id the id of the tipo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipo, or with status 404 (Not Found)
     */
    @GetMapping("/tipos/{id}")
    @Timed
    public ResponseEntity<Tipo> getTipo(@PathVariable Long id) {
        log.debug("REST request to get Tipo : {}", id);
        Tipo tipo = tipoRepository.findOne(id);
        return Optional.ofNullable(tipo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipos/:id : delete the "id" tipo.
     *
     * @param id the id of the tipo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipo(@PathVariable Long id) {
        log.debug("REST request to delete Tipo : {}", id);
        tipoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipo", id.toString())).build();
    }

}
