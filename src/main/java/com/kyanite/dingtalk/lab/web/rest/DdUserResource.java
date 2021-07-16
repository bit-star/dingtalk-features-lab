package com.kyanite.dingtalk.lab.web.rest;

import com.kyanite.dingtalk.lab.domain.DdUser;
import com.kyanite.dingtalk.lab.repository.DdUserRepository;
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
 * REST controller for managing {@link com.kyanite.dingtalk.lab.domain.DdUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DdUserResource {

    private final Logger log = LoggerFactory.getLogger(DdUserResource.class);

    private static final String ENTITY_NAME = "ddUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DdUserRepository ddUserRepository;

    public DdUserResource(DdUserRepository ddUserRepository) {
        this.ddUserRepository = ddUserRepository;
    }

    /**
     * {@code POST  /dd-users} : Create a new ddUser.
     *
     * @param ddUser the ddUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ddUser, or with status {@code 400 (Bad Request)} if the ddUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dd-users")
    public ResponseEntity<DdUser> createDdUser(@RequestBody DdUser ddUser) throws URISyntaxException {
        log.debug("REST request to save DdUser : {}", ddUser);
        if (ddUser.getId() != null) {
            throw new BadRequestAlertException("A new ddUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DdUser result = ddUserRepository.save(ddUser);
        return ResponseEntity
            .created(new URI("/api/dd-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dd-users/:id} : Updates an existing ddUser.
     *
     * @param id the id of the ddUser to save.
     * @param ddUser the ddUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ddUser,
     * or with status {@code 400 (Bad Request)} if the ddUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ddUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dd-users/{id}")
    public ResponseEntity<DdUser> updateDdUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody DdUser ddUser)
        throws URISyntaxException {
        log.debug("REST request to update DdUser : {}, {}", id, ddUser);
        if (ddUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ddUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ddUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DdUser result = ddUserRepository.save(ddUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ddUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dd-users/:id} : Partial updates given fields of an existing ddUser, field will ignore if it is null
     *
     * @param id the id of the ddUser to save.
     * @param ddUser the ddUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ddUser,
     * or with status {@code 400 (Bad Request)} if the ddUser is not valid,
     * or with status {@code 404 (Not Found)} if the ddUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the ddUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dd-users/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DdUser> partialUpdateDdUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DdUser ddUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update DdUser partially : {}, {}", id, ddUser);
        if (ddUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ddUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ddUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DdUser> result = ddUserRepository
            .findById(ddUser.getId())
            .map(
                existingDdUser -> {
                    if (ddUser.getUnionid() != null) {
                        existingDdUser.setUnionid(ddUser.getUnionid());
                    }
                    if (ddUser.getRemark() != null) {
                        existingDdUser.setRemark(ddUser.getRemark());
                    }
                    if (ddUser.getUserid() != null) {
                        existingDdUser.setUserid(ddUser.getUserid());
                    }
                    if (ddUser.getIsLeaderInDepts() != null) {
                        existingDdUser.setIsLeaderInDepts(ddUser.getIsLeaderInDepts());
                    }
                    if (ddUser.getIsBoss() != null) {
                        existingDdUser.setIsBoss(ddUser.getIsBoss());
                    }
                    if (ddUser.getHiredDate() != null) {
                        existingDdUser.setHiredDate(ddUser.getHiredDate());
                    }
                    if (ddUser.getIsSenior() != null) {
                        existingDdUser.setIsSenior(ddUser.getIsSenior());
                    }
                    if (ddUser.getTel() != null) {
                        existingDdUser.setTel(ddUser.getTel());
                    }
                    if (ddUser.getDepartment() != null) {
                        existingDdUser.setDepartment(ddUser.getDepartment());
                    }
                    if (ddUser.getWorkPlace() != null) {
                        existingDdUser.setWorkPlace(ddUser.getWorkPlace());
                    }
                    if (ddUser.getOrderInDepts() != null) {
                        existingDdUser.setOrderInDepts(ddUser.getOrderInDepts());
                    }
                    if (ddUser.getMobile() != null) {
                        existingDdUser.setMobile(ddUser.getMobile());
                    }
                    if (ddUser.getErrmsg() != null) {
                        existingDdUser.setErrmsg(ddUser.getErrmsg());
                    }
                    if (ddUser.getActive() != null) {
                        existingDdUser.setActive(ddUser.getActive());
                    }
                    if (ddUser.getAvatar() != null) {
                        existingDdUser.setAvatar(ddUser.getAvatar());
                    }
                    if (ddUser.getIsAdmin() != null) {
                        existingDdUser.setIsAdmin(ddUser.getIsAdmin());
                    }
                    if (ddUser.getIsHide() != null) {
                        existingDdUser.setIsHide(ddUser.getIsHide());
                    }
                    if (ddUser.getJobnumber() != null) {
                        existingDdUser.setJobnumber(ddUser.getJobnumber());
                    }
                    if (ddUser.getName() != null) {
                        existingDdUser.setName(ddUser.getName());
                    }
                    if (ddUser.getExtattr() != null) {
                        existingDdUser.setExtattr(ddUser.getExtattr());
                    }
                    if (ddUser.getStateCode() != null) {
                        existingDdUser.setStateCode(ddUser.getStateCode());
                    }
                    if (ddUser.getPosition() != null) {
                        existingDdUser.setPosition(ddUser.getPosition());
                    }
                    if (ddUser.getRoles() != null) {
                        existingDdUser.setRoles(ddUser.getRoles());
                    }

                    return existingDdUser;
                }
            )
            .map(ddUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ddUser.getId().toString())
        );
    }

    /**
     * {@code GET  /dd-users} : get all the ddUsers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ddUsers in body.
     */
    @GetMapping("/dd-users")
    public List<DdUser> getAllDdUsers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DdUsers");
        return ddUserRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /dd-users/:id} : get the "id" ddUser.
     *
     * @param id the id of the ddUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ddUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dd-users/{id}")
    public ResponseEntity<DdUser> getDdUser(@PathVariable Long id) {
        log.debug("REST request to get DdUser : {}", id);
        Optional<DdUser> ddUser = ddUserRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(ddUser);
    }

    /**
     * {@code DELETE  /dd-users/:id} : delete the "id" ddUser.
     *
     * @param id the id of the ddUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dd-users/{id}")
    public ResponseEntity<Void> deleteDdUser(@PathVariable Long id) {
        log.debug("REST request to delete DdUser : {}", id);
        ddUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
