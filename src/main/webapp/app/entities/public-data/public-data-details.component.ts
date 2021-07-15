import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPublicData } from '@/shared/model/public-data.model';
import PublicDataService from './public-data.service';

@Component
export default class PublicDataDetails extends Vue {
  @Inject('publicDataService') private publicDataService: () => PublicDataService;
  public publicData: IPublicData = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicDataId) {
        vm.retrievePublicData(to.params.publicDataId);
      }
    });
  }

  public retrievePublicData(publicDataId) {
    this.publicDataService()
      .find(publicDataId)
      .then(res => {
        this.publicData = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
