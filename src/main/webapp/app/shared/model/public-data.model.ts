import { IPrivateData } from '@/shared/model/private-data.model';

import { ItemType } from '@/shared/model/enumerations/item-type.model';
import { TypesOfFee } from '@/shared/model/enumerations/types-of-fee.model';
export interface IPublicData {
  id?: number;
  name?: string | null;
  fee?: number | null;
  reason?: string | null;
  itemType?: ItemType | null;
  typesOfFee?: TypesOfFee | null;
  privateData?: IPrivateData[] | null;
}

export class PublicData implements IPublicData {
  constructor(
    public id?: number,
    public name?: string | null,
    public fee?: number | null,
    public reason?: string | null,
    public itemType?: ItemType | null,
    public typesOfFee?: TypesOfFee | null,
    public privateData?: IPrivateData[] | null
  ) {}
}
