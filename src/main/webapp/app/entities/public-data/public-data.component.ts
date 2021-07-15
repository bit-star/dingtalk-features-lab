import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPublicData } from '@/shared/model/public-data.model';

import PublicDataService from './public-data.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PublicData extends Vue {
  @Inject('publicDataService') private publicDataService: () => PublicDataService;
  private removeId: number = null;

  public publicData: IPublicData[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPublicDatas();
  }

  public clear(): void {
    this.retrieveAllPublicDatas();
  }

  public retrieveAllPublicDatas(): void {
    this.isFetching = true;
    this.publicDataService()
      .retrieve()
      .then(
        res => {
          this.publicData = res.data;
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

  public prepareRemove(instance: IPublicData): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePublicData(): void {
    this.publicDataService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('dingtalkFeaturesLabApp.publicData.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPublicDatas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
