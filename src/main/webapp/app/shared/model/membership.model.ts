import { IUser } from 'app/shared/model/user.model';
import { IMessage } from 'app/shared/model/message.model';
import { IChannel } from 'app/shared/model/channel.model';

export interface IMembership {
  id?: number;
  user?: IUser | null;
  messages?: IMessage[] | null;
  channels?: IChannel[] | null;
}

export const defaultValue: Readonly<IMembership> = {};
