/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PrivateDataUpdateComponent from '@/entities/private-data/private-data-update.vue';
import PrivateDataClass from '@/entities/private-data/private-data-update.component';
import PrivateDataService from '@/entities/private-data/private-data.service';

import PublicDataService from '@/entities/public-data/public-data.service';

import DdUserService from '@/entities/dd-user/dd-user.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('PrivateData Management Update Component', () => {
    let wrapper: Wrapper<PrivateDataClass>;
    let comp: PrivateDataClass;
    let privateDataServiceStub: SinonStubbedInstance<PrivateDataService>;

    beforeEach(() => {
      privateDataServiceStub = sinon.createStubInstance<PrivateDataService>(PrivateDataService);

      wrapper = shallowMount<PrivateDataClass>(PrivateDataUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          privateDataService: () => privateDataServiceStub,

          publicDataService: () => new PublicDataService(),

          ddUserService: () => new DdUserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.privateData = entity;
        privateDataServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(privateDataServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.privateData = entity;
        privateDataServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(privateDataServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPrivateData = { id: 123 };
        privateDataServiceStub.find.resolves(foundPrivateData);
        privateDataServiceStub.retrieve.resolves([foundPrivateData]);

        // WHEN
        comp.beforeRouteEnter({ params: { privateDataId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.privateData).toBe(foundPrivateData);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
