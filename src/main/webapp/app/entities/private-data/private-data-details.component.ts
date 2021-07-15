import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPrivateData } from '@/shared/model/private-data.model';
import PrivateDataService from './private-data.service';

@Component
export default class PrivateDataDetails extends Vue {
  @Inject('privateDataService') private privateDataService: () => PrivateDataService;
  public privateData: IPrivateData = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.privateDataId) {
        vm.retrievePrivateData(to.params.privateDataId);
      }
    });
  }

  public retrievePrivateData(privateDataId) {
    this.privateDataService()
      .find(privateDataId)
      .then(res => {
        this.privateData = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
