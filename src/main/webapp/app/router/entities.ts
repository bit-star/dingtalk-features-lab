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
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
