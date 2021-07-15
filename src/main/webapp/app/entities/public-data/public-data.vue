<template>
  <div>
    <h2 id="page-heading" data-cy="PublicDataHeading">
      <span v-text="$t('dingtalkFeaturesLabApp.publicData.home.title')" id="public-data-heading">Public Data</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('dingtalkFeaturesLabApp.publicData.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PublicDataCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-public-data"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('dingtalkFeaturesLabApp.publicData.home.createLabel')"> Create a new Public Data </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && publicData && publicData.length === 0">
      <span v-text="$t('dingtalkFeaturesLabApp.publicData.home.notFound')">No publicData found</span>
    </div>
    <div class="table-responsive" v-if="publicData && publicData.length > 0">
      <table class="table table-striped" aria-describedby="publicData">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.publicData.name')">Name</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.publicData.fee')">Fee</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.publicData.reason')">Reason</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.publicData.itemType')">Item Type</span></th>
            <th scope="row"><span v-text="$t('dingtalkFeaturesLabApp.publicData.typesOfFee')">Types Of Fee</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="publicData in publicData" :key="publicData.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PublicDataView', params: { publicDataId: publicData.id } }">{{ publicData.id }}</router-link>
            </td>
            <td>{{ publicData.name }}</td>
            <td>{{ publicData.fee }}</td>
            <td>{{ publicData.reason }}</td>
            <td v-text="$t('dingtalkFeaturesLabApp.ItemType.' + publicData.itemType)">{{ publicData.itemType }}</td>
            <td v-text="$t('dingtalkFeaturesLabApp.TypesOfFee.' + publicData.typesOfFee)">{{ publicData.typesOfFee }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PublicDataView', params: { publicDataId: publicData.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PublicDataEdit', params: { publicDataId: publicData.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(publicData)"
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
          id="dingtalkFeaturesLabApp.publicData.delete.question"
          data-cy="publicDataDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-publicData-heading" v-text="$t('dingtalkFeaturesLabApp.publicData.delete.question', { id: removeId })">
          Are you sure you want to delete this Public Data?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-publicData"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePublicData()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./public-data.component.ts"></script>
