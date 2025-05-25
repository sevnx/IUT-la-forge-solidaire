import React, { useEffect } from 'react';
import api from '@/lib/api';
import { useAuth } from './AuthContext';

interface ApiProviderProps {
  children: React.ReactNode;
}

export const ApiContext = ({ children }: ApiProviderProps) => {
  const { logout } = useAuth();

  useEffect(() => {
    const interceptorId = api.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response?.status === 401) {
          logout();
        }
        return Promise.reject(error);
      },
    );

    return () => {
      api.interceptors.response.eject(interceptorId);
    };
  }, [logout]);

  return <>{children}</>;
};
