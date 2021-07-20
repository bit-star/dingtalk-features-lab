/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PublicDataUpdateComponent from '@/entities/public-data/public-data-update.vue';
import PublicDataClass from '@/entities/public-data/public-data-update.component';
import PublicDataService from '@/entities/public-data/public-data.service';

import PrivateDataService from '@/entities/private-data/private-data.service';

import ConversationService from '@/entities/conversation/conversation.service';

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
  describe('PublicData Management Update Component', () => {
    let wrapper: Wrapper<PublicDataClass>;
    let comp: PublicDataClass;
    let publicDataServiceStub: SinonStubbedInstance<PublicDataService>;

    beforeEach(() => {
      publicDataServiceStub = sinon.createStubInstance<PublicDataService>(PublicDataService);

      wrapper = shallowMount<PublicDataClass>(PublicDataUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          publicDataService: () => publicDataServiceStub,

          privateDataService: () => new PrivateDataService(),

          conversationService: () => new ConversationService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.publicData = entity;
        publicDataServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicDataServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.publicData = entity;
        publicDataServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicDataServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublicData = { id: 123 };
        publicDataServiceStub.find.resolves(foundPublicData);
        publicDataServiceStub.retrieve.resolves([foundPublicData]);

        // WHEN
        comp.beforeRouteEnter({ params: { publicDataId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.publicData).toBe(foundPublicData);
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
