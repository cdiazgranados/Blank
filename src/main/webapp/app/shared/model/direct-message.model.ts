import { IUser } from 'app/shared/model/user.model';
import { IMessage } from 'app/shared/model/message.model';

export interface IDirectMessage {
  id?: number;
  fromUserId?: number | null;
  toUserId?: number | null;
  users?: IUser[] | null;
  messages?: IMessage[] | null;
}

export const defaultValue: Readonly<IDirectMessage> = {};
