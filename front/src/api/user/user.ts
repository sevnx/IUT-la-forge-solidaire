import api from '@/lib/api';
import axios from 'axios';
import { ResultAsync } from 'neverthrow';

export enum UserError {
  Unauthorized = 'Unauthorized',
  UnexpectedError = 'Unexpected error',
}

export const getUser = (): ResultAsync<void, UserError> => {
  return ResultAsync.fromPromise(api.get('/user'), (error) => {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 401) {
        return UserError.Unauthorized;
      }
      return UserError.UnexpectedError;
    }
    return UserError.UnexpectedError;
  });
};
