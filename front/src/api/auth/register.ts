import api from '@/lib/api';
import axios from 'axios';
import { ResultAsync } from 'neverthrow';

export interface RegisterRequest {
  username: string;
  password: string;
  address: string;
}

export enum RegisterError {
  UsernameAlreadyExists = 'Username already exists',
  UnexpectedError = 'Unexpected error',
}

export const register = (request: RegisterRequest): ResultAsync<void, RegisterError> => {
  return ResultAsync.fromPromise(api.post('/auth/register', request), (error) => {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 400) {
        return RegisterError.UsernameAlreadyExists;
      }
      return RegisterError.UnexpectedError;
    }
    return RegisterError.UnexpectedError;
  });
};
