import api from '@/lib/api';
import axios from 'axios';
import { ResultAsync } from 'neverthrow';

export interface LoginRequest {
  username: string;
  password: string;
}

export enum LoginError {
  InvalidCredentials = 'Invalid credentials',
  UnexpectedError = 'Unexpected error',
}

export const login = (credentials: LoginRequest): ResultAsync<void, LoginError> => {
  return ResultAsync.fromPromise(api.post('/auth/login', credentials), (error) => {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 401) {
        return LoginError.InvalidCredentials;
      }
      return LoginError.UnexpectedError;
    }
    return LoginError.UnexpectedError;
  });
};
