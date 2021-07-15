<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="dingtalkFeaturesLabApp.publicData.home.createOrEditLabel"
          data-cy="PublicDataCreateUpdateHeading"
          v-text="$t('dingtalkFeaturesLabApp.publicData.home.createOrEditLabel')"
        >
          Create or edit a PublicData
        </h2>
        <div>
          <div class="form-group" v-if="publicData.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="publicData.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.publicData.name')" for="public-data-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="public-data-name"
              data-cy="name"
              :class="{ valid: !$v.publicData.name.$invalid, invalid: $v.publicData.name.$invalid }"
              v-model="$v.publicData.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.publicData.fee')" for="public-data-fee">Fee</label>
            <input
              type="number"
              class="form-control"
              name="fee"
              id="public-data-fee"
              data-cy="fee"
              :class="{ valid: !$v.publicData.fee.$invalid, invalid: $v.publicData.fee.$invalid }"
              v-model.number="$v.publicData.fee.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.publicData.reason')" for="public-data-reason"
              >Reason</label
            >
            <input
              type="text"
              class="form-control"
              name="reason"
              id="public-data-reason"
              data-cy="reason"
              :class="{ valid: !$v.publicData.reason.$invalid, invalid: $v.publicData.reason.$invalid }"
              v-model="$v.publicData.reason.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.publicData.itemType')" for="public-data-itemType"
              >Item Type</label
            >
            <select
              class="form-control"
              name="itemType"
              :class="{ valid: !$v.publicData.itemType.$invalid, invalid: $v.publicData.itemType.$invalid }"
              v-model="$v.publicData.itemType.$model"
              id="public-data-itemType"
              data-cy="itemType"
            >
              <option value="CostBudget" v-bind:label="$t('dingtalkFeaturesLabApp.ItemType.CostBudget')">CostBudget</option>
              <option value="ContractAmount" v-bind:label="$t('dingtalkFeaturesLabApp.ItemType.ContractAmount')">ContractAmount</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.publicData.typesOfFee')" for="public-data-typesOfFee"
              >Types Of Fee</label
            >
            <select
              class="form-control"
              name="typesOfFee"
              :class="{ valid: !$v.publicData.typesOfFee.$invalid, invalid: $v.publicData.typesOfFee.$invalid }"
              v-model="$v.publicData.typesOfFee.$model"
              id="public-data-typesOfFee"
              data-cy="typesOfFee"
            >
              <option value="IT" v-bind:label="$t('dingtalkFeaturesLabApp.TypesOfFee.IT')">IT</option>
              <option value="Purchase" v-bind:label="$t('dingtalkFeaturesLabApp.TypesOfFee.Purchase')">Purchase</option>
              <option value="DueDiligence" v-bind:label="$t('dingtalkFeaturesLabApp.TypesOfFee.DueDiligence')">DueDiligence</option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.publicData.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./public-data-update.component.ts"></script>
