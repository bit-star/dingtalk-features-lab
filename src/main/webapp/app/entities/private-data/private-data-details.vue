<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="privateData">
        <h2 class="jh-entity-heading" data-cy="privateDataDetailsHeading">
          <span v-text="$t('dingtalkFeaturesLabApp.privateData.detail.title')">PrivateData</span> {{ privateData.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.name')">Name</span>
          </dt>
          <dd>
            <span>{{ privateData.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.fee')">Fee</span>
          </dt>
          <dd>
            <span>{{ privateData.fee }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.reason')">Reason</span>
          </dt>
          <dd>
            <span>{{ privateData.reason }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.itemType')">Item Type</span>
          </dt>
          <dd>
            <span v-text="$t('dingtalkFeaturesLabApp.ItemType.' + privateData.itemType)">{{ privateData.itemType }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.typesOfFee')">Types Of Fee</span>
          </dt>
          <dd>
            <span v-text="$t('dingtalkFeaturesLabApp.TypesOfFee.' + privateData.typesOfFee)">{{ privateData.typesOfFee }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.agree')">Agree</span>
          </dt>
          <dd>
            <span>{{ privateData.agree }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.publicData')">Public Data</span>
          </dt>
          <dd>
            <div v-if="privateData.publicData">
              <router-link :to="{ name: 'PublicDataView', params: { publicDataId: privateData.publicData.id } }">{{
                privateData.publicData.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.privateData.ddUser')">Dd User</span>
          </dt>
          <dd>
            <div v-if="privateData.ddUser">
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: privateData.ddUser.id } }">{{
                privateData.ddUser.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="privateData.id"
          :to="{ name: 'PrivateDataEdit', params: { privateDataId: privateData.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./private-data-details.component.ts"></script>
