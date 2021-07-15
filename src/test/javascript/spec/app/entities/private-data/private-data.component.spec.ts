/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PrivateDataComponent from '@/entities/private-data/private-data.vue';
import PrivateDataClass from '@/entities/private-data/private-data.component';
import PrivateDataService from '@/entities/private-data/private-data.service';

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
  describe('PrivateData Management Component', () => {
    let wrapper: Wrapper<PrivateDataClass>;
    let comp: PrivateDataClass;
    let privateDataServiceStub: SinonStubbedInstance<PrivateDataService>;

    beforeEach(() => {
      privateDataServiceStub = sinon.createStubInstance<PrivateDataService>(PrivateDataService);
      privateDataServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PrivateDataClass>(PrivateDataComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          privateDataService: () => privateDataServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      privateDataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPrivateDatas();
      await comp.$nextTick();

      // THEN
      expect(privateDataServiceStub.retrieve.called).toBeTruthy();
      expect(comp.privateData[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      privateDataServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePrivateData();
      await comp.$nextTick();

      // THEN
      expect(privateDataServiceStub.delete.called).toBeTruthy();
      expect(privateDataServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
