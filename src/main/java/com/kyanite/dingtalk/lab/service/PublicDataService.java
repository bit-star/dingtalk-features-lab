package com.kyanite.dingtalk.lab.service;

import com.kyanite.dingtalk.lab.domain.PublicData;
import com.kyanite.dingtalk.lab.repository.PublicDataRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PublicData}.
 */
@Service
@Transactional
public class PublicDataService {

    private final Logger log = LoggerFactory.getLogger(PublicDataService.class);

    private final PublicDataRepository publicDataRepository;

    public PublicDataService(PublicDataRepository publicDataRepository) {
        this.publicDataRepository = publicDataRepository;
    }

    /**
     * Save a publicData.
     *
     * @param publicData the entity to save.
     * @return the persisted entity.
     */
    public PublicData save(PublicData publicData) {
        log.debug("Request to save PublicData : {}", publicData);
        return publicDataRepository.save(publicData);
    }

    /**
     * Partially update a publicData.
     *
     * @param publicData the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicData> partialUpdate(PublicData publicData) {
        log.debug("Request to partially update PublicData : {}", publicData);

        return publicDataRepository
            .findById(publicData.getId())
            .map(
                existingPublicData -> {
                    if (publicData.getName() != null) {
                        existingPublicData.setName(publicData.getName());
                    }
                    if (publicData.getFee() != null) {
                        existingPublicData.setFee(publicData.getFee());
                    }
                    if (publicData.getReason() != null) {
                        existingPublicData.setReason(publicData.getReason());
                    }
                    if (publicData.getItemType() != null) {
                        existingPublicData.setItemType(publicData.getItemType());
                    }
                    if (publicData.getTypesOfFee() != null) {
                        existingPublicData.setTypesOfFee(publicData.getTypesOfFee());
                    }
                    if (publicData.getAgree() != null) {
                        existingPublicData.setAgree(publicData.getAgree());
                    }

                    return existingPublicData;
                }
            )
            .map(publicDataRepository::save);
    }

    /**
     * Get all the publicData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PublicData> findAll() {
        log.debug("Request to get all PublicData");
        return publicDataRepository.findAll();
    }

    /**
     * Get one publicData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicData> findOne(Long id) {
        log.debug("Request to get PublicData : {}", id);
        return publicDataRepository.findById(id);
    }

    /**
     * Delete the publicData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PublicData : {}", id);
        publicDataRepository.deleteById(id);
    }
}
