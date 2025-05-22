import type { LoginError, LoginRequest } from '@/api/auth/login';
import type { RegisterError, RegisterRequest } from '@/api/auth/register';
import type { ResultAsync } from 'neverthrow';

interface AuthContextProps {
  state: AuthState;
  login: (credentials: LoginRequest) => ResultAsync<void, LoginError>;
  register: (request: RegisterRequest) => ResultAsync<void, RegisterError>;
}

export enum AuthState {
  Loading,
  LoggedIn,
  LoggedOut,
}
