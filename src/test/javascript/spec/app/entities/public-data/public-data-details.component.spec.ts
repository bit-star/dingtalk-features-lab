/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PublicDataDetailComponent from '@/entities/public-data/public-data-details.vue';
import PublicDataClass from '@/entities/public-data/public-data-details.component';
import PublicDataService from '@/entities/public-data/public-data.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PublicData Management Detail Component', () => {
    let wrapper: Wrapper<PublicDataClass>;
    let comp: PublicDataClass;
    let publicDataServiceStub: SinonStubbedInstance<PublicDataService>;

    beforeEach(() => {
      publicDataServiceStub = sinon.createStubInstance<PublicDataService>(PublicDataService);

      wrapper = shallowMount<PublicDataClass>(PublicDataDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { publicDataService: () => publicDataServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPublicData = { id: 123 };
        publicDataServiceStub.find.resolves(foundPublicData);

        // WHEN
        comp.retrievePublicData(123);
        await comp.$nextTick();

        // THEN
        expect(comp.publicData).toBe(foundPublicData);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublicData = { id: 123 };
        publicDataServiceStub.find.resolves(foundPublicData);

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
