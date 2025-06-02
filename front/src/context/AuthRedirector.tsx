import type { FileRoutesByFullPath } from '@/routeTree.gen';
import { AuthState, useAuth } from './AuthContext';
import { Navigate } from '@tanstack/react-router';
import { EmptyPage } from '@/components/core/EmptyPage';

interface AuthRedirectorProps {
  children: React.ReactNode;
  expectedState: AuthState;
  fallbackRoute: keyof FileRoutesByFullPath;
}

export const AuthRedirector = ({ children, expectedState, fallbackRoute }: AuthRedirectorProps) => {
  const { state } = useAuth();

  if (state === AuthState.Loading) {
    return <EmptyPage />;
  }

  if (state === expectedState) {
    return <>{children}</>;
  }

  return <Navigate to={fallbackRoute} />;
};
