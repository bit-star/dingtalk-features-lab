import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPrivateData } from '@/shared/model/private-data.model';

import PrivateDataService from './private-data.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PrivateData extends Vue {
  @Inject('privateDataService') private privateDataService: () => PrivateDataService;
  private removeId: number = null;

  public privateData: IPrivateData[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPrivateDatas();
  }

  public clear(): void {
    this.retrieveAllPrivateDatas();
  }

  public retrieveAllPrivateDatas(): void {
    this.isFetching = true;
    this.privateDataService()
      .retrieve()
      .then(
        res => {
          this.privateData = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IPrivateData): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePrivateData(): void {
    this.privateDataService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('dingtalkFeaturesLabApp.privateData.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPrivateDatas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
