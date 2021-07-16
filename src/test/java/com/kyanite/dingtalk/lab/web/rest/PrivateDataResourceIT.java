package com.kyanite.dingtalk.lab.web.rest;

import static com.kyanite.dingtalk.lab.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.dingtalk.lab.IntegrationTest;
import com.kyanite.dingtalk.lab.domain.PrivateData;
import com.kyanite.dingtalk.lab.domain.enumeration.ItemType;
import com.kyanite.dingtalk.lab.domain.enumeration.TypesOfFee;
import com.kyanite.dingtalk.lab.repository.PrivateDataRepository;
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
 * Integration tests for the {@link PrivateDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrivateDataResourceIT {

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

    private static final String ENTITY_API_URL = "/api/private-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrivateDataRepository privateDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrivateDataMockMvc;

    private PrivateData privateData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrivateData createEntity(EntityManager em) {
        PrivateData privateData = new PrivateData()
            .name(DEFAULT_NAME)
            .fee(DEFAULT_FEE)
            .reason(DEFAULT_REASON)
            .itemType(DEFAULT_ITEM_TYPE)
            .typesOfFee(DEFAULT_TYPES_OF_FEE)
            .agree(DEFAULT_AGREE);
        return privateData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrivateData createUpdatedEntity(EntityManager em) {
        PrivateData privateData = new PrivateData()
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE);
        return privateData;
    }

    @BeforeEach
    public void initTest() {
        privateData = createEntity(em);
    }

    @Test
    @Transactional
    void createPrivateData() throws Exception {
        int databaseSizeBeforeCreate = privateDataRepository.findAll().size();
        // Create the PrivateData
        restPrivateDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateData)))
            .andExpect(status().isCreated());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeCreate + 1);
        PrivateData testPrivateData = privateDataList.get(privateDataList.size() - 1);
        assertThat(testPrivateData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrivateData.getFee()).isEqualByComparingTo(DEFAULT_FEE);
        assertThat(testPrivateData.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testPrivateData.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testPrivateData.getTypesOfFee()).isEqualTo(DEFAULT_TYPES_OF_FEE);
        assertThat(testPrivateData.getAgree()).isEqualTo(DEFAULT_AGREE);
    }

    @Test
    @Transactional
    void createPrivateDataWithExistingId() throws Exception {
        // Create the PrivateData with an existing ID
        privateData.setId(1L);

        int databaseSizeBeforeCreate = privateDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrivateDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateData)))
            .andExpect(status().isBadRequest());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrivateData() throws Exception {
        // Initialize the database
        privateDataRepository.saveAndFlush(privateData);

        // Get all the privateDataList
        restPrivateDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privateData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(sameNumber(DEFAULT_FEE))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typesOfFee").value(hasItem(DEFAULT_TYPES_OF_FEE.toString())))
            .andExpect(jsonPath("$.[*].agree").value(hasItem(DEFAULT_AGREE.booleanValue())));
    }

    @Test
    @Transactional
    void getPrivateData() throws Exception {
        // Initialize the database
        privateDataRepository.saveAndFlush(privateData);

        // Get the privateData
        restPrivateDataMockMvc
            .perform(get(ENTITY_API_URL_ID, privateData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(privateData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fee").value(sameNumber(DEFAULT_FEE)))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.typesOfFee").value(DEFAULT_TYPES_OF_FEE.toString()))
            .andExpect(jsonPath("$.agree").value(DEFAULT_AGREE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPrivateData() throws Exception {
        // Get the privateData
        restPrivateDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPrivateData() throws Exception {
        // Initialize the database
        privateDataRepository.saveAndFlush(privateData);

        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();

        // Update the privateData
        PrivateData updatedPrivateData = privateDataRepository.findById(privateData.getId()).get();
        // Disconnect from session so that the updates on updatedPrivateData are not directly saved in db
        em.detach(updatedPrivateData);
        updatedPrivateData
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE);

        restPrivateDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrivateData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrivateData))
            )
            .andExpect(status().isOk());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
        PrivateData testPrivateData = privateDataList.get(privateDataList.size() - 1);
        assertThat(testPrivateData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrivateData.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testPrivateData.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPrivateData.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPrivateData.getTypesOfFee()).isEqualTo(UPDATED_TYPES_OF_FEE);
        assertThat(testPrivateData.getAgree()).isEqualTo(UPDATED_AGREE);
    }

    @Test
    @Transactional
    void putNonExistingPrivateData() throws Exception {
        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();
        privateData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivateDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, privateData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privateData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrivateData() throws Exception {
        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();
        privateData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privateData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrivateData() throws Exception {
        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();
        privateData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrivateDataWithPatch() throws Exception {
        // Initialize the database
        privateDataRepository.saveAndFlush(privateData);

        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();

        // Update the privateData using partial update
        PrivateData partialUpdatedPrivateData = new PrivateData();
        partialUpdatedPrivateData.setId(privateData.getId());

        partialUpdatedPrivateData.name(UPDATED_NAME).reason(UPDATED_REASON).itemType(UPDATED_ITEM_TYPE).agree(UPDATED_AGREE);

        restPrivateDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivateData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivateData))
            )
            .andExpect(status().isOk());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
        PrivateData testPrivateData = privateDataList.get(privateDataList.size() - 1);
        assertThat(testPrivateData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrivateData.getFee()).isEqualByComparingTo(DEFAULT_FEE);
        assertThat(testPrivateData.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPrivateData.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPrivateData.getTypesOfFee()).isEqualTo(DEFAULT_TYPES_OF_FEE);
        assertThat(testPrivateData.getAgree()).isEqualTo(UPDATED_AGREE);
    }

    @Test
    @Transactional
    void fullUpdatePrivateDataWithPatch() throws Exception {
        // Initialize the database
        privateDataRepository.saveAndFlush(privateData);

        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();

        // Update the privateData using partial update
        PrivateData partialUpdatedPrivateData = new PrivateData();
        partialUpdatedPrivateData.setId(privateData.getId());

        partialUpdatedPrivateData
            .name(UPDATED_NAME)
            .fee(UPDATED_FEE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE);

        restPrivateDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivateData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivateData))
            )
            .andExpect(status().isOk());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
        PrivateData testPrivateData = privateDataList.get(privateDataList.size() - 1);
        assertThat(testPrivateData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrivateData.getFee()).isEqualByComparingTo(UPDATED_FEE);
        assertThat(testPrivateData.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPrivateData.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPrivateData.getTypesOfFee()).isEqualTo(UPDATED_TYPES_OF_FEE);
        assertThat(testPrivateData.getAgree()).isEqualTo(UPDATED_AGREE);
    }

    @Test
    @Transactional
    void patchNonExistingPrivateData() throws Exception {
        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();
        privateData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivateDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, privateData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privateData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrivateData() throws Exception {
        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();
        privateData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privateData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrivateData() throws Exception {
        int databaseSizeBeforeUpdate = privateDataRepository.findAll().size();
        privateData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(privateData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrivateData in the database
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrivateData() throws Exception {
        // Initialize the database
        privateDataRepository.saveAndFlush(privateData);

        int databaseSizeBeforeDelete = privateDataRepository.findAll().size();

        // Delete the privateData
        restPrivateDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, privateData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrivateData> privateDataList = privateDataRepository.findAll();
        assertThat(privateDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
