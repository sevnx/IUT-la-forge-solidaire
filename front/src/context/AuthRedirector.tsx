import type { FileRoutesByFullPath } from '@/routeTree.gen';
import { AuthState, useAuth } from './AuthContext';
import { Navigate } from '@tanstack/react-router';

interface AuthRedirectorProps {
  children: React.ReactNode;
  expectedState: AuthState;
  fallbackRoute: keyof FileRoutesByFullPath;
}

export const AuthRedirector = ({ children, expectedState, fallbackRoute }: AuthRedirectorProps) => {
  const { state } = useAuth();

  if (state === AuthState.Loading) {
    return null;
  }

  if (state === expectedState) {
    return <>{children}</>;
  }

  return <Navigate to={fallbackRoute} />;
};
