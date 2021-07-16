import { Component, Vue, Inject } from 'vue-property-decorator';

import PublicDataService from '@/entities/public-data/public-data.service';
import { IPublicData } from '@/shared/model/public-data.model';

import { IPrivateData, PrivateData } from '@/shared/model/private-data.model';
import PrivateDataService from './private-data.service';

const validations: any = {
  privateData: {
    name: {},
    fee: {},
    reason: {},
    itemType: {},
    typesOfFee: {},
    agree: {},
  },
};

@Component({
  validations,
})
export default class PrivateDataUpdate extends Vue {
  @Inject('privateDataService') private privateDataService: () => PrivateDataService;
  public privateData: IPrivateData = new PrivateData();

  @Inject('publicDataService') private publicDataService: () => PublicDataService;

  public publicData: IPublicData[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.privateDataId) {
        vm.retrievePrivateData(to.params.privateDataId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.privateData.id) {
      this.privateDataService()
        .update(this.privateData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('dingtalkFeaturesLabApp.privateData.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.privateDataService()
        .create(this.privateData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('dingtalkFeaturesLabApp.privateData.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrievePrivateData(privateDataId): void {
    this.privateDataService()
      .find(privateDataId)
      .then(res => {
        this.privateData = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.publicDataService()
      .retrieve()
      .then(res => {
        this.publicData = res.data;
      });
  }
}
