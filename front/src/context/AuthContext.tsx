import { login as loginApi, type LoginError, type LoginRequest } from '@/api/auth/login';
import { register as registerApi, type RegisterError, type RegisterRequest } from '@/api/auth/register';
import { logout as logoutApi, LogoutError } from '@/api/auth/logout';
import { getUser } from '@/api/user/user';
import { ResultAsync } from 'neverthrow';
import { createContext, useContext, useEffect, useState } from 'react';

interface AuthContextProps {
  state: AuthState;
  login: (credentials: LoginRequest) => ResultAsync<void, LoginError>;
  register: (request: RegisterRequest) => ResultAsync<void, RegisterError>;
  logout: () => ResultAsync<void, LogoutError>;
}

export enum AuthState {
  Loading,
  LoggedIn,
  LoggedOut,
}

export const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [state, setState] = useState<AuthState>(AuthState.Loading);

  useEffect(() => {
    const fetchUser = async () => {
      const user = await getUser();
      if (user.isOk()) {
        setState(AuthState.LoggedIn);
      } else {
        setState(AuthState.LoggedOut);
      }
    };

    fetchUser();
  }, []);

  const register = (request: RegisterRequest): ResultAsync<void, RegisterError> => {
    return registerApi(request).andThen(() => {
      setState(AuthState.LoggedIn);
      return ResultAsync.fromSafePromise(Promise.resolve());
    });
  };

  const login = (credentials: LoginRequest): ResultAsync<void, LoginError> => {
    setState(AuthState.LoggedIn);
    return loginApi(credentials).andThen(() => {
      setState(AuthState.LoggedIn);
      return ResultAsync.fromSafePromise(Promise.resolve());
    });
  };

  const logout = (): ResultAsync<void, LogoutError> => {
    setState(AuthState.LoggedOut);
    return logoutApi().andThen(() => {
      setState(AuthState.LoggedOut);
      return ResultAsync.fromSafePromise(Promise.resolve());
    });
  };

  return <AuthContext.Provider value={{ state, login, register, logout }}>{children}</AuthContext.Provider>;
};

export const useAuth = (): AuthContextProps => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
