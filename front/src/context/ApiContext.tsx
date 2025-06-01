import React, { useEffect } from 'react';
import api from '@/lib/api';
import { AuthState, useAuth } from './AuthContext';

interface ApiProviderProps {
  children: React.ReactNode;
}

export const ApiContext = ({ children }: ApiProviderProps) => {
  const { state, logout } = useAuth();

  useEffect(() => {
    const interceptorId = api.interceptors.response.use(
      (response) => response,
      (error) => {
        if (state === AuthState.LoggedIn && error.response?.status === 401) {
          logout();
        }
        return Promise.reject(error);
      },
    );

    return () => {
      api.interceptors.response.eject(interceptorId);
    };
  }, [logout, state]);

  return <>{children}</>;
};
