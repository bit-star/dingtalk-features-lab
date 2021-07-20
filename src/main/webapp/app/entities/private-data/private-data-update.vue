<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="dingtalkFeaturesLabApp.privateData.home.createOrEditLabel"
          data-cy="PrivateDataCreateUpdateHeading"
          v-text="$t('dingtalkFeaturesLabApp.privateData.home.createOrEditLabel')"
        >
          Create or edit a PrivateData
        </h2>
        <div>
          <div class="form-group" v-if="privateData.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="privateData.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.privateData.name')" for="private-data-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="private-data-name"
              data-cy="name"
              :class="{ valid: !$v.privateData.name.$invalid, invalid: $v.privateData.name.$invalid }"
              v-model="$v.privateData.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.privateData.fee')" for="private-data-fee">Fee</label>
            <input
              type="number"
              class="form-control"
              name="fee"
              id="private-data-fee"
              data-cy="fee"
              :class="{ valid: !$v.privateData.fee.$invalid, invalid: $v.privateData.fee.$invalid }"
              v-model.number="$v.privateData.fee.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.privateData.reason')" for="private-data-reason"
              >Reason</label
            >
            <input
              type="text"
              class="form-control"
              name="reason"
              id="private-data-reason"
              data-cy="reason"
              :class="{ valid: !$v.privateData.reason.$invalid, invalid: $v.privateData.reason.$invalid }"
              v-model="$v.privateData.reason.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.privateData.itemType')" for="private-data-itemType"
              >Item Type</label
            >
            <select
              class="form-control"
              name="itemType"
              :class="{ valid: !$v.privateData.itemType.$invalid, invalid: $v.privateData.itemType.$invalid }"
              v-model="$v.privateData.itemType.$model"
              id="private-data-itemType"
              data-cy="itemType"
            >
              <option value="CostBudget" v-bind:label="$t('dingtalkFeaturesLabApp.ItemType.CostBudget')">CostBudget</option>
              <option value="ContractAmount" v-bind:label="$t('dingtalkFeaturesLabApp.ItemType.ContractAmount')">ContractAmount</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.privateData.typesOfFee')" for="private-data-typesOfFee"
              >Types Of Fee</label
            >
            <select
              class="form-control"
              name="typesOfFee"
              :class="{ valid: !$v.privateData.typesOfFee.$invalid, invalid: $v.privateData.typesOfFee.$invalid }"
              v-model="$v.privateData.typesOfFee.$model"
              id="private-data-typesOfFee"
              data-cy="typesOfFee"
            >
              <option value="IT" v-bind:label="$t('dingtalkFeaturesLabApp.TypesOfFee.IT')">IT</option>
              <option value="Purchase" v-bind:label="$t('dingtalkFeaturesLabApp.TypesOfFee.Purchase')">Purchase</option>
              <option value="DueDiligence" v-bind:label="$t('dingtalkFeaturesLabApp.TypesOfFee.DueDiligence')">DueDiligence</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.privateData.agree')" for="private-data-agree">Agree</label>
            <input
              type="checkbox"
              class="form-check"
              name="agree"
              id="private-data-agree"
              data-cy="agree"
              :class="{ valid: !$v.privateData.agree.$invalid, invalid: $v.privateData.agree.$invalid }"
              v-model="$v.privateData.agree.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.privateData.publicData')" for="private-data-publicData"
              >Public Data</label
            >
            <select
              class="form-control"
              id="private-data-publicData"
              data-cy="publicData"
              name="publicData"
              v-model="privateData.publicData"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  privateData.publicData && publicDataOption.id === privateData.publicData.id ? privateData.publicData : publicDataOption
                "
                v-for="publicDataOption in publicData"
                :key="publicDataOption.id"
              >
                {{ publicDataOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('dingtalkFeaturesLabApp.privateData.ddUser')" for="private-data-ddUser"
              >Dd User</label
            >
            <select class="form-control" id="private-data-ddUser" data-cy="ddUser" name="ddUser" v-model="privateData.ddUser">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="privateData.ddUser && ddUserOption.id === privateData.ddUser.id ? privateData.ddUser : ddUserOption"
                v-for="ddUserOption in ddUsers"
                :key="ddUserOption.id"
              >
                {{ ddUserOption.id }}
              </option>
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
            :disabled="$v.privateData.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./private-data-update.component.ts"></script>
