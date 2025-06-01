import { login as loginApi, type LoginError, type LoginRequest } from '@/api/auth/login';
import { register as registerApi, type RegisterError, type RegisterRequest } from '@/api/auth/register';
import { logout as logoutApi, LogoutError } from '@/api/auth/logout';
import { getUser } from '@/api/user/user';
import { Result } from 'neverthrow';
import { createContext, useContext, useEffect, useState } from 'react';

interface AuthContextProps {
  state: AuthState;
  login: (credentials: LoginRequest) => Promise<Result<void, LoginError>>;
  register: (request: RegisterRequest) => Promise<Result<void, RegisterError>>;
  logout: () => Promise<Result<void, LogoutError>>;
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

  const register = async (request: RegisterRequest): Promise<Result<void, RegisterError>> => {
    const result = await registerApi(request);
    if (result.isOk()) {
      setState(AuthState.LoggedIn);
    }
    return result;
  };

  const login = async (credentials: LoginRequest): Promise<Result<void, LoginError>> => {
    const result = await loginApi(credentials);
    if (result.isOk()) {
      setState(AuthState.LoggedIn);
    }
    return result;
  };

  const logout = async (): Promise<Result<void, LogoutError>> => {
    const result = await logoutApi();
    if (result.isErr()) {
      console.error(result.error);
    }
    setState(AuthState.LoggedOut);
    return result;
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
