import { IPublicData } from '@/shared/model/public-data.model';
import { IDdUser } from '@/shared/model/dd-user.model';

export interface IConversation {
  id?: string;
  name?: string | null;
  publicData?: IPublicData[] | null;
  ddUsers?: IDdUser[] | null;
}

export class Conversation implements IConversation {
  constructor(
    public id?: string,
    public name?: string | null,
    public publicData?: IPublicData[] | null,
    public ddUsers?: IDdUser[] | null
  ) {}
}
