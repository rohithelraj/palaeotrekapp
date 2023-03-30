package com.elraj.palaeotrek.web.rest;

import com.elraj.palaeotrek.repository.HikeRepository;
import com.elraj.palaeotrek.service.HikeService;
import com.elraj.palaeotrek.service.dto.HikeDTO;
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
 * REST controller for managing {@link com.elraj.palaeotrek.domain.Hike}.
 */
@RestController
@RequestMapping("/api")
public class HikeResource {

    private final Logger log = LoggerFactory.getLogger(HikeResource.class);

    private static final String ENTITY_NAME = "hike";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HikeService hikeService;

    private final HikeRepository hikeRepository;

    public HikeResource(HikeService hikeService, HikeRepository hikeRepository) {
        this.hikeService = hikeService;
        this.hikeRepository = hikeRepository;
    }

    /**
     * {@code POST  /hikes} : Create a new hike.
     *
     * @param hikeDTO the hikeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hikeDTO, or with status {@code 400 (Bad Request)} if the hike has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hikes")
    public ResponseEntity<HikeDTO> createHike(@Valid @RequestBody HikeDTO hikeDTO) throws URISyntaxException {
        log.debug("REST request to save Hike : {}", hikeDTO);
        if (hikeDTO.getId() != null) {
            throw new BadRequestAlertException("A new hike cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HikeDTO result = hikeService.save(hikeDTO);
        return ResponseEntity
            .created(new URI("/api/hikes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hikes/:id} : Updates an existing hike.
     *
     * @param id the id of the hikeDTO to save.
     * @param hikeDTO the hikeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hikeDTO,
     * or with status {@code 400 (Bad Request)} if the hikeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hikeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hikes/{id}")
    public ResponseEntity<HikeDTO> updateHike(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HikeDTO hikeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Hike : {}, {}", id, hikeDTO);
        if (hikeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hikeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hikeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HikeDTO result = hikeService.update(hikeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hikeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hikes/:id} : Partial updates given fields of an existing hike, field will ignore if it is null
     *
     * @param id the id of the hikeDTO to save.
     * @param hikeDTO the hikeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hikeDTO,
     * or with status {@code 400 (Bad Request)} if the hikeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hikeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hikeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hikes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HikeDTO> partialUpdateHike(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HikeDTO hikeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hike partially : {}, {}", id, hikeDTO);
        if (hikeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hikeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hikeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HikeDTO> result = hikeService.partialUpdate(hikeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hikeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hikes} : get all the hikes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hikes in body.
     */
    @GetMapping("/hikes")
    public ResponseEntity<List<HikeDTO>> getAllHikes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Hikes");
        Page<HikeDTO> page = hikeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hikes/:id} : get the "id" hike.
     *
     * @param id the id of the hikeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hikeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hikes/{id}")
    public ResponseEntity<HikeDTO> getHike(@PathVariable Long id) {
        log.debug("REST request to get Hike : {}", id);
        Optional<HikeDTO> hikeDTO = hikeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hikeDTO);
    }

    /**
     * {@code DELETE  /hikes/:id} : delete the "id" hike.
     *
     * @param id the id of the hikeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hikes/{id}")
    public ResponseEntity<Void> deleteHike(@PathVariable Long id) {
        log.debug("REST request to delete Hike : {}", id);
        hikeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
