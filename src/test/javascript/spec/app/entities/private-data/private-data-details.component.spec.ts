/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PrivateDataDetailComponent from '@/entities/private-data/private-data-details.vue';
import PrivateDataClass from '@/entities/private-data/private-data-details.component';
import PrivateDataService from '@/entities/private-data/private-data.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PrivateData Management Detail Component', () => {
    let wrapper: Wrapper<PrivateDataClass>;
    let comp: PrivateDataClass;
    let privateDataServiceStub: SinonStubbedInstance<PrivateDataService>;

    beforeEach(() => {
      privateDataServiceStub = sinon.createStubInstance<PrivateDataService>(PrivateDataService);

      wrapper = shallowMount<PrivateDataClass>(PrivateDataDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { privateDataService: () => privateDataServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPrivateData = { id: 123 };
        privateDataServiceStub.find.resolves(foundPrivateData);

        // WHEN
        comp.retrievePrivateData(123);
        await comp.$nextTick();

        // THEN
        expect(comp.privateData).toBe(foundPrivateData);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPrivateData = { id: 123 };
        privateDataServiceStub.find.resolves(foundPrivateData);

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
