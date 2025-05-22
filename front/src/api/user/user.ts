import api from '@/lib/api';
import axios from 'axios';
import { okAsync, ResultAsync } from 'neverthrow';

export interface User {
  username: string;
}

export enum UserError {
  Unauthorized = 'Unauthorized',
  UnexpectedError = 'Unexpected error',
}

export const getUser = (): ResultAsync<User, UserError> => {
  return ResultAsync.fromPromise(api.get('/user'), (error) => {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 401) {
        return UserError.Unauthorized;
      }
      return UserError.UnexpectedError;
    }
    return UserError.UnexpectedError;
  }).andThen((response) => {
    return okAsync<User, UserError>({
      username: response.data.username,
    });
  });
};
