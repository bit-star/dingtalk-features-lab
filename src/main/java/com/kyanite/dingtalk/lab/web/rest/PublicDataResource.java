package com.kyanite.dingtalk.lab.web.rest;

import com.kyanite.dingtalk.lab.domain.PublicData;
import com.kyanite.dingtalk.lab.repository.PublicDataRepository;
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
 * REST controller for managing {@link com.kyanite.dingtalk.lab.domain.PublicData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PublicDataResource {

    private final Logger log = LoggerFactory.getLogger(PublicDataResource.class);

    private static final String ENTITY_NAME = "publicData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicDataRepository publicDataRepository;

    public PublicDataResource(PublicDataRepository publicDataRepository) {
        this.publicDataRepository = publicDataRepository;
    }

    /**
     * {@code POST  /public-data} : Create a new publicData.
     *
     * @param publicData the publicData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicData, or with status {@code 400 (Bad Request)} if the publicData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-data")
    public ResponseEntity<PublicData> createPublicData(@RequestBody PublicData publicData) throws URISyntaxException {
        log.debug("REST request to save PublicData : {}", publicData);
        if (publicData.getId() != null) {
            throw new BadRequestAlertException("A new publicData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicData result = publicDataRepository.save(publicData);
        return ResponseEntity
            .created(new URI("/api/public-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-data/:id} : Updates an existing publicData.
     *
     * @param id the id of the publicData to save.
     * @param publicData the publicData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicData,
     * or with status {@code 400 (Bad Request)} if the publicData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-data/{id}")
    public ResponseEntity<PublicData> updatePublicData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicData publicData
    ) throws URISyntaxException {
        log.debug("REST request to update PublicData : {}, {}", id, publicData);
        if (publicData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PublicData result = publicDataRepository.save(publicData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /public-data/:id} : Partial updates given fields of an existing publicData, field will ignore if it is null
     *
     * @param id the id of the publicData to save.
     * @param publicData the publicData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicData,
     * or with status {@code 400 (Bad Request)} if the publicData is not valid,
     * or with status {@code 404 (Not Found)} if the publicData is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/public-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PublicData> partialUpdatePublicData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicData publicData
    ) throws URISyntaxException {
        log.debug("REST request to partial update PublicData partially : {}, {}", id, publicData);
        if (publicData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicData> result = publicDataRepository
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

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicData.getId().toString())
        );
    }

    /**
     * {@code GET  /public-data} : get all the publicData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicData in body.
     */
    @GetMapping("/public-data")
    public List<PublicData> getAllPublicData() {
        log.debug("REST request to get all PublicData");
        return publicDataRepository.findAll();
    }

    /**
     * {@code GET  /public-data/:id} : get the "id" publicData.
     *
     * @param id the id of the publicData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-data/{id}")
    public ResponseEntity<PublicData> getPublicData(@PathVariable Long id) {
        log.debug("REST request to get PublicData : {}", id);
        Optional<PublicData> publicData = publicDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(publicData);
    }

    /**
     * {@code DELETE  /public-data/:id} : delete the "id" publicData.
     *
     * @param id the id of the publicData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-data/{id}")
    public ResponseEntity<Void> deletePublicData(@PathVariable Long id) {
        log.debug("REST request to delete PublicData : {}", id);
        publicDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
