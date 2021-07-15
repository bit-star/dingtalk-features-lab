package com.kyanite.dingtalk.lab.web.rest;

import com.kyanite.dingtalk.lab.domain.PrivateData;
import com.kyanite.dingtalk.lab.repository.PrivateDataRepository;
import com.kyanite.dingtalk.lab.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kyanite.dingtalk.lab.domain.PrivateData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrivateDataResource {

    private final Logger log = LoggerFactory.getLogger(PrivateDataResource.class);

    private static final String ENTITY_NAME = "privateData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrivateDataRepository privateDataRepository;

    public PrivateDataResource(PrivateDataRepository privateDataRepository) {
        this.privateDataRepository = privateDataRepository;
    }

    /**
     * {@code POST  /private-data} : Create a new privateData.
     *
     * @param privateData the privateData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new privateData, or with status {@code 400 (Bad Request)} if the privateData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/private-data")
    public ResponseEntity<PrivateData> createPrivateData(@RequestBody PrivateData privateData) throws URISyntaxException {
        log.debug("REST request to save PrivateData : {}", privateData);
        if (privateData.getId() != null) {
            throw new BadRequestAlertException("A new privateData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrivateData result = privateDataRepository.save(privateData);
        return ResponseEntity
            .created(new URI("/api/private-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /private-data/:id} : Updates an existing privateData.
     *
     * @param id the id of the privateData to save.
     * @param privateData the privateData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privateData,
     * or with status {@code 400 (Bad Request)} if the privateData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the privateData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/private-data/{id}")
    public ResponseEntity<PrivateData> updatePrivateData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrivateData privateData
    ) throws URISyntaxException {
        log.debug("REST request to update PrivateData : {}, {}", id, privateData);
        if (privateData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privateData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privateDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrivateData result = privateDataRepository.save(privateData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privateData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /private-data/:id} : Partial updates given fields of an existing privateData, field will ignore if it is null
     *
     * @param id the id of the privateData to save.
     * @param privateData the privateData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privateData,
     * or with status {@code 400 (Bad Request)} if the privateData is not valid,
     * or with status {@code 404 (Not Found)} if the privateData is not found,
     * or with status {@code 500 (Internal Server Error)} if the privateData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/private-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PrivateData> partialUpdatePrivateData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrivateData privateData
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrivateData partially : {}, {}", id, privateData);
        if (privateData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privateData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privateDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrivateData> result = privateDataRepository
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

                    return existingPrivateData;
                }
            )
            .map(privateDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privateData.getId().toString())
        );
    }

    /**
     * {@code GET  /private-data} : get all the privateData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of privateData in body.
     */
    @GetMapping("/private-data")
    public List<PrivateData> getAllPrivateData() {
        log.debug("REST request to get all PrivateData");
        return privateDataRepository.findAll();
    }

    /**
     * {@code GET  /private-data/:id} : get the "id" privateData.
     *
     * @param id the id of the privateData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the privateData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/private-data/{id}")
    public ResponseEntity<PrivateData> getPrivateData(@PathVariable Long id) {
        log.debug("REST request to get PrivateData : {}", id);
        Optional<PrivateData> privateData = privateDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(privateData);
    }

    /**
     * {@code DELETE  /private-data/:id} : delete the "id" privateData.
     *
     * @param id the id of the privateData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/private-data/{id}")
    public ResponseEntity<Void> deletePrivateData(@PathVariable Long id) {
        log.debug("REST request to delete PrivateData : {}", id);
        privateDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
