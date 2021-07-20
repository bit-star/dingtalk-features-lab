import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const PublicData = () => import('@/entities/public-data/public-data.vue');
// prettier-ignore
const PublicDataUpdate = () => import('@/entities/public-data/public-data-update.vue');
// prettier-ignore
const PublicDataDetails = () => import('@/entities/public-data/public-data-details.vue');
// prettier-ignore
const PrivateData = () => import('@/entities/private-data/private-data.vue');
// prettier-ignore
const PrivateDataUpdate = () => import('@/entities/private-data/private-data-update.vue');
// prettier-ignore
const PrivateDataDetails = () => import('@/entities/private-data/private-data-details.vue');
// prettier-ignore
const DdUser = () => import('@/entities/dd-user/dd-user.vue');
// prettier-ignore
const DdUserUpdate = () => import('@/entities/dd-user/dd-user-update.vue');
// prettier-ignore
const DdUserDetails = () => import('@/entities/dd-user/dd-user-details.vue');
// prettier-ignore
const Conversation = () => import('@/entities/conversation/conversation.vue');
// prettier-ignore
const ConversationUpdate = () => import('@/entities/conversation/conversation-update.vue');
// prettier-ignore
const ConversationDetails = () => import('@/entities/conversation/conversation-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/public-data',
    name: 'PublicData',
    component: PublicData,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-data/new',
    name: 'PublicDataCreate',
    component: PublicDataUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-data/:publicDataId/edit',
    name: 'PublicDataEdit',
    component: PublicDataUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-data/:publicDataId/view',
    name: 'PublicDataView',
    component: PublicDataDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/private-data',
    name: 'PrivateData',
    component: PrivateData,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/private-data/new',
    name: 'PrivateDataCreate',
    component: PrivateDataUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/private-data/:privateDataId/edit',
    name: 'PrivateDataEdit',
    component: PrivateDataUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/private-data/:privateDataId/view',
    name: 'PrivateDataView',
    component: PrivateDataDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user',
    name: 'DdUser',
    component: DdUser,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/new',
    name: 'DdUserCreate',
    component: DdUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/:ddUserId/edit',
    name: 'DdUserEdit',
    component: DdUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/:ddUserId/view',
    name: 'DdUserView',
    component: DdUserDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/conversation',
    name: 'Conversation',
    component: Conversation,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/conversation/new',
    name: 'ConversationCreate',
    component: ConversationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/conversation/:conversationId/edit',
    name: 'ConversationEdit',
    component: ConversationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/conversation/:conversationId/view',
    name: 'ConversationView',
    component: ConversationDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
