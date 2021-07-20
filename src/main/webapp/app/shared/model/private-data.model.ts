import { IPublicData } from '@/shared/model/public-data.model';
import { IDdUser } from '@/shared/model/dd-user.model';

import { ItemType } from '@/shared/model/enumerations/item-type.model';
import { TypesOfFee } from '@/shared/model/enumerations/types-of-fee.model';
export interface IPrivateData {
  id?: number;
  name?: string | null;
  fee?: number | null;
  reason?: string | null;
  itemType?: ItemType | null;
  typesOfFee?: TypesOfFee | null;
  agree?: boolean | null;
  publicData?: IPublicData | null;
  ddUser?: IDdUser | null;
}

export class PrivateData implements IPrivateData {
  constructor(
    public id?: number,
    public name?: string | null,
    public fee?: number | null,
    public reason?: string | null,
    public itemType?: ItemType | null,
    public typesOfFee?: TypesOfFee | null,
    public agree?: boolean | null,
    public publicData?: IPublicData | null,
    public ddUser?: IDdUser | null
  ) {
    this.agree = this.agree ?? false;
  }
}
