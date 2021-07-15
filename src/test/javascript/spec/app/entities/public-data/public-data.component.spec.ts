/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PublicDataComponent from '@/entities/public-data/public-data.vue';
import PublicDataClass from '@/entities/public-data/public-data.component';
import PublicDataService from '@/entities/public-data/public-data.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('PublicData Management Component', () => {
    let wrapper: Wrapper<PublicDataClass>;
    let comp: PublicDataClass;
    let publicDataServiceStub: SinonStubbedInstance<PublicDataService>;

    beforeEach(() => {
      publicDataServiceStub = sinon.createStubInstance<PublicDataService>(PublicDataService);
      publicDataServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PublicDataClass>(PublicDataComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          publicDataService: () => publicDataServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      publicDataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPublicDatas();
      await comp.$nextTick();

      // THEN
      expect(publicDataServiceStub.retrieve.called).toBeTruthy();
      expect(comp.publicData[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      publicDataServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePublicData();
      await comp.$nextTick();

      // THEN
      expect(publicDataServiceStub.delete.called).toBeTruthy();
      expect(publicDataServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
