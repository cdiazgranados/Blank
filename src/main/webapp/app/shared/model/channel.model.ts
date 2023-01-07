import { IMembership } from 'app/shared/model/membership.model';
import { IMessage } from 'app/shared/model/message.model';

export interface IChannel {
  id?: number;
  name?: string | null;
  description?: string | null;
  membership?: IMembership | null;
  messages?: IMessage[] | null;
}

export const defaultValue: Readonly<IChannel> = {};
