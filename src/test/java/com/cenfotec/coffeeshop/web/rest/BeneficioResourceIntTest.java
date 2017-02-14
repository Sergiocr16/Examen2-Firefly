package com.cenfotec.coffeeshop.web.rest;

import com.cenfotec.coffeeshop.CoffeeShopApp;

import com.cenfotec.coffeeshop.domain.Beneficio;
import com.cenfotec.coffeeshop.repository.BeneficioRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BeneficioResource REST controller.
 *
 * @see BeneficioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoffeeShopApp.class)
public class BeneficioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIZACION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIZACION = "BBBBBBBBBB";

    @Inject
    private BeneficioRepository beneficioRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBeneficioMockMvc;

    private Beneficio beneficio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BeneficioResource beneficioResource = new BeneficioResource();
        ReflectionTestUtils.setField(beneficioResource, "beneficioRepository", beneficioRepository);
        this.restBeneficioMockMvc = MockMvcBuilders.standaloneSetup(beneficioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficio createEntity(EntityManager em) {
        Beneficio beneficio = new Beneficio()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION)
                .localizacion(DEFAULT_LOCALIZACION);
        return beneficio;
    }

    @Before
    public void initTest() {
        beneficio = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficio() throws Exception {
        int databaseSizeBeforeCreate = beneficioRepository.findAll().size();

        // Create the Beneficio

        restBeneficioMockMvc.perform(post("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficio)))
            .andExpect(status().isCreated());

        // Validate the Beneficio in the database
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeCreate + 1);
        Beneficio testBeneficio = beneficioList.get(beneficioList.size() - 1);
        assertThat(testBeneficio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testBeneficio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testBeneficio.getLocalizacion()).isEqualTo(DEFAULT_LOCALIZACION);
    }

    @Test
    @Transactional
    public void createBeneficioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficioRepository.findAll().size();

        // Create the Beneficio with an existing ID
        Beneficio existingBeneficio = new Beneficio();
        existingBeneficio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficioMockMvc.perform(post("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBeneficio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBeneficios() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList
        restBeneficioMockMvc.perform(get("/api/beneficios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].localizacion").value(hasItem(DEFAULT_LOCALIZACION.toString())));
    }

    @Test
    @Transactional
    public void getBeneficio() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get the beneficio
        restBeneficioMockMvc.perform(get("/api/beneficios/{id}", beneficio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.localizacion").value(DEFAULT_LOCALIZACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBeneficio() throws Exception {
        // Get the beneficio
        restBeneficioMockMvc.perform(get("/api/beneficios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficio() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);
        int databaseSizeBeforeUpdate = beneficioRepository.findAll().size();

        // Update the beneficio
        Beneficio updatedBeneficio = beneficioRepository.findOne(beneficio.getId());
        updatedBeneficio
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION)
                .localizacion(UPDATED_LOCALIZACION);

        restBeneficioMockMvc.perform(put("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeneficio)))
            .andExpect(status().isOk());

        // Validate the Beneficio in the database
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeUpdate);
        Beneficio testBeneficio = beneficioList.get(beneficioList.size() - 1);
        assertThat(testBeneficio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testBeneficio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testBeneficio.getLocalizacion()).isEqualTo(UPDATED_LOCALIZACION);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficio() throws Exception {
        int databaseSizeBeforeUpdate = beneficioRepository.findAll().size();

        // Create the Beneficio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBeneficioMockMvc.perform(put("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficio)))
            .andExpect(status().isCreated());

        // Validate the Beneficio in the database
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBeneficio() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);
        int databaseSizeBeforeDelete = beneficioRepository.findAll().size();

        // Get the beneficio
        restBeneficioMockMvc.perform(delete("/api/beneficios/{id}", beneficio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
