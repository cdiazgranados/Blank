import { IMessage } from 'app/shared/model/message.model';
import { IMembership } from 'app/shared/model/membership.model';

export interface IChannel {
  id?: number;
  name?: string | null;
  description?: string | null;
  messages?: IMessage[] | null;
  memberships?: IMembership[] | null;
  messages?: IMessage[] | null;
}

export const defaultValue: Readonly<IChannel> = {};
