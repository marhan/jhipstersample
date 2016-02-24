package com.jhipster.sample.web.rest;

import com.jhipster.sample.Application;
import com.jhipster.sample.domain.Setting;
import com.jhipster.sample.repository.SettingRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.sample.domain.enumeration.WeightUnit;

/**
 * Test class for the SettingResource REST controller.
 *
 * @see SettingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SettingResourceIntTest {


    private static final Integer DEFAULT_WEEKLY_GOAL = 10;
    private static final Integer UPDATED_WEEKLY_GOAL = 11;
    
    private static final WeightUnit DEFAULT_WEIGHT_UNIT = WeightUnit.KG;
    private static final WeightUnit UPDATED_WEIGHT_UNIT = WeightUnit.LB;

    @Inject
    private SettingRepository settingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSettingMockMvc;

    private Setting setting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SettingResource settingResource = new SettingResource();
        ReflectionTestUtils.setField(settingResource, "settingRepository", settingRepository);
        this.restSettingMockMvc = MockMvcBuilders.standaloneSetup(settingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        setting = new Setting();
        setting.setWeeklyGoal(DEFAULT_WEEKLY_GOAL);
        setting.setWeightUnit(DEFAULT_WEIGHT_UNIT);
    }

    @Test
    @Transactional
    public void createSetting() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Create the Setting

        restSettingMockMvc.perform(post("/api/settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setting)))
                .andExpect(status().isCreated());

        // Validate the Setting in the database
        List<Setting> settings = settingRepository.findAll();
        assertThat(settings).hasSize(databaseSizeBeforeCreate + 1);
        Setting testSetting = settings.get(settings.size() - 1);
        assertThat(testSetting.getWeeklyGoal()).isEqualTo(DEFAULT_WEEKLY_GOAL);
        assertThat(testSetting.getWeightUnit()).isEqualTo(DEFAULT_WEIGHT_UNIT);
    }

    @Test
    @Transactional
    public void checkWeeklyGoalIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingRepository.findAll().size();
        // set the field null
        setting.setWeeklyGoal(null);

        // Create the Setting, which fails.

        restSettingMockMvc.perform(post("/api/settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setting)))
                .andExpect(status().isBadRequest());

        List<Setting> settings = settingRepository.findAll();
        assertThat(settings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingRepository.findAll().size();
        // set the field null
        setting.setWeightUnit(null);

        // Create the Setting, which fails.

        restSettingMockMvc.perform(post("/api/settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setting)))
                .andExpect(status().isBadRequest());

        List<Setting> settings = settingRepository.findAll();
        assertThat(settings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSettings() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settings
        restSettingMockMvc.perform(get("/api/settings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(setting.getId().intValue())))
                .andExpect(jsonPath("$.[*].weeklyGoal").value(hasItem(DEFAULT_WEEKLY_GOAL)))
                .andExpect(jsonPath("$.[*].weightUnit").value(hasItem(DEFAULT_WEIGHT_UNIT.toString())));
    }

    @Test
    @Transactional
    public void getSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", setting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(setting.getId().intValue()))
            .andExpect(jsonPath("$.weeklyGoal").value(DEFAULT_WEEKLY_GOAL))
            .andExpect(jsonPath("$.weightUnit").value(DEFAULT_WEIGHT_UNIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSetting() throws Exception {
        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

		int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting
        setting.setWeeklyGoal(UPDATED_WEEKLY_GOAL);
        setting.setWeightUnit(UPDATED_WEIGHT_UNIT);

        restSettingMockMvc.perform(put("/api/settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setting)))
                .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settings = settingRepository.findAll();
        assertThat(settings).hasSize(databaseSizeBeforeUpdate);
        Setting testSetting = settings.get(settings.size() - 1);
        assertThat(testSetting.getWeeklyGoal()).isEqualTo(UPDATED_WEEKLY_GOAL);
        assertThat(testSetting.getWeightUnit()).isEqualTo(UPDATED_WEIGHT_UNIT);
    }

    @Test
    @Transactional
    public void deleteSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

		int databaseSizeBeforeDelete = settingRepository.findAll().size();

        // Get the setting
        restSettingMockMvc.perform(delete("/api/settings/{id}", setting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Setting> settings = settingRepository.findAll();
        assertThat(settings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
