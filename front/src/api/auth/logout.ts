import api from '@/lib/api';
import axios from 'axios';
import { ResultAsync } from 'neverthrow';

export enum LogoutError {
  Unauthorized = 'Unauthorized',
  UnexpectedError = 'Unexpected error',
}

export const logout = (): ResultAsync<void, LogoutError> => {
  return ResultAsync.fromPromise(api.post('/auth/logout'), (error) => {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 401) {
        return LogoutError.Unauthorized;
      }
    }
    return LogoutError.UnexpectedError;
  });
};
