import { Component, Vue, Inject } from 'vue-property-decorator';

import PrivateDataService from '@/entities/private-data/private-data.service';
import { IPrivateData } from '@/shared/model/private-data.model';

import ConversationService from '@/entities/conversation/conversation.service';
import { IConversation } from '@/shared/model/conversation.model';

import { IPublicData, PublicData } from '@/shared/model/public-data.model';
import PublicDataService from './public-data.service';

const validations: any = {
  publicData: {
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
export default class PublicDataUpdate extends Vue {
  @Inject('publicDataService') private publicDataService: () => PublicDataService;
  public publicData: IPublicData = new PublicData();

  @Inject('privateDataService') private privateDataService: () => PrivateDataService;

  public privateData: IPrivateData[] = [];

  @Inject('conversationService') private conversationService: () => ConversationService;

  public conversations: IConversation[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicDataId) {
        vm.retrievePublicData(to.params.publicDataId);
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
    if (this.publicData.id) {
      this.publicDataService()
        .update(this.publicData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('dingtalkFeaturesLabApp.publicData.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.publicDataService()
        .create(this.publicData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('dingtalkFeaturesLabApp.publicData.created', { param: param.id });
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

  public retrievePublicData(publicDataId): void {
    this.publicDataService()
      .find(publicDataId)
      .then(res => {
        this.publicData = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.privateDataService()
      .retrieve()
      .then(res => {
        this.privateData = res.data;
      });
    this.conversationService()
      .retrieve()
      .then(res => {
        this.conversations = res.data;
      });
  }
}
