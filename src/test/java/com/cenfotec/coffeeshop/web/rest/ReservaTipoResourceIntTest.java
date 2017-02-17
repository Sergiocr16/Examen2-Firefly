package com.cenfotec.coffeeshop.web.rest;

import com.cenfotec.coffeeshop.CoffeeShopApp;

import com.cenfotec.coffeeshop.domain.ReservaTipo;
import com.cenfotec.coffeeshop.repository.ReservaTipoRepository;
import com.cenfotec.coffeeshop.service.ReservaTipoService;
import com.cenfotec.coffeeshop.service.dto.ReservaTipoDTO;
import com.cenfotec.coffeeshop.service.mapper.ReservaTipoMapper;

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
 * Test class for the ReservaTipoResource REST controller.
 *
 * @see ReservaTipoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoffeeShopApp.class)
public class ReservaTipoResourceIntTest {

    private static final Integer DEFAULT_CANTIDAD = 0;
    private static final Integer UPDATED_CANTIDAD = 1;

    @Inject
    private ReservaTipoRepository reservaTipoRepository;

    @Inject
    private ReservaTipoMapper reservaTipoMapper;

    @Inject
    private ReservaTipoService reservaTipoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReservaTipoMockMvc;

    private ReservaTipo reservaTipo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReservaTipoResource reservaTipoResource = new ReservaTipoResource();
        ReflectionTestUtils.setField(reservaTipoResource, "reservaTipoService", reservaTipoService);
        this.restReservaTipoMockMvc = MockMvcBuilders.standaloneSetup(reservaTipoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservaTipo createEntity(EntityManager em) {
        ReservaTipo reservaTipo = new ReservaTipo()
                .cantidad(DEFAULT_CANTIDAD);
        return reservaTipo;
    }

    @Before
    public void initTest() {
        reservaTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservaTipo() throws Exception {
        int databaseSizeBeforeCreate = reservaTipoRepository.findAll().size();

        // Create the ReservaTipo
        ReservaTipoDTO reservaTipoDTO = reservaTipoMapper.reservaTipoToReservaTipoDTO(reservaTipo);

        restReservaTipoMockMvc.perform(post("/api/reserva-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the ReservaTipo in the database
        List<ReservaTipo> reservaTipoList = reservaTipoRepository.findAll();
        assertThat(reservaTipoList).hasSize(databaseSizeBeforeCreate + 1);
        ReservaTipo testReservaTipo = reservaTipoList.get(reservaTipoList.size() - 1);
        assertThat(testReservaTipo.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createReservaTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservaTipoRepository.findAll().size();

        // Create the ReservaTipo with an existing ID
        ReservaTipo existingReservaTipo = new ReservaTipo();
        existingReservaTipo.setId(1L);
        ReservaTipoDTO existingReservaTipoDTO = reservaTipoMapper.reservaTipoToReservaTipoDTO(existingReservaTipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservaTipoMockMvc.perform(post("/api/reserva-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReservaTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ReservaTipo> reservaTipoList = reservaTipoRepository.findAll();
        assertThat(reservaTipoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservaTipoRepository.findAll().size();
        // set the field null
        reservaTipo.setCantidad(null);

        // Create the ReservaTipo, which fails.
        ReservaTipoDTO reservaTipoDTO = reservaTipoMapper.reservaTipoToReservaTipoDTO(reservaTipo);

        restReservaTipoMockMvc.perform(post("/api/reserva-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaTipoDTO)))
            .andExpect(status().isBadRequest());

        List<ReservaTipo> reservaTipoList = reservaTipoRepository.findAll();
        assertThat(reservaTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReservaTipos() throws Exception {
        // Initialize the database
        reservaTipoRepository.saveAndFlush(reservaTipo);

        // Get all the reservaTipoList
        restReservaTipoMockMvc.perform(get("/api/reserva-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservaTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    public void getReservaTipo() throws Exception {
        // Initialize the database
        reservaTipoRepository.saveAndFlush(reservaTipo);

        // Get the reservaTipo
        restReservaTipoMockMvc.perform(get("/api/reserva-tipos/{id}", reservaTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reservaTipo.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingReservaTipo() throws Exception {
        // Get the reservaTipo
        restReservaTipoMockMvc.perform(get("/api/reserva-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservaTipo() throws Exception {
        // Initialize the database
        reservaTipoRepository.saveAndFlush(reservaTipo);
        int databaseSizeBeforeUpdate = reservaTipoRepository.findAll().size();

        // Update the reservaTipo
        ReservaTipo updatedReservaTipo = reservaTipoRepository.findOne(reservaTipo.getId());
        updatedReservaTipo
                .cantidad(UPDATED_CANTIDAD);
        ReservaTipoDTO reservaTipoDTO = reservaTipoMapper.reservaTipoToReservaTipoDTO(updatedReservaTipo);

        restReservaTipoMockMvc.perform(put("/api/reserva-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaTipoDTO)))
            .andExpect(status().isOk());

        // Validate the ReservaTipo in the database
        List<ReservaTipo> reservaTipoList = reservaTipoRepository.findAll();
        assertThat(reservaTipoList).hasSize(databaseSizeBeforeUpdate);
        ReservaTipo testReservaTipo = reservaTipoList.get(reservaTipoList.size() - 1);
        assertThat(testReservaTipo.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingReservaTipo() throws Exception {
        int databaseSizeBeforeUpdate = reservaTipoRepository.findAll().size();

        // Create the ReservaTipo
        ReservaTipoDTO reservaTipoDTO = reservaTipoMapper.reservaTipoToReservaTipoDTO(reservaTipo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReservaTipoMockMvc.perform(put("/api/reserva-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the ReservaTipo in the database
        List<ReservaTipo> reservaTipoList = reservaTipoRepository.findAll();
        assertThat(reservaTipoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReservaTipo() throws Exception {
        // Initialize the database
        reservaTipoRepository.saveAndFlush(reservaTipo);
        int databaseSizeBeforeDelete = reservaTipoRepository.findAll().size();

        // Get the reservaTipo
        restReservaTipoMockMvc.perform(delete("/api/reserva-tipos/{id}", reservaTipo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReservaTipo> reservaTipoList = reservaTipoRepository.findAll();
        assertThat(reservaTipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
