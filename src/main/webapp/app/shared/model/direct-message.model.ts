import { IMessage } from 'app/shared/model/message.model';
import { IUser } from 'app/shared/model/user.model';

export interface IDirectMessage {
  id?: number;
  fromUserId?: number | null;
  toUserId?: number | null;
  messages?: IMessage[] | null;
  users?: IUser[] | null;
}

export const defaultValue: Readonly<IDirectMessage> = {};
