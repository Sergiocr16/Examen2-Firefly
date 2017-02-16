package com.cenfotec.coffeeshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.coffeeshop.service.BeneficioService;
import com.cenfotec.coffeeshop.web.rest.util.HeaderUtil;
import com.cenfotec.coffeeshop.web.rest.util.PaginationUtil;
import com.cenfotec.coffeeshop.service.dto.BeneficioDTO;

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
 * REST controller for managing Beneficio.
 */
@RestController
@RequestMapping("/api")
public class BeneficioResource {

    private final Logger log = LoggerFactory.getLogger(BeneficioResource.class);
        
    @Inject
    private BeneficioService beneficioService;

    /**
     * POST  /beneficios : Create a new beneficio.
     *
     * @param beneficioDTO the beneficioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beneficioDTO, or with status 400 (Bad Request) if the beneficio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/beneficios")
    @Timed
    public ResponseEntity<BeneficioDTO> createBeneficio(@Valid @RequestBody BeneficioDTO beneficioDTO) throws URISyntaxException {
        log.debug("REST request to save Beneficio : {}", beneficioDTO);
        if (beneficioDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("beneficio", "idexists", "A new beneficio cannot already have an ID")).body(null);
        }
        BeneficioDTO result = beneficioService.save(beneficioDTO);
        return ResponseEntity.created(new URI("/api/beneficios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("beneficio", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beneficios : Updates an existing beneficio.
     *
     * @param beneficioDTO the beneficioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beneficioDTO,
     * or with status 400 (Bad Request) if the beneficioDTO is not valid,
     * or with status 500 (Internal Server Error) if the beneficioDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/beneficios")
    @Timed
    public ResponseEntity<BeneficioDTO> updateBeneficio(@Valid @RequestBody BeneficioDTO beneficioDTO) throws URISyntaxException {
        log.debug("REST request to update Beneficio : {}", beneficioDTO);
        if (beneficioDTO.getId() == null) {
            return createBeneficio(beneficioDTO);
        }
        BeneficioDTO result = beneficioService.save(beneficioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("beneficio", beneficioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beneficios : get all the beneficios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of beneficios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/beneficios")
    @Timed
    public ResponseEntity<List<BeneficioDTO>> getAllBeneficios(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Beneficios");
        Page<BeneficioDTO> page = beneficioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/beneficios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /beneficios/:id : get the "id" beneficio.
     *
     * @param id the id of the beneficioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beneficioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/beneficios/{id}")
    @Timed
    public ResponseEntity<BeneficioDTO> getBeneficio(@PathVariable Long id) {
        log.debug("REST request to get Beneficio : {}", id);
        BeneficioDTO beneficioDTO = beneficioService.findOne(id);
        return Optional.ofNullable(beneficioDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /beneficios/:id : delete the "id" beneficio.
     *
     * @param id the id of the beneficioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/beneficios/{id}")
    @Timed
    public ResponseEntity<Void> deleteBeneficio(@PathVariable Long id) {
        log.debug("REST request to delete Beneficio : {}", id);
        beneficioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("beneficio", id.toString())).build();
    }

}
