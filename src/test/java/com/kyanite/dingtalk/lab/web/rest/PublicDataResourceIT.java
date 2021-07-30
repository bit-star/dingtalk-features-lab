package com.kyanite.dingtalk.lab.web.rest;

import static com.kyanite.dingtalk.lab.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.dingtalk.lab.IntegrationTest;
import com.kyanite.dingtalk.lab.domain.PublicData;
import com.kyanite.dingtalk.lab.domain.enumeration.ItemType;
import com.kyanite.dingtalk.lab.domain.enumeration.TypesOfFee;
import com.kyanite.dingtalk.lab.repository.PublicDataRepository;
import java.math.BigDecimal;
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

/**
 * Integration tests for the {@link PublicDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicDataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FEE = new BigDecimal(2);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final ItemType DEFAULT_ITEM_TYPE = ItemType.CostBudget;
    private static final ItemType UPDATED_ITEM_TYPE = ItemType.ContractAmount;

    private static final TypesOfFee DEFAULT_TYPES_OF_FEE = TypesOfFee.IT;
    private static final TypesOfFee UPDATED_TYPES_OF_FEE = TypesOfFee.Purchase;

    private static final Boolean DEFAULT_AGREE = false;
    private static final Boolean UPDATED_AGREE = true;

    private static final Long DEFAULT_REQUESTID = 1L;
    private static final Long UPDATED_REQUESTID = 2L;

    private static final Long DEFAULT_WORKFLOWID = 1L;
    private static final Long UPDATED_WORKFLOWID = 2L;

    private static final String ENTITY_API_URL = "/api/public-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PublicDataRepository publicDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicDataMockMvc;

    private PublicData publicData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicData createEntity(EntityManager em) {
        PublicData publicData = new PublicData()
            .name(DEFAULT_NAME)
            .fee(DEFAULT_FEE)
            .reason(DEFAULT_REASON)
            .itemType(DEFAULT_ITEM_TYPE)
            .typesOfFee(DEFAULT_TYPES_OF_FEE)
            .agree(DEFAULT_AGREE)
            .requestid(DEFAULT_REQUESTID)
            .workflowid(DEFAULT_WORKFLOWID);
        return publicData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicData createUpdatedEntity(EntityManager em) {
        PublicData publicData = new PublicData()
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE)
            .requestid(UPDATED_REQUESTID)
            .workflowid(UPDATED_WORKFLOWID);
        return publicData;
    }

    @BeforeEach
    public void initTest() {
        publicData = createEntity(em);
    }

    @Test
    @Transactional
    void createPublicData() throws Exception {
        int databaseSizeBeforeCreate = publicDataRepository.findAll().size();
        // Create the PublicData
        restPublicDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicData)))
            .andExpect(status().isCreated());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeCreate + 1);
        PublicData testPublicData = publicDataList.get(publicDataList.size() - 1);
        assertThat(testPublicData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPublicData.getFee()).isEqualByComparingTo(DEFAULT_FEE);
        assertThat(testPublicData.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testPublicData.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testPublicData.getTypesOfFee()).isEqualTo(DEFAULT_TYPES_OF_FEE);
        assertThat(testPublicData.getAgree()).isEqualTo(DEFAULT_AGREE);
        assertThat(testPublicData.getRequestid()).isEqualTo(DEFAULT_REQUESTID);
        assertThat(testPublicData.getWorkflowid()).isEqualTo(DEFAULT_WORKFLOWID);
    }

    @Test
    @Transactional
    void createPublicDataWithExistingId() throws Exception {
        // Create the PublicData with an existing ID
        publicData.setId(1L);

        int databaseSizeBeforeCreate = publicDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicData)))
            .andExpect(status().isBadRequest());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicData() throws Exception {
        // Initialize the database
        publicDataRepository.saveAndFlush(publicData);

        // Get all the publicDataList
        restPublicDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(sameNumber(DEFAULT_FEE))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typesOfFee").value(hasItem(DEFAULT_TYPES_OF_FEE.toString())))
            .andExpect(jsonPath("$.[*].agree").value(hasItem(DEFAULT_AGREE.booleanValue())))
            .andExpect(jsonPath("$.[*].requestid").value(hasItem(DEFAULT_REQUESTID.intValue())))
            .andExpect(jsonPath("$.[*].workflowid").value(hasItem(DEFAULT_WORKFLOWID.intValue())));
    }

    @Test
    @Transactional
    void getPublicData() throws Exception {
        // Initialize the database
        publicDataRepository.saveAndFlush(publicData);

        // Get the publicData
        restPublicDataMockMvc
            .perform(get(ENTITY_API_URL_ID, publicData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fee").value(sameNumber(DEFAULT_FEE)))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.typesOfFee").value(DEFAULT_TYPES_OF_FEE.toString()))
            .andExpect(jsonPath("$.agree").value(DEFAULT_AGREE.booleanValue()))
            .andExpect(jsonPath("$.requestid").value(DEFAULT_REQUESTID.intValue()))
            .andExpect(jsonPath("$.workflowid").value(DEFAULT_WORKFLOWID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPublicData() throws Exception {
        // Get the publicData
        restPublicDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPublicData() throws Exception {
        // Initialize the database
        publicDataRepository.saveAndFlush(publicData);

        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();

        // Update the publicData
        PublicData updatedPublicData = publicDataRepository.findById(publicData.getId()).get();
        // Disconnect from session so that the updates on updatedPublicData are not directly saved in db
        em.detach(updatedPublicData);
        updatedPublicData
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE)
            .requestid(UPDATED_REQUESTID)
            .workflowid(UPDATED_WORKFLOWID);

        restPublicDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPublicData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPublicData))
            )
            .andExpect(status().isOk());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
        PublicData testPublicData = publicDataList.get(publicDataList.size() - 1);
        assertThat(testPublicData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublicData.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testPublicData.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPublicData.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPublicData.getTypesOfFee()).isEqualTo(UPDATED_TYPES_OF_FEE);
        assertThat(testPublicData.getAgree()).isEqualTo(UPDATED_AGREE);
        assertThat(testPublicData.getRequestid()).isEqualTo(UPDATED_REQUESTID);
        assertThat(testPublicData.getWorkflowid()).isEqualTo(UPDATED_WORKFLOWID);
    }

    @Test
    @Transactional
    void putNonExistingPublicData() throws Exception {
        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();
        publicData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicData() throws Exception {
        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();
        publicData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicData() throws Exception {
        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();
        publicData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicDataWithPatch() throws Exception {
        // Initialize the database
        publicDataRepository.saveAndFlush(publicData);

        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();

        // Update the publicData using partial update
        PublicData partialUpdatedPublicData = new PublicData();
        partialUpdatedPublicData.setId(publicData.getId());

        partialUpdatedPublicData
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .workflowid(UPDATED_WORKFLOWID);

        restPublicDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicData))
            )
            .andExpect(status().isOk());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
        PublicData testPublicData = publicDataList.get(publicDataList.size() - 1);
        assertThat(testPublicData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublicData.getFee()).isEqualByComparingTo(UPDATED_FEE);
        assertThat(testPublicData.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPublicData.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPublicData.getTypesOfFee()).isEqualTo(DEFAULT_TYPES_OF_FEE);
        assertThat(testPublicData.getAgree()).isEqualTo(DEFAULT_AGREE);
        assertThat(testPublicData.getRequestid()).isEqualTo(DEFAULT_REQUESTID);
        assertThat(testPublicData.getWorkflowid()).isEqualTo(UPDATED_WORKFLOWID);
    }

    @Test
    @Transactional
    void fullUpdatePublicDataWithPatch() throws Exception {
        // Initialize the database
        publicDataRepository.saveAndFlush(publicData);

        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();

        // Update the publicData using partial update
        PublicData partialUpdatedPublicData = new PublicData();
        partialUpdatedPublicData.setId(publicData.getId());

        partialUpdatedPublicData
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE)
            .requestid(UPDATED_REQUESTID)
            .workflowid(UPDATED_WORKFLOWID);

        restPublicDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicData))
            )
            .andExpect(status().isOk());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
        PublicData testPublicData = publicDataList.get(publicDataList.size() - 1);
        assertThat(testPublicData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublicData.getFee()).isEqualByComparingTo(UPDATED_FEE);
        assertThat(testPublicData.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPublicData.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPublicData.getTypesOfFee()).isEqualTo(UPDATED_TYPES_OF_FEE);
        assertThat(testPublicData.getAgree()).isEqualTo(UPDATED_AGREE);
        assertThat(testPublicData.getRequestid()).isEqualTo(UPDATED_REQUESTID);
        assertThat(testPublicData.getWorkflowid()).isEqualTo(UPDATED_WORKFLOWID);
    }

    @Test
    @Transactional
    void patchNonExistingPublicData() throws Exception {
        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();
        publicData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicData() throws Exception {
        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();
        publicData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicData() throws Exception {
        int databaseSizeBeforeUpdate = publicDataRepository.findAll().size();
        publicData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(publicData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicData in the database
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicData() throws Exception {
        // Initialize the database
        publicDataRepository.saveAndFlush(publicData);

        int databaseSizeBeforeDelete = publicDataRepository.findAll().size();

        // Delete the publicData
        restPublicDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicData> publicDataList = publicDataRepository.findAll();
        assertThat(publicDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
