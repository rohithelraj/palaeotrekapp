package com.elraj.palaeotrek.web.rest;

import com.elraj.palaeotrek.repository.PdfDataRepository;
import com.elraj.palaeotrek.service.PdfDataService;
import com.elraj.palaeotrek.service.dto.PdfDataDTO;
import com.elraj.palaeotrek.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.elraj.palaeotrek.domain.PdfData}.
 */
@RestController
@RequestMapping("/api")
public class PdfDataResource {

    private final Logger log = LoggerFactory.getLogger(PdfDataResource.class);

    private static final String ENTITY_NAME = "pdfData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PdfDataService pdfDataService;

    private final PdfDataRepository pdfDataRepository;

    public PdfDataResource(PdfDataService pdfDataService, PdfDataRepository pdfDataRepository) {
        this.pdfDataService = pdfDataService;
        this.pdfDataRepository = pdfDataRepository;
    }

    /**
     * {@code POST  /pdf-data} : Create a new pdfData.
     *
     * @param pdfDataDTO the pdfDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pdfDataDTO, or with status {@code 400 (Bad Request)} if the pdfData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pdf-data")
    public ResponseEntity<PdfDataDTO> createPdfData(@Valid @RequestBody PdfDataDTO pdfDataDTO) throws URISyntaxException {
        log.debug("REST request to save PdfData : {}", pdfDataDTO);
        if (pdfDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new pdfData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PdfDataDTO result = pdfDataService.save(pdfDataDTO);
        return ResponseEntity
            .created(new URI("/api/pdf-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pdf-data/:id} : Updates an existing pdfData.
     *
     * @param id the id of the pdfDataDTO to save.
     * @param pdfDataDTO the pdfDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pdfDataDTO,
     * or with status {@code 400 (Bad Request)} if the pdfDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pdfDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pdf-data/{id}")
    public ResponseEntity<PdfDataDTO> updatePdfData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PdfDataDTO pdfDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PdfData : {}, {}", id, pdfDataDTO);
        if (pdfDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pdfDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pdfDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PdfDataDTO result = pdfDataService.update(pdfDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pdfDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pdf-data/:id} : Partial updates given fields of an existing pdfData, field will ignore if it is null
     *
     * @param id the id of the pdfDataDTO to save.
     * @param pdfDataDTO the pdfDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pdfDataDTO,
     * or with status {@code 400 (Bad Request)} if the pdfDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pdfDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pdfDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pdf-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PdfDataDTO> partialUpdatePdfData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PdfDataDTO pdfDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PdfData partially : {}, {}", id, pdfDataDTO);
        if (pdfDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pdfDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pdfDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PdfDataDTO> result = pdfDataService.partialUpdate(pdfDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pdfDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pdf-data} : get all the pdfData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pdfData in body.
     */
    @GetMapping("/pdf-data")
    public ResponseEntity<List<PdfDataDTO>> getAllPdfData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PdfData");
        Page<PdfDataDTO> page = pdfDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pdf-data/:id} : get the "id" pdfData.
     *
     * @param id the id of the pdfDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pdfDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pdf-data/{id}")
    public ResponseEntity<PdfDataDTO> getPdfData(@PathVariable Long id) {
        log.debug("REST request to get PdfData : {}", id);
        Optional<PdfDataDTO> pdfDataDTO = pdfDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pdfDataDTO);
    }

    /**
     * {@code DELETE  /pdf-data/:id} : delete the "id" pdfData.
     *
     * @param id the id of the pdfDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pdf-data/{id}")
    public ResponseEntity<Void> deletePdfData(@PathVariable Long id) {
        log.debug("REST request to delete PdfData : {}", id);
        pdfDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
