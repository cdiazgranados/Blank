import { IUser } from 'app/shared/model/user.model';

export interface IUserProfile {
  id?: number;
  displayName?: string | null;
  profileImageContentType?: string | null;
  profileImage?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUserProfile> = {};
