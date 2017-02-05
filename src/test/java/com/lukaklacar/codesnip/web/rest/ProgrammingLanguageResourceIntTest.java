package com.lukaklacar.codesnip.web.rest;

import com.lukaklacar.codesnip.CodeSnipApp;

import com.lukaklacar.codesnip.domain.ProgrammingLanguage;
import com.lukaklacar.codesnip.repository.ProgrammingLanguageRepository;
import com.lukaklacar.codesnip.service.ProgrammingLanguageService;
import com.lukaklacar.codesnip.service.dto.ProgrammingLanguageDTO;
import com.lukaklacar.codesnip.service.mapper.ProgrammingLanguageMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProgrammingLanguageResource REST controller.
 *
 * @see ProgrammingLanguageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeSnipApp.class)
public class ProgrammingLanguageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    private ProgrammingLanguageMapper programmingLanguageMapper;

    @Autowired
    private ProgrammingLanguageService programmingLanguageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restProgrammingLanguageMockMvc;

    private ProgrammingLanguage programmingLanguage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProgrammingLanguageResource programmingLanguageResource = new ProgrammingLanguageResource(programmingLanguageService);
        this.restProgrammingLanguageMockMvc = MockMvcBuilders.standaloneSetup(programmingLanguageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgrammingLanguage createEntity(EntityManager em) {
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage()
                .name(DEFAULT_NAME);
        return programmingLanguage;
    }

    @Before
    public void initTest() {
        programmingLanguage = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgrammingLanguage() throws Exception {
        int databaseSizeBeforeCreate = programmingLanguageRepository.findAll().size();

        // Create the ProgrammingLanguage
        ProgrammingLanguageDTO programmingLanguageDTO = programmingLanguageMapper.programmingLanguageToProgrammingLanguageDTO(programmingLanguage);

        restProgrammingLanguageMockMvc.perform(post("/api/programming-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmingLanguageDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgrammingLanguage in the database
        List<ProgrammingLanguage> programmingLanguageList = programmingLanguageRepository.findAll();
        assertThat(programmingLanguageList).hasSize(databaseSizeBeforeCreate + 1);
        ProgrammingLanguage testProgrammingLanguage = programmingLanguageList.get(programmingLanguageList.size() - 1);
        assertThat(testProgrammingLanguage.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProgrammingLanguageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programmingLanguageRepository.findAll().size();

        // Create the ProgrammingLanguage with an existing ID
        ProgrammingLanguage existingProgrammingLanguage = new ProgrammingLanguage();
        existingProgrammingLanguage.setId(1L);
        ProgrammingLanguageDTO existingProgrammingLanguageDTO = programmingLanguageMapper.programmingLanguageToProgrammingLanguageDTO(existingProgrammingLanguage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammingLanguageMockMvc.perform(post("/api/programming-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProgrammingLanguageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProgrammingLanguage> programmingLanguageList = programmingLanguageRepository.findAll();
        assertThat(programmingLanguageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmingLanguageRepository.findAll().size();
        // set the field null
        programmingLanguage.setName(null);

        // Create the ProgrammingLanguage, which fails.
        ProgrammingLanguageDTO programmingLanguageDTO = programmingLanguageMapper.programmingLanguageToProgrammingLanguageDTO(programmingLanguage);

        restProgrammingLanguageMockMvc.perform(post("/api/programming-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmingLanguageDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammingLanguage> programmingLanguageList = programmingLanguageRepository.findAll();
        assertThat(programmingLanguageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProgrammingLanguages() throws Exception {
        // Initialize the database
        programmingLanguageRepository.saveAndFlush(programmingLanguage);

        // Get all the programmingLanguageList
        restProgrammingLanguageMockMvc.perform(get("/api/programming-languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programmingLanguage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProgrammingLanguage() throws Exception {
        // Initialize the database
        programmingLanguageRepository.saveAndFlush(programmingLanguage);

        // Get the programmingLanguage
        restProgrammingLanguageMockMvc.perform(get("/api/programming-languages/{id}", programmingLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programmingLanguage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProgrammingLanguage() throws Exception {
        // Get the programmingLanguage
        restProgrammingLanguageMockMvc.perform(get("/api/programming-languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgrammingLanguage() throws Exception {
        // Initialize the database
        programmingLanguageRepository.saveAndFlush(programmingLanguage);
        int databaseSizeBeforeUpdate = programmingLanguageRepository.findAll().size();

        // Update the programmingLanguage
        ProgrammingLanguage updatedProgrammingLanguage = programmingLanguageRepository.findOne(programmingLanguage.getId());
        updatedProgrammingLanguage
                .name(UPDATED_NAME);
        ProgrammingLanguageDTO programmingLanguageDTO = programmingLanguageMapper.programmingLanguageToProgrammingLanguageDTO(updatedProgrammingLanguage);

        restProgrammingLanguageMockMvc.perform(put("/api/programming-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmingLanguageDTO)))
            .andExpect(status().isOk());

        // Validate the ProgrammingLanguage in the database
        List<ProgrammingLanguage> programmingLanguageList = programmingLanguageRepository.findAll();
        assertThat(programmingLanguageList).hasSize(databaseSizeBeforeUpdate);
        ProgrammingLanguage testProgrammingLanguage = programmingLanguageList.get(programmingLanguageList.size() - 1);
        assertThat(testProgrammingLanguage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProgrammingLanguage() throws Exception {
        int databaseSizeBeforeUpdate = programmingLanguageRepository.findAll().size();

        // Create the ProgrammingLanguage
        ProgrammingLanguageDTO programmingLanguageDTO = programmingLanguageMapper.programmingLanguageToProgrammingLanguageDTO(programmingLanguage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProgrammingLanguageMockMvc.perform(put("/api/programming-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmingLanguageDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgrammingLanguage in the database
        List<ProgrammingLanguage> programmingLanguageList = programmingLanguageRepository.findAll();
        assertThat(programmingLanguageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProgrammingLanguage() throws Exception {
        // Initialize the database
        programmingLanguageRepository.saveAndFlush(programmingLanguage);
        int databaseSizeBeforeDelete = programmingLanguageRepository.findAll().size();

        // Get the programmingLanguage
        restProgrammingLanguageMockMvc.perform(delete("/api/programming-languages/{id}", programmingLanguage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProgrammingLanguage> programmingLanguageList = programmingLanguageRepository.findAll();
        assertThat(programmingLanguageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgrammingLanguage.class);
    }
}
