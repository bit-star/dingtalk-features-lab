<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="publicData">
        <h2 class="jh-entity-heading" data-cy="publicDataDetailsHeading">
          <span v-text="$t('dingtalkFeaturesLabApp.publicData.detail.title')">PublicData</span> {{ publicData.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.publicData.name')">Name</span>
          </dt>
          <dd>
            <span>{{ publicData.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.publicData.fee')">Fee</span>
          </dt>
          <dd>
            <span>{{ publicData.fee }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.publicData.reason')">Reason</span>
          </dt>
          <dd>
            <span>{{ publicData.reason }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.publicData.itemType')">Item Type</span>
          </dt>
          <dd>
            <span v-text="$t('dingtalkFeaturesLabApp.ItemType.' + publicData.itemType)">{{ publicData.itemType }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.publicData.typesOfFee')">Types Of Fee</span>
          </dt>
          <dd>
            <span v-text="$t('dingtalkFeaturesLabApp.TypesOfFee.' + publicData.typesOfFee)">{{ publicData.typesOfFee }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.publicData.agree')">Agree</span>
          </dt>
          <dd>
            <span>{{ publicData.agree }}</span>
          </dd>
          <dt>
            <span v-text="$t('dingtalkFeaturesLabApp.publicData.conversation')">Conversation</span>
          </dt>
          <dd>
            <div v-if="publicData.conversation">
              <router-link :to="{ name: 'ConversationView', params: { conversationId: publicData.conversation.id } }">{{
                publicData.conversation.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="publicData.id"
          :to="{ name: 'PublicDataEdit', params: { publicDataId: publicData.id } }"
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

<script lang="ts" src="./public-data-details.component.ts"></script>
