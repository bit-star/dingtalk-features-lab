import { Component, Vue, Inject } from 'vue-property-decorator';

import PublicDataService from '@/entities/public-data/public-data.service';
import { IPublicData } from '@/shared/model/public-data.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import { IConversation, Conversation } from '@/shared/model/conversation.model';
import ConversationService from './conversation.service';

const validations: any = {
  conversation: {
    name: {},
  },
};

@Component({
  validations,
})
export default class ConversationUpdate extends Vue {
  @Inject('conversationService') private conversationService: () => ConversationService;
  public conversation: IConversation = new Conversation();

  @Inject('publicDataService') private publicDataService: () => PublicDataService;

  public publicData: IPublicData[] = [];

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.conversationId) {
        vm.retrieveConversation(to.params.conversationId);
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
    if (this.conversation.id) {
      this.conversationService()
        .update(this.conversation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('dingtalkFeaturesLabApp.conversation.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.conversationService()
        .create(this.conversation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('dingtalkFeaturesLabApp.conversation.created', { param: param.id });
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

  public retrieveConversation(conversationId): void {
    this.conversationService()
      .find(conversationId)
      .then(res => {
        this.conversation = res;
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
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
  }
}
