package com.elraj.palaeotrek.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.elraj.palaeotrek.IntegrationTest;
import com.elraj.palaeotrek.domain.PdfData;
import com.elraj.palaeotrek.domain.enumeration.DetailedType;
import com.elraj.palaeotrek.domain.enumeration.DnaType;
import com.elraj.palaeotrek.repository.PdfDataRepository;
import com.elraj.palaeotrek.service.dto.PdfDataDTO;
import com.elraj.palaeotrek.service.mapper.PdfDataMapper;
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
 * Integration tests for the {@link PdfDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PdfDataResourceIT {

    private static final DnaType DEFAULT_DNA_TYPE = DnaType.MTDNA;
    private static final DnaType UPDATED_DNA_TYPE = DnaType.AUTOSOMAL;

    private static final DetailedType DEFAULT_DETAILED_TYPE = DetailedType.MAPS;
    private static final DetailedType UPDATED_DETAILED_TYPE = DetailedType.TEMPERATURE;

    private static final String DEFAULT_PDF_ID = "AAAAAAAAAA";
    private static final String UPDATED_PDF_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PDF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PDF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PDF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PDF_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/pdf-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PdfDataRepository pdfDataRepository;

    @Autowired
    private PdfDataMapper pdfDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPdfDataMockMvc;

    private PdfData pdfData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PdfData createEntity(EntityManager em) {
        PdfData pdfData = new PdfData()
            .dnaType(DEFAULT_DNA_TYPE)
            .detailedType(DEFAULT_DETAILED_TYPE)
            .pdfId(DEFAULT_PDF_ID)
            .dateOfCreation(DEFAULT_DATE_OF_CREATION)
            .userId(DEFAULT_USER_ID)
            .pdf(DEFAULT_PDF)
            .pdfContentType(DEFAULT_PDF_CONTENT_TYPE);
        return pdfData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PdfData createUpdatedEntity(EntityManager em) {
        PdfData pdfData = new PdfData()
            .dnaType(UPDATED_DNA_TYPE)
            .detailedType(UPDATED_DETAILED_TYPE)
            .pdfId(UPDATED_PDF_ID)
            .dateOfCreation(UPDATED_DATE_OF_CREATION)
            .userId(UPDATED_USER_ID)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE);
        return pdfData;
    }

    @BeforeEach
    public void initTest() {
        pdfData = createEntity(em);
    }

    @Test
    @Transactional
    void createPdfData() throws Exception {
        int databaseSizeBeforeCreate = pdfDataRepository.findAll().size();
        // Create the PdfData
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);
        restPdfDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pdfDataDTO)))
            .andExpect(status().isCreated());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeCreate + 1);
        PdfData testPdfData = pdfDataList.get(pdfDataList.size() - 1);
        assertThat(testPdfData.getDnaType()).isEqualTo(DEFAULT_DNA_TYPE);
        assertThat(testPdfData.getDetailedType()).isEqualTo(DEFAULT_DETAILED_TYPE);
        assertThat(testPdfData.getPdfId()).isEqualTo(DEFAULT_PDF_ID);
        assertThat(testPdfData.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);
        assertThat(testPdfData.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPdfData.getPdf()).isEqualTo(DEFAULT_PDF);
        assertThat(testPdfData.getPdfContentType()).isEqualTo(DEFAULT_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPdfDataWithExistingId() throws Exception {
        // Create the PdfData with an existing ID
        pdfData.setId(1L);
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        int databaseSizeBeforeCreate = pdfDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPdfDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pdfDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDnaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdfDataRepository.findAll().size();
        // set the field null
        pdfData.setDnaType(null);

        // Create the PdfData, which fails.
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        restPdfDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pdfDataDTO)))
            .andExpect(status().isBadRequest());

        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDetailedTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdfDataRepository.findAll().size();
        // set the field null
        pdfData.setDetailedType(null);

        // Create the PdfData, which fails.
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        restPdfDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pdfDataDTO)))
            .andExpect(status().isBadRequest());

        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPdfIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdfDataRepository.findAll().size();
        // set the field null
        pdfData.setPdfId(null);

        // Create the PdfData, which fails.
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        restPdfDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pdfDataDTO)))
            .andExpect(status().isBadRequest());

        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdfDataRepository.findAll().size();
        // set the field null
        pdfData.setDateOfCreation(null);

        // Create the PdfData, which fails.
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        restPdfDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pdfDataDTO)))
            .andExpect(status().isBadRequest());

        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdfDataRepository.findAll().size();
        // set the field null
        pdfData.setUserId(null);

        // Create the PdfData, which fails.
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        restPdfDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pdfDataDTO)))
            .andExpect(status().isBadRequest());

        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPdfData() throws Exception {
        // Initialize the database
        pdfDataRepository.saveAndFlush(pdfData);

        // Get all the pdfDataList
        restPdfDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pdfData.getId().intValue())))
            .andExpect(jsonPath("$.[*].dnaType").value(hasItem(DEFAULT_DNA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].detailedType").value(hasItem(DEFAULT_DETAILED_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pdfId").value(hasItem(DEFAULT_PDF_ID)))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(DEFAULT_DATE_OF_CREATION.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].pdfContentType").value(hasItem(DEFAULT_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_PDF))));
    }

    @Test
    @Transactional
    void getPdfData() throws Exception {
        // Initialize the database
        pdfDataRepository.saveAndFlush(pdfData);

        // Get the pdfData
        restPdfDataMockMvc
            .perform(get(ENTITY_API_URL_ID, pdfData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pdfData.getId().intValue()))
            .andExpect(jsonPath("$.dnaType").value(DEFAULT_DNA_TYPE.toString()))
            .andExpect(jsonPath("$.detailedType").value(DEFAULT_DETAILED_TYPE.toString()))
            .andExpect(jsonPath("$.pdfId").value(DEFAULT_PDF_ID))
            .andExpect(jsonPath("$.dateOfCreation").value(DEFAULT_DATE_OF_CREATION.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.pdfContentType").value(DEFAULT_PDF_CONTENT_TYPE))
            .andExpect(jsonPath("$.pdf").value(Base64Utils.encodeToString(DEFAULT_PDF)));
    }

    @Test
    @Transactional
    void getNonExistingPdfData() throws Exception {
        // Get the pdfData
        restPdfDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPdfData() throws Exception {
        // Initialize the database
        pdfDataRepository.saveAndFlush(pdfData);

        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();

        // Update the pdfData
        PdfData updatedPdfData = pdfDataRepository.findById(pdfData.getId()).get();
        // Disconnect from session so that the updates on updatedPdfData are not directly saved in db
        em.detach(updatedPdfData);
        updatedPdfData
            .dnaType(UPDATED_DNA_TYPE)
            .detailedType(UPDATED_DETAILED_TYPE)
            .pdfId(UPDATED_PDF_ID)
            .dateOfCreation(UPDATED_DATE_OF_CREATION)
            .userId(UPDATED_USER_ID)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE);
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(updatedPdfData);

        restPdfDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pdfDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pdfDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
        PdfData testPdfData = pdfDataList.get(pdfDataList.size() - 1);
        assertThat(testPdfData.getDnaType()).isEqualTo(UPDATED_DNA_TYPE);
        assertThat(testPdfData.getDetailedType()).isEqualTo(UPDATED_DETAILED_TYPE);
        assertThat(testPdfData.getPdfId()).isEqualTo(UPDATED_PDF_ID);
        assertThat(testPdfData.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);
        assertThat(testPdfData.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPdfData.getPdf()).isEqualTo(UPDATED_PDF);
        assertThat(testPdfData.getPdfContentType()).isEqualTo(UPDATED_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPdfData() throws Exception {
        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();
        pdfData.setId(count.incrementAndGet());

        // Create the PdfData
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPdfDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pdfDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pdfDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPdfData() throws Exception {
        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();
        pdfData.setId(count.incrementAndGet());

        // Create the PdfData
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPdfDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pdfDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPdfData() throws Exception {
        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();
        pdfData.setId(count.incrementAndGet());

        // Create the PdfData
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPdfDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pdfDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePdfDataWithPatch() throws Exception {
        // Initialize the database
        pdfDataRepository.saveAndFlush(pdfData);

        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();

        // Update the pdfData using partial update
        PdfData partialUpdatedPdfData = new PdfData();
        partialUpdatedPdfData.setId(pdfData.getId());

        partialUpdatedPdfData.detailedType(UPDATED_DETAILED_TYPE).pdfId(UPDATED_PDF_ID);

        restPdfDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPdfData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPdfData))
            )
            .andExpect(status().isOk());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
        PdfData testPdfData = pdfDataList.get(pdfDataList.size() - 1);
        assertThat(testPdfData.getDnaType()).isEqualTo(DEFAULT_DNA_TYPE);
        assertThat(testPdfData.getDetailedType()).isEqualTo(UPDATED_DETAILED_TYPE);
        assertThat(testPdfData.getPdfId()).isEqualTo(UPDATED_PDF_ID);
        assertThat(testPdfData.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);
        assertThat(testPdfData.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPdfData.getPdf()).isEqualTo(DEFAULT_PDF);
        assertThat(testPdfData.getPdfContentType()).isEqualTo(DEFAULT_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePdfDataWithPatch() throws Exception {
        // Initialize the database
        pdfDataRepository.saveAndFlush(pdfData);

        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();

        // Update the pdfData using partial update
        PdfData partialUpdatedPdfData = new PdfData();
        partialUpdatedPdfData.setId(pdfData.getId());

        partialUpdatedPdfData
            .dnaType(UPDATED_DNA_TYPE)
            .detailedType(UPDATED_DETAILED_TYPE)
            .pdfId(UPDATED_PDF_ID)
            .dateOfCreation(UPDATED_DATE_OF_CREATION)
            .userId(UPDATED_USER_ID)
            .pdf(UPDATED_PDF)
            .pdfContentType(UPDATED_PDF_CONTENT_TYPE);

        restPdfDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPdfData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPdfData))
            )
            .andExpect(status().isOk());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
        PdfData testPdfData = pdfDataList.get(pdfDataList.size() - 1);
        assertThat(testPdfData.getDnaType()).isEqualTo(UPDATED_DNA_TYPE);
        assertThat(testPdfData.getDetailedType()).isEqualTo(UPDATED_DETAILED_TYPE);
        assertThat(testPdfData.getPdfId()).isEqualTo(UPDATED_PDF_ID);
        assertThat(testPdfData.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);
        assertThat(testPdfData.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPdfData.getPdf()).isEqualTo(UPDATED_PDF);
        assertThat(testPdfData.getPdfContentType()).isEqualTo(UPDATED_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPdfData() throws Exception {
        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();
        pdfData.setId(count.incrementAndGet());

        // Create the PdfData
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPdfDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pdfDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pdfDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPdfData() throws Exception {
        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();
        pdfData.setId(count.incrementAndGet());

        // Create the PdfData
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPdfDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pdfDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPdfData() throws Exception {
        int databaseSizeBeforeUpdate = pdfDataRepository.findAll().size();
        pdfData.setId(count.incrementAndGet());

        // Create the PdfData
        PdfDataDTO pdfDataDTO = pdfDataMapper.toDto(pdfData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPdfDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pdfDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PdfData in the database
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePdfData() throws Exception {
        // Initialize the database
        pdfDataRepository.saveAndFlush(pdfData);

        int databaseSizeBeforeDelete = pdfDataRepository.findAll().size();

        // Delete the pdfData
        restPdfDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, pdfData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PdfData> pdfDataList = pdfDataRepository.findAll();
        assertThat(pdfDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
