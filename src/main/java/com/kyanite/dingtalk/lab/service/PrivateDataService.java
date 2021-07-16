package com.kyanite.dingtalk.lab.service;

import com.kyanite.dingtalk.lab.domain.PrivateData;
import com.kyanite.dingtalk.lab.repository.PrivateDataRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PrivateData}.
 */
@Service
@Transactional
public class PrivateDataService {

    private final Logger log = LoggerFactory.getLogger(PrivateDataService.class);

    private final PrivateDataRepository privateDataRepository;

    public PrivateDataService(PrivateDataRepository privateDataRepository) {
        this.privateDataRepository = privateDataRepository;
    }

    /**
     * Save a privateData.
     *
     * @param privateData the entity to save.
     * @return the persisted entity.
     */
    public PrivateData save(PrivateData privateData) {
        log.debug("Request to save PrivateData : {}", privateData);
        return privateDataRepository.save(privateData);
    }

    /**
     * Partially update a privateData.
     *
     * @param privateData the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PrivateData> partialUpdate(PrivateData privateData) {
        log.debug("Request to partially update PrivateData : {}", privateData);

        return privateDataRepository
            .findById(privateData.getId())
            .map(
                existingPrivateData -> {
                    if (privateData.getName() != null) {
                        existingPrivateData.setName(privateData.getName());
                    }
                    if (privateData.getFee() != null) {
                        existingPrivateData.setFee(privateData.getFee());
                    }
                    if (privateData.getReason() != null) {
                        existingPrivateData.setReason(privateData.getReason());
                    }
                    if (privateData.getItemType() != null) {
                        existingPrivateData.setItemType(privateData.getItemType());
                    }
                    if (privateData.getTypesOfFee() != null) {
                        existingPrivateData.setTypesOfFee(privateData.getTypesOfFee());
                    }
                    if (privateData.getAgree() != null) {
                        existingPrivateData.setAgree(privateData.getAgree());
                    }

                    return existingPrivateData;
                }
            )
            .map(privateDataRepository::save);
    }

    /**
     * Get all the privateData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrivateData> findAll() {
        log.debug("Request to get all PrivateData");
        return privateDataRepository.findAll();
    }

    /**
     * Get one privateData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrivateData> findOne(Long id) {
        log.debug("Request to get PrivateData : {}", id);
        return privateDataRepository.findById(id);
    }

    /**
     * Delete the privateData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrivateData : {}", id);
        privateDataRepository.deleteById(id);
    }
}
