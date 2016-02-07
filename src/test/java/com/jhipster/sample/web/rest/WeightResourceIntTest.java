package com.jhipster.sample.web.rest;

import com.jhipster.sample.Application;
import com.jhipster.sample.domain.Weight;
import com.jhipster.sample.repository.WeightRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the WeightResource REST controller.
 *
 * @see WeightResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class WeightResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_TIME_STR = dateTimeFormatter.format(DEFAULT_DATE_TIME);

    private static final Integer DEFAULT_SYSTOLIC = 1;
    private static final Integer UPDATED_SYSTOLIC = 2;

    private static final Integer DEFAULT_DIASTOLIC = 1;
    private static final Integer UPDATED_DIASTOLIC = 2;

    @Inject
    private WeightRepository weightRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWeightMockMvc;

    private Weight weight;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WeightResource weightResource = new WeightResource();
        ReflectionTestUtils.setField(weightResource, "weightRepository", weightRepository);
        this.restWeightMockMvc = MockMvcBuilders.standaloneSetup(weightResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        weight = new Weight();
        weight.setDateTime(DEFAULT_DATE_TIME);
        weight.setSystolic(DEFAULT_SYSTOLIC);
        weight.setDiastolic(DEFAULT_DIASTOLIC);
    }

    @Test
    @Transactional
    public void createWeight() throws Exception {
        int databaseSizeBeforeCreate = weightRepository.findAll().size();

        // Create the Weight

        restWeightMockMvc.perform(post("/api/weights")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weight)))
                .andExpect(status().isCreated());

        // Validate the Weight in the database
        List<Weight> weights = weightRepository.findAll();
        assertThat(weights).hasSize(databaseSizeBeforeCreate + 1);
        Weight testWeight = weights.get(weights.size() - 1);
        assertThat(testWeight.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testWeight.getSystolic()).isEqualTo(DEFAULT_SYSTOLIC);
        assertThat(testWeight.getDiastolic()).isEqualTo(DEFAULT_DIASTOLIC);
    }

    @Test
    @Transactional
    public void checkDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = weightRepository.findAll().size();
        // set the field null
        weight.setDateTime(null);

        // Create the Weight, which fails.

        restWeightMockMvc.perform(post("/api/weights")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weight)))
                .andExpect(status().isBadRequest());

        List<Weight> weights = weightRepository.findAll();
        assertThat(weights).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSystolicIsRequired() throws Exception {
        int databaseSizeBeforeTest = weightRepository.findAll().size();
        // set the field null
        weight.setSystolic(null);

        // Create the Weight, which fails.

        restWeightMockMvc.perform(post("/api/weights")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weight)))
                .andExpect(status().isBadRequest());

        List<Weight> weights = weightRepository.findAll();
        assertThat(weights).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiastolicIsRequired() throws Exception {
        int databaseSizeBeforeTest = weightRepository.findAll().size();
        // set the field null
        weight.setDiastolic(null);

        // Create the Weight, which fails.

        restWeightMockMvc.perform(post("/api/weights")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weight)))
                .andExpect(status().isBadRequest());

        List<Weight> weights = weightRepository.findAll();
        assertThat(weights).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeights() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weights
        restWeightMockMvc.perform(get("/api/weights?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(weight.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].systolic").value(hasItem(DEFAULT_SYSTOLIC)))
                .andExpect(jsonPath("$.[*].diastolic").value(hasItem(DEFAULT_DIASTOLIC)));
    }

    @Test
    @Transactional
    public void getWeight() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get the weight
        restWeightMockMvc.perform(get("/api/weights/{id}", weight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(weight.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME_STR))
            .andExpect(jsonPath("$.systolic").value(DEFAULT_SYSTOLIC))
            .andExpect(jsonPath("$.diastolic").value(DEFAULT_DIASTOLIC));
    }

    @Test
    @Transactional
    public void getNonExistingWeight() throws Exception {
        // Get the weight
        restWeightMockMvc.perform(get("/api/weights/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeight() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

		int databaseSizeBeforeUpdate = weightRepository.findAll().size();

        // Update the weight
        weight.setDateTime(UPDATED_DATE_TIME);
        weight.setSystolic(UPDATED_SYSTOLIC);
        weight.setDiastolic(UPDATED_DIASTOLIC);

        restWeightMockMvc.perform(put("/api/weights")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weight)))
                .andExpect(status().isOk());

        // Validate the Weight in the database
        List<Weight> weights = weightRepository.findAll();
        assertThat(weights).hasSize(databaseSizeBeforeUpdate);
        Weight testWeight = weights.get(weights.size() - 1);
        assertThat(testWeight.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testWeight.getSystolic()).isEqualTo(UPDATED_SYSTOLIC);
        assertThat(testWeight.getDiastolic()).isEqualTo(UPDATED_DIASTOLIC);
    }

    @Test
    @Transactional
    public void deleteWeight() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

		int databaseSizeBeforeDelete = weightRepository.findAll().size();

        // Get the weight
        restWeightMockMvc.perform(delete("/api/weights/{id}", weight.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Weight> weights = weightRepository.findAll();
        assertThat(weights).hasSize(databaseSizeBeforeDelete - 1);
    }
}
