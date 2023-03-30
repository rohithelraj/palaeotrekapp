package com.elraj.palaeotrek.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.elraj.palaeotrek.IntegrationTest;
import com.elraj.palaeotrek.domain.Hike;
import com.elraj.palaeotrek.repository.HikeRepository;
import com.elraj.palaeotrek.service.dto.HikeDTO;
import com.elraj.palaeotrek.service.mapper.HikeMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link HikeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HikeResourceIT {

    private static final LocalDate DEFAULT_HIKE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HIKE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KOMOOT_MAP = "AAAAAAAAAA";
    private static final String UPDATED_KOMOOT_MAP = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_1 = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_2 = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TRAIN_CONNECTION = "AAAAAAAAAA";
    private static final String UPDATED_TRAIN_CONNECTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_DATA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_BLOB_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/hikes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HikeRepository hikeRepository;

    @Autowired
    private HikeMapper hikeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHikeMockMvc;

    private Hike hike;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hike createEntity(EntityManager em) {
        Hike hike = new Hike()
            .hikeDate(DEFAULT_HIKE_DATE)
            .komootMap(DEFAULT_KOMOOT_MAP)
            .description1(DEFAULT_DESCRIPTION_1)
            .description2(DEFAULT_DESCRIPTION_2)
            .trainConnection(DEFAULT_TRAIN_CONNECTION)
            .imageData(DEFAULT_IMAGE_DATA)
            .imageBlob(DEFAULT_IMAGE_BLOB)
            .imageBlobContentType(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        return hike;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hike createUpdatedEntity(EntityManager em) {
        Hike hike = new Hike()
            .hikeDate(UPDATED_HIKE_DATE)
            .komootMap(UPDATED_KOMOOT_MAP)
            .description1(UPDATED_DESCRIPTION_1)
            .description2(UPDATED_DESCRIPTION_2)
            .trainConnection(UPDATED_TRAIN_CONNECTION)
            .imageData(UPDATED_IMAGE_DATA)
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        return hike;
    }

    @BeforeEach
    public void initTest() {
        hike = createEntity(em);
    }

    @Test
    @Transactional
    void createHike() throws Exception {
        int databaseSizeBeforeCreate = hikeRepository.findAll().size();
        // Create the Hike
        HikeDTO hikeDTO = hikeMapper.toDto(hike);
        restHikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hikeDTO)))
            .andExpect(status().isCreated());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeCreate + 1);
        Hike testHike = hikeList.get(hikeList.size() - 1);
        assertThat(testHike.getHikeDate()).isEqualTo(DEFAULT_HIKE_DATE);
        assertThat(testHike.getKomootMap()).isEqualTo(DEFAULT_KOMOOT_MAP);
        assertThat(testHike.getDescription1()).isEqualTo(DEFAULT_DESCRIPTION_1);
        assertThat(testHike.getDescription2()).isEqualTo(DEFAULT_DESCRIPTION_2);
        assertThat(testHike.getTrainConnection()).isEqualTo(DEFAULT_TRAIN_CONNECTION);
        assertThat(testHike.getImageData()).isEqualTo(DEFAULT_IMAGE_DATA);
        assertThat(testHike.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testHike.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createHikeWithExistingId() throws Exception {
        // Create the Hike with an existing ID
        hike.setId(1L);
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        int databaseSizeBeforeCreate = hikeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hikeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHikeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hikeRepository.findAll().size();
        // set the field null
        hike.setHikeDate(null);

        // Create the Hike, which fails.
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        restHikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hikeDTO)))
            .andExpect(status().isBadRequest());

        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKomootMapIsRequired() throws Exception {
        int databaseSizeBeforeTest = hikeRepository.findAll().size();
        // set the field null
        hike.setKomootMap(null);

        // Create the Hike, which fails.
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        restHikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hikeDTO)))
            .andExpect(status().isBadRequest());

        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrainConnectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = hikeRepository.findAll().size();
        // set the field null
        hike.setTrainConnection(null);

        // Create the Hike, which fails.
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        restHikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hikeDTO)))
            .andExpect(status().isBadRequest());

        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHikes() throws Exception {
        // Initialize the database
        hikeRepository.saveAndFlush(hike);

        // Get all the hikeList
        restHikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hike.getId().intValue())))
            .andExpect(jsonPath("$.[*].hikeDate").value(hasItem(DEFAULT_HIKE_DATE.toString())))
            .andExpect(jsonPath("$.[*].komootMap").value(hasItem(DEFAULT_KOMOOT_MAP)))
            .andExpect(jsonPath("$.[*].description1").value(hasItem(DEFAULT_DESCRIPTION_1)))
            .andExpect(jsonPath("$.[*].description2").value(hasItem(DEFAULT_DESCRIPTION_2)))
            .andExpect(jsonPath("$.[*].trainConnection").value(hasItem(DEFAULT_TRAIN_CONNECTION)))
            .andExpect(jsonPath("$.[*].imageData").value(hasItem(DEFAULT_IMAGE_DATA)))
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))));
    }

    @Test
    @Transactional
    void getHike() throws Exception {
        // Initialize the database
        hikeRepository.saveAndFlush(hike);

        // Get the hike
        restHikeMockMvc
            .perform(get(ENTITY_API_URL_ID, hike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hike.getId().intValue()))
            .andExpect(jsonPath("$.hikeDate").value(DEFAULT_HIKE_DATE.toString()))
            .andExpect(jsonPath("$.komootMap").value(DEFAULT_KOMOOT_MAP))
            .andExpect(jsonPath("$.description1").value(DEFAULT_DESCRIPTION_1))
            .andExpect(jsonPath("$.description2").value(DEFAULT_DESCRIPTION_2))
            .andExpect(jsonPath("$.trainConnection").value(DEFAULT_TRAIN_CONNECTION))
            .andExpect(jsonPath("$.imageData").value(DEFAULT_IMAGE_DATA))
            .andExpect(jsonPath("$.imageBlobContentType").value(DEFAULT_IMAGE_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB)));
    }

    @Test
    @Transactional
    void getNonExistingHike() throws Exception {
        // Get the hike
        restHikeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHike() throws Exception {
        // Initialize the database
        hikeRepository.saveAndFlush(hike);

        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();

        // Update the hike
        Hike updatedHike = hikeRepository.findById(hike.getId()).get();
        // Disconnect from session so that the updates on updatedHike are not directly saved in db
        em.detach(updatedHike);
        updatedHike
            .hikeDate(UPDATED_HIKE_DATE)
            .komootMap(UPDATED_KOMOOT_MAP)
            .description1(UPDATED_DESCRIPTION_1)
            .description2(UPDATED_DESCRIPTION_2)
            .trainConnection(UPDATED_TRAIN_CONNECTION)
            .imageData(UPDATED_IMAGE_DATA)
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        HikeDTO hikeDTO = hikeMapper.toDto(updatedHike);

        restHikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hikeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hikeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
        Hike testHike = hikeList.get(hikeList.size() - 1);
        assertThat(testHike.getHikeDate()).isEqualTo(UPDATED_HIKE_DATE);
        assertThat(testHike.getKomootMap()).isEqualTo(UPDATED_KOMOOT_MAP);
        assertThat(testHike.getDescription1()).isEqualTo(UPDATED_DESCRIPTION_1);
        assertThat(testHike.getDescription2()).isEqualTo(UPDATED_DESCRIPTION_2);
        assertThat(testHike.getTrainConnection()).isEqualTo(UPDATED_TRAIN_CONNECTION);
        assertThat(testHike.getImageData()).isEqualTo(UPDATED_IMAGE_DATA);
        assertThat(testHike.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testHike.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingHike() throws Exception {
        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();
        hike.setId(count.incrementAndGet());

        // Create the Hike
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hikeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHike() throws Exception {
        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();
        hike.setId(count.incrementAndGet());

        // Create the Hike
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHike() throws Exception {
        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();
        hike.setId(count.incrementAndGet());

        // Create the Hike
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHikeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hikeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHikeWithPatch() throws Exception {
        // Initialize the database
        hikeRepository.saveAndFlush(hike);

        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();

        // Update the hike using partial update
        Hike partialUpdatedHike = new Hike();
        partialUpdatedHike.setId(hike.getId());

        partialUpdatedHike
            .description2(UPDATED_DESCRIPTION_2)
            .trainConnection(UPDATED_TRAIN_CONNECTION)
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE);

        restHikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHike))
            )
            .andExpect(status().isOk());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
        Hike testHike = hikeList.get(hikeList.size() - 1);
        assertThat(testHike.getHikeDate()).isEqualTo(DEFAULT_HIKE_DATE);
        assertThat(testHike.getKomootMap()).isEqualTo(DEFAULT_KOMOOT_MAP);
        assertThat(testHike.getDescription1()).isEqualTo(DEFAULT_DESCRIPTION_1);
        assertThat(testHike.getDescription2()).isEqualTo(UPDATED_DESCRIPTION_2);
        assertThat(testHike.getTrainConnection()).isEqualTo(UPDATED_TRAIN_CONNECTION);
        assertThat(testHike.getImageData()).isEqualTo(DEFAULT_IMAGE_DATA);
        assertThat(testHike.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testHike.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateHikeWithPatch() throws Exception {
        // Initialize the database
        hikeRepository.saveAndFlush(hike);

        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();

        // Update the hike using partial update
        Hike partialUpdatedHike = new Hike();
        partialUpdatedHike.setId(hike.getId());

        partialUpdatedHike
            .hikeDate(UPDATED_HIKE_DATE)
            .komootMap(UPDATED_KOMOOT_MAP)
            .description1(UPDATED_DESCRIPTION_1)
            .description2(UPDATED_DESCRIPTION_2)
            .trainConnection(UPDATED_TRAIN_CONNECTION)
            .imageData(UPDATED_IMAGE_DATA)
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE);

        restHikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHike))
            )
            .andExpect(status().isOk());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
        Hike testHike = hikeList.get(hikeList.size() - 1);
        assertThat(testHike.getHikeDate()).isEqualTo(UPDATED_HIKE_DATE);
        assertThat(testHike.getKomootMap()).isEqualTo(UPDATED_KOMOOT_MAP);
        assertThat(testHike.getDescription1()).isEqualTo(UPDATED_DESCRIPTION_1);
        assertThat(testHike.getDescription2()).isEqualTo(UPDATED_DESCRIPTION_2);
        assertThat(testHike.getTrainConnection()).isEqualTo(UPDATED_TRAIN_CONNECTION);
        assertThat(testHike.getImageData()).isEqualTo(UPDATED_IMAGE_DATA);
        assertThat(testHike.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testHike.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingHike() throws Exception {
        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();
        hike.setId(count.incrementAndGet());

        // Create the Hike
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hikeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHike() throws Exception {
        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();
        hike.setId(count.incrementAndGet());

        // Create the Hike
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHike() throws Exception {
        int databaseSizeBeforeUpdate = hikeRepository.findAll().size();
        hike.setId(count.incrementAndGet());

        // Create the Hike
        HikeDTO hikeDTO = hikeMapper.toDto(hike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHikeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hikeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hike in the database
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHike() throws Exception {
        // Initialize the database
        hikeRepository.saveAndFlush(hike);

        int databaseSizeBeforeDelete = hikeRepository.findAll().size();

        // Delete the hike
        restHikeMockMvc
            .perform(delete(ENTITY_API_URL_ID, hike.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hike> hikeList = hikeRepository.findAll();
        assertThat(hikeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
