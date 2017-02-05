package com.lukaklacar.codesnip.web.rest;

import com.lukaklacar.codesnip.CodeSnipApp;

import com.lukaklacar.codesnip.domain.Snippet;
import com.lukaklacar.codesnip.repository.SnippetRepository;
import com.lukaklacar.codesnip.service.SnippetService;
import com.lukaklacar.codesnip.service.dto.SnippetDTO;
import com.lukaklacar.codesnip.service.mapper.SnippetMapper;

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
 * Test class for the SnippetResource REST controller.
 *
 * @see SnippetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeSnipApp.class)
public class SnippetResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SNIPPET = "AAAAAAAAAA";
    private static final String UPDATED_SNIPPET = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION_IN_MINUTES = 1;
    private static final Integer UPDATED_DURATION_IN_MINUTES = 2;

    private static final Boolean DEFAULT_COMMENTS_BLOCKED = false;
    private static final Boolean UPDATED_COMMENTS_BLOCKED = true;

    @Autowired
    private SnippetRepository snippetRepository;

    @Autowired
    private SnippetMapper snippetMapper;

    @Autowired
    private SnippetService snippetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restSnippetMockMvc;

    private Snippet snippet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SnippetResource snippetResource = new SnippetResource(snippetService);
        this.restSnippetMockMvc = MockMvcBuilders.standaloneSetup(snippetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Snippet createEntity(EntityManager em) {
        Snippet snippet = new Snippet()
                .description(DEFAULT_DESCRIPTION)
                .snippet(DEFAULT_SNIPPET)
                .url(DEFAULT_URL)
                .durationInMinutes(DEFAULT_DURATION_IN_MINUTES)
                .commentsBlocked(DEFAULT_COMMENTS_BLOCKED);
        return snippet;
    }

    @Before
    public void initTest() {
        snippet = createEntity(em);
    }

    @Test
    @Transactional
    public void createSnippet() throws Exception {
        int databaseSizeBeforeCreate = snippetRepository.findAll().size();

        // Create the Snippet
        SnippetDTO snippetDTO = snippetMapper.snippetToSnippetDTO(snippet);

        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snippetDTO)))
            .andExpect(status().isCreated());

        // Validate the Snippet in the database
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeCreate + 1);
        Snippet testSnippet = snippetList.get(snippetList.size() - 1);
        assertThat(testSnippet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSnippet.getSnippet()).isEqualTo(DEFAULT_SNIPPET);
        assertThat(testSnippet.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSnippet.getDurationInMinutes()).isEqualTo(DEFAULT_DURATION_IN_MINUTES);
        assertThat(testSnippet.isCommentsBlocked()).isEqualTo(DEFAULT_COMMENTS_BLOCKED);
    }

    @Test
    @Transactional
    public void createSnippetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = snippetRepository.findAll().size();

        // Create the Snippet with an existing ID
        Snippet existingSnippet = new Snippet();
        existingSnippet.setId(1L);
        SnippetDTO existingSnippetDTO = snippetMapper.snippetToSnippetDTO(existingSnippet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSnippetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSnippetIsRequired() throws Exception {
        int databaseSizeBeforeTest = snippetRepository.findAll().size();
        // set the field null
        snippet.setSnippet(null);

        // Create the Snippet, which fails.
        SnippetDTO snippetDTO = snippetMapper.snippetToSnippetDTO(snippet);

        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snippetDTO)))
            .andExpect(status().isBadRequest());

        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationInMinutesIsRequired() throws Exception {
        int databaseSizeBeforeTest = snippetRepository.findAll().size();
        // set the field null
        snippet.setDurationInMinutes(null);

        // Create the Snippet, which fails.
        SnippetDTO snippetDTO = snippetMapper.snippetToSnippetDTO(snippet);

        restSnippetMockMvc.perform(post("/api/snippets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snippetDTO)))
            .andExpect(status().isBadRequest());

        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSnippets() throws Exception {
        // Initialize the database
        snippetRepository.saveAndFlush(snippet);

        // Get all the snippetList
        restSnippetMockMvc.perform(get("/api/snippets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(snippet.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].snippet").value(hasItem(DEFAULT_SNIPPET.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].durationInMinutes").value(hasItem(DEFAULT_DURATION_IN_MINUTES)))
            .andExpect(jsonPath("$.[*].commentsBlocked").value(hasItem(DEFAULT_COMMENTS_BLOCKED.booleanValue())));
    }

    @Test
    @Transactional
    public void getSnippet() throws Exception {
        // Initialize the database
        snippetRepository.saveAndFlush(snippet);

        // Get the snippet
        restSnippetMockMvc.perform(get("/api/snippets/{id}", snippet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(snippet.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.snippet").value(DEFAULT_SNIPPET.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.durationInMinutes").value(DEFAULT_DURATION_IN_MINUTES))
            .andExpect(jsonPath("$.commentsBlocked").value(DEFAULT_COMMENTS_BLOCKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSnippet() throws Exception {
        // Get the snippet
        restSnippetMockMvc.perform(get("/api/snippets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSnippet() throws Exception {
        // Initialize the database
        snippetRepository.saveAndFlush(snippet);
        int databaseSizeBeforeUpdate = snippetRepository.findAll().size();

        // Update the snippet
        Snippet updatedSnippet = snippetRepository.findOne(snippet.getId());
        updatedSnippet
                .description(UPDATED_DESCRIPTION)
                .snippet(UPDATED_SNIPPET)
                .url(UPDATED_URL)
                .durationInMinutes(UPDATED_DURATION_IN_MINUTES)
                .commentsBlocked(UPDATED_COMMENTS_BLOCKED);
        SnippetDTO snippetDTO = snippetMapper.snippetToSnippetDTO(updatedSnippet);

        restSnippetMockMvc.perform(put("/api/snippets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snippetDTO)))
            .andExpect(status().isOk());

        // Validate the Snippet in the database
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeUpdate);
        Snippet testSnippet = snippetList.get(snippetList.size() - 1);
        assertThat(testSnippet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSnippet.getSnippet()).isEqualTo(UPDATED_SNIPPET);
        assertThat(testSnippet.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSnippet.getDurationInMinutes()).isEqualTo(UPDATED_DURATION_IN_MINUTES);
        assertThat(testSnippet.isCommentsBlocked()).isEqualTo(UPDATED_COMMENTS_BLOCKED);
    }

    @Test
    @Transactional
    public void updateNonExistingSnippet() throws Exception {
        int databaseSizeBeforeUpdate = snippetRepository.findAll().size();

        // Create the Snippet
        SnippetDTO snippetDTO = snippetMapper.snippetToSnippetDTO(snippet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSnippetMockMvc.perform(put("/api/snippets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(snippetDTO)))
            .andExpect(status().isCreated());

        // Validate the Snippet in the database
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSnippet() throws Exception {
        // Initialize the database
        snippetRepository.saveAndFlush(snippet);
        int databaseSizeBeforeDelete = snippetRepository.findAll().size();

        // Get the snippet
        restSnippetMockMvc.perform(delete("/api/snippets/{id}", snippet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Snippet> snippetList = snippetRepository.findAll();
        assertThat(snippetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Snippet.class);
    }
}
