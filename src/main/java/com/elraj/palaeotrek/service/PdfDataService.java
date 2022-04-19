package com.elraj.palaeotrek.service;

import com.elraj.palaeotrek.domain.PdfData;
import com.elraj.palaeotrek.repository.PdfDataRepository;
import com.elraj.palaeotrek.service.dto.PdfDataDTO;
import com.elraj.palaeotrek.service.mapper.PdfDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PdfData}.
 */
@Service
@Transactional
public class PdfDataService {

    private final Logger log = LoggerFactory.getLogger(PdfDataService.class);

    private final PdfDataRepository pdfDataRepository;

    private final PdfDataMapper pdfDataMapper;

    public PdfDataService(PdfDataRepository pdfDataRepository, PdfDataMapper pdfDataMapper) {
        this.pdfDataRepository = pdfDataRepository;
        this.pdfDataMapper = pdfDataMapper;
    }

    /**
     * Save a pdfData.
     *
     * @param pdfDataDTO the entity to save.
     * @return the persisted entity.
     */
    public PdfDataDTO save(PdfDataDTO pdfDataDTO) {
        log.debug("Request to save PdfData : {}", pdfDataDTO);
        PdfData pdfData = pdfDataMapper.toEntity(pdfDataDTO);
        pdfData = pdfDataRepository.save(pdfData);
        return pdfDataMapper.toDto(pdfData);
    }

    /**
     * Update a pdfData.
     *
     * @param pdfDataDTO the entity to save.
     * @return the persisted entity.
     */
    public PdfDataDTO update(PdfDataDTO pdfDataDTO) {
        log.debug("Request to save PdfData : {}", pdfDataDTO);
        PdfData pdfData = pdfDataMapper.toEntity(pdfDataDTO);
        pdfData = pdfDataRepository.save(pdfData);
        return pdfDataMapper.toDto(pdfData);
    }

    /**
     * Partially update a pdfData.
     *
     * @param pdfDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PdfDataDTO> partialUpdate(PdfDataDTO pdfDataDTO) {
        log.debug("Request to partially update PdfData : {}", pdfDataDTO);

        return pdfDataRepository
            .findById(pdfDataDTO.getId())
            .map(existingPdfData -> {
                pdfDataMapper.partialUpdate(existingPdfData, pdfDataDTO);

                return existingPdfData;
            })
            .map(pdfDataRepository::save)
            .map(pdfDataMapper::toDto);
    }

    /**
     * Get all the pdfData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PdfDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PdfData");
        return pdfDataRepository.findAll(pageable).map(pdfDataMapper::toDto);
    }

    /**
     * Get one pdfData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PdfDataDTO> findOne(Long id) {
        log.debug("Request to get PdfData : {}", id);
        return pdfDataRepository.findById(id).map(pdfDataMapper::toDto);
    }

    /**
     * Get one pdfData by id.
     *
     * @param pdfId the formatted unique id of the Pdf following syntax <USER_ID>_<DNA_TYPE>_<DETAILED_TYPE>.
     * @param userId the userID of the benutzer
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PdfDataDTO> findByPdfIdAndUserId(String pdfId, String userId) {
        log.debug("Request to get PdfData with pdfId and userId : {}", pdfId,userId);
        return pdfDataRepository.findByPdfIdAndUserId(pdfId,userId).map(pdfDataMapper::toDto);
    }

    /**
     * Delete the pdfData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PdfData : {}", id);
        pdfDataRepository.deleteById(id);
    }
}
