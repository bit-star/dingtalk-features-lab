<template>
  <div>
    <h2 id="page-heading" data-cy="PrivateDataHeading">
      <span v-text="$t('dingtalkFeaturesLabApp.privateData.home.title')" id="private-data-heading">Private Data</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('dingtalkFeaturesLabApp.privateData.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PrivateDataCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-private-data"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.home.createLabel')"> Create a new Private Data </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && privateData && privateData.length === 0">
      <span v-text="$t('dingtalkFeaturesLabApp.privateData.home.notFound')">No privateData found</span>
    </div>
    <div class="table-responsive" v-if="privateData && privateData.length > 0">
      <table class="table table-striped" aria-describedby="privateData">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.privateData.name')">Name</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.privateData.fee')">Fee</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.privateData.reason')">Reason</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.privateData.itemType')">Item Type</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.privateData.typesOfFee')">Types Of Fee</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.privateData.agree')">Agree</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.privateData.publicData')">Public Data</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="privateData in privateData" :key="privateData.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PrivateDataView', params: { privateDataId: privateData.id } }">{{ privateData.id }}</router-link>
            </td>
            <td>{{ privateData.name }}</td>
            <td>{{ privateData.fee }}</td>
            <td>{{ privateData.reason }}</td>
            <td v-text="$t('dingtalkFeaturesLabApp.ItemType.' + privateData.itemType)">{{ privateData.itemType }}</td>
            <td v-text="$t('dingtalkFeaturesLabApp.TypesOfFee.' + privateData.typesOfFee)">{{ privateData.typesOfFee }}</td>
            <td>{{ privateData.agree }}</td>
            <td>
              <div v-if="privateData.publicData">
                <router-link :to="{ name: 'PublicDataView', params: { publicDataId: privateData.publicData.id } }">{{
                  privateData.publicData.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PrivateDataView', params: { privateDataId: privateData.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PrivateDataEdit', params: { privateDataId: privateData.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(privateData)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="dingtalkFeaturesLabApp.privateData.delete.question"
          data-cy="privateDataDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-privateData-heading" v-text="$t('dingtalkFeaturesLabApp.privateData.delete.question', { id: removeId })">
          Are you sure you want to delete this Private Data?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-privateData"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePrivateData()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./private-data.component.ts"></script>
