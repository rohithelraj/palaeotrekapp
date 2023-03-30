package com.elraj.palaeotrek.service;

import com.elraj.palaeotrek.domain.Hike;
import com.elraj.palaeotrek.repository.HikeRepository;
import com.elraj.palaeotrek.service.dto.HikeDTO;
import com.elraj.palaeotrek.service.mapper.HikeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Hike}.
 */
@Service
@Transactional
public class HikeService {

    private final Logger log = LoggerFactory.getLogger(HikeService.class);

    private final HikeRepository hikeRepository;

    private final HikeMapper hikeMapper;

    public HikeService(HikeRepository hikeRepository, HikeMapper hikeMapper) {
        this.hikeRepository = hikeRepository;
        this.hikeMapper = hikeMapper;
    }

    /**
     * Save a hike.
     *
     * @param hikeDTO the entity to save.
     * @return the persisted entity.
     */
    public HikeDTO save(HikeDTO hikeDTO) {
        log.debug("Request to save Hike : {}", hikeDTO);
        Hike hike = hikeMapper.toEntity(hikeDTO);
        hike = hikeRepository.save(hike);
        return hikeMapper.toDto(hike);
    }

    /**
     * Update a hike.
     *
     * @param hikeDTO the entity to save.
     * @return the persisted entity.
     */
    public HikeDTO update(HikeDTO hikeDTO) {
        log.debug("Request to save Hike : {}", hikeDTO);
        Hike hike = hikeMapper.toEntity(hikeDTO);
        hike = hikeRepository.save(hike);
        return hikeMapper.toDto(hike);
    }

    /**
     * Partially update a hike.
     *
     * @param hikeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HikeDTO> partialUpdate(HikeDTO hikeDTO) {
        log.debug("Request to partially update Hike : {}", hikeDTO);

        return hikeRepository
            .findById(hikeDTO.getId())
            .map(existingHike -> {
                hikeMapper.partialUpdate(existingHike, hikeDTO);

                return existingHike;
            })
            .map(hikeRepository::save)
            .map(hikeMapper::toDto);
    }

    /**
     * Get all the hikes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HikeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hikes");
        return hikeRepository.findAll(pageable).map(hikeMapper::toDto);
    }

    /**
     * Get one hike by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HikeDTO> findOne(Long id) {
        log.debug("Request to get Hike : {}", id);
        return hikeRepository.findById(id).map(hikeMapper::toDto);
    }

    /**
     * Delete the hike by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hike : {}", id);
        hikeRepository.deleteById(id);
    }
}
